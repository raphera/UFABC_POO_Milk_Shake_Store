package com.ufabc.poo.services;

import com.ufabc.poo.domain.Compra;
import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.domain.Remocao;
import com.ufabc.poo.domain.Venda;
import com.ufabc.poo.domain.abstractions.ATransacao;
import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;
import com.ufabc.poo.services.interfaces.IEstoque;
import com.ufabc.poo.services.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class TransacaoService implements ITranscaoService {

    private final IBancoDeMilkShakes bancoDeMilkShakes;
    private final IEstoque estoque;

    private Connection connect() {
        String url = "jdbc:sqlite:milkshakestore.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void insert(ATransacao transacao) {
        String sql = "INSERT INTO Transacoes (Nome, Tipo, Valor, Quantidade, Data) VALUES (?,?,?,?,?)";
        try {
            Connection con = connect();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, transacao.getNome());
            pstmt.setString(2, transacao.getTipo());
            pstmt.setFloat(3, transacao.getValorTotal());
            pstmt.setInt(4, transacao.getQuantidade());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String ts = sdf.format(timestamp);
            pstmt.setString(5, ts);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Inject
    public TransacaoService(IBancoDeMilkShakes bancoDeMilkShakes, IEstoque estoque) {
        this.bancoDeMilkShakes = bancoDeMilkShakes;
        this.estoque = estoque;
    }

    @Override
    public boolean efetuaCompra(ATransacao compra) {
        try {
            estoque.AdicionaIng(compra.getCodigo(), compra.getNome(), compra.getQuantidade(), compra.getValor());
            insert(compra);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean removeCompra(UUID Id) {
        try {
            Ingrediente tempIng = estoque.getIng(Id);
            estoque.RemoveIng(Id);
            insert(new Remocao(tempIng.getCodigo(), tempIng.getNome(), tempIng.getQuantidade(), tempIng.getPCusto()));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean efetuaVenda(ATransacao venda) {
        MilkShake MilkShake = bancoDeMilkShakes.getMilkShake(venda.getNome());

        for (Map.Entry<UUID, Integer> ingrediente : MilkShake.getIngredientes().entrySet()) {
            estoque.RemoveIng(estoque.getIng(ingrediente.getKey()).getNome(), ingrediente.getValue());
        }

        insert(venda);
        return true;
    }

    @Override
    public ArrayList<ATransacao> getVendas(Timestamp date) {
        String sql = "SELECT * FROM Transacoes WHERE Tipo = \"Venda\" AND DATE(data) <= DATE(?)";
        ArrayList<ATransacao> retorno = new ArrayList<>();
        try {
            Connection con = connect();
            PreparedStatement pstmt = con.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ts = sdf.format(date);
            pstmt.setString(1, ts);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Venda(
                        rs.getLong("Codigo"),
                        rs.getString("Nome"),
                        rs.getInt("Quantidade"),
                        rs.getInt("Valor"),
                        rs.getString("Data")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public ArrayList<ATransacao> getCompras(Timestamp date) {
        String sql = "SELECT * FROM Transacoes WHERE Tipo = \"Compra\" AND DATE(data) = DATE(?)";
        ArrayList<ATransacao> retorno = new ArrayList<>();
        try {
            Connection con = connect();
            PreparedStatement pstmt = con.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ts = sdf.format(date);
            pstmt.setString(1, ts);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Compra(
                        rs.getLong("Codigo"),
                        rs.getString("Nome"),
                        rs.getInt("Quantidade"),
                        rs.getInt("Valor"),
                        rs.getString("Data")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}

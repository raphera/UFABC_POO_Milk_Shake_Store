package com.ufabc.poo.controllers;

import com.ufabc.poo.App;
import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.domain.abstractions.ATransacao;
import com.ufabc.poo.helpers.DI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;
import com.ufabc.poo.services.interfaces.IEstoque;
import com.ufabc.poo.services.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RelatoriosController implements Initializable {
    private final ITranscaoService transacao;
    private final IEstoque estoque;

    @Inject
    public RelatoriosController(IEstoque estoque, ITranscaoService transacao, IBancoDeMilkShakes receitas) {
        this.transacao = transacao;
        this.estoque = estoque;
    }

    @FXML
    DatePicker dataInicial, dataFinal;

    @FXML
    Label labelCompras, labelFaturamento, labelVendas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataInicial.setValue(LocalDate.now().minusDays(7));
        dataFinal.setValue(LocalDate.now());

        calcValues(dataInicial.getValue(), dataFinal.getValue());

    }

    @FXML
    private void dataAlterada() {
        calcValues(dataInicial.getValue(), dataFinal.getValue());
    }

    private void calcValues(LocalDate dataInicial, LocalDate dataFinal) {
        ArrayList<ATransacao> compras = transacao.getCompras(dataInicial, dataFinal);
        ArrayList<ATransacao> vendas = transacao.getVendas(dataInicial, dataFinal);

        float valorCompras = 0;
        float valorVendas = 0;
        float valorCusto = 0;
        float valorFaturamento = 0;

        for (ATransacao compra : compras) {
            valorCompras += compra.getValor();
        }

        for (ATransacao venda : vendas) {
            valorVendas += venda.getValor();
            valorCusto += venda.getCusto();
        }

        valorFaturamento = valorVendas - valorCusto;

        labelCompras.setText(String.format("%.2f", valorCompras));
        labelVendas.setText(String.format("%.2f", valorVendas));
        labelFaturamento.setText(String.format("%.2f", valorFaturamento));
    }

}
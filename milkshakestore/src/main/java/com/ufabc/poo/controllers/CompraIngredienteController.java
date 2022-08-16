package com.ufabc.poo.controllers;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.ufabc.poo.domain.Compra;
import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.services.interfaces.IEstoque;
import com.ufabc.poo.services.interfaces.ITranscaoService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CompraIngredienteController implements Initializable {
    private final ITranscaoService transacao;
    public static Ingrediente selectedIngrediente;

    private final IEstoque estoque;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private TextField fieldCodigo, fieldNome, fieldPreco, fieldQuantidade;

    public CompraIngredienteController(ITranscaoService transacao, IEstoque estoque) {
        this.transacao = transacao;
        this.estoque = estoque;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Garante que o código não tenha mais que 13 dígitos
        fieldCodigo.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("[0-9]{0,13}") ? c : null));
        fieldPreco.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("[0-9,.]*") ? c : null));

        if (selectedIngrediente != null) {
            fieldCodigo.setDisable(true);
            fieldCodigo.setText(String.valueOf(selectedIngrediente.getCodigo()));
            fieldNome.setText(selectedIngrediente.getNome());
            fieldPreco.setText(NumberFormat.getNumberInstance(new Locale("pt", "BR"))
                    .format(selectedIngrediente.getPCusto()).toString());
            fieldQuantidade.setText(String.valueOf(selectedIngrediente.getQuantidade()));

            botaoAdicionar.setText("Salvar");
        }
    }

    @FXML
    private void botaoCancelar() {
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void botaoAdicionar() {
        float valor;
        try {
            valor = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(fieldPreco.getText()).floatValue();

            if (selectedIngrediente == null) {

                transacao.efetuaCompra(
                        new Compra(
                                Long.parseLong(fieldCodigo.getText()),
                                fieldNome.getText(),
                                Integer.parseInt(fieldQuantidade.getText()),
                                valor));
            } else {
                selectedIngrediente.setCodigo(Long.parseLong(fieldCodigo.getText()));
                selectedIngrediente.setNome(fieldNome.getText());

                if (Integer.parseInt(fieldQuantidade.getText()) < selectedIngrediente.getQuantidade()) {
                    // Caso haja ocorra uma remoção de ingrediente
                    transacao.removeCompra(
                            selectedIngrediente.getId(),
                            selectedIngrediente.getQuantidade() - Integer.parseInt(fieldQuantidade.getText()));

                    selectedIngrediente.setPCusto(valor);
                } else if (Integer.parseInt(fieldQuantidade.getText()) > selectedIngrediente.getQuantidade()) {
                    // Caso haja ocorra uma adição de ingrediente
                    transacao.efetuaCompra(
                            new Compra(
                                    Long.parseLong(fieldCodigo.getText()),
                                    fieldNome.getText(),
                                    Integer.parseInt(fieldQuantidade.getText()) - selectedIngrediente.getQuantidade(),
                                    valor));
                } else {
                    // Caso não haja adição nem remoção de ingrediente
                    selectedIngrediente.setPCusto(valor);
                    selectedIngrediente.setQuantidade(Integer.parseInt(fieldQuantidade.getText()));
                }

                estoque.editIng(selectedIngrediente);
            }
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar ingrediente");
            alert.setContentText("O valor do ingrediente não pode ser convertido para float");
            alert.showAndWait();
        } finally {
            Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
            stage.close();
        }
    }
}

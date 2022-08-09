package com.ufabc.poo.controllers;

import com.ufabc.poo.domain.Compra;
import com.ufabc.poo.domain.Ingrediente;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import com.ufabc.poo.services.Estoque;
import com.ufabc.poo.services.interfaces.ITranscaoService;
import com.ufabc.poo.services.interfaces.IEstoque;

import javax.inject.Inject;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

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

        if (selectedIngrediente == null) {
            transacao.efetuaCompra(new Compra(Long.parseLong(fieldCodigo.getText()), fieldNome.getText(),
                    Integer.parseInt(fieldQuantidade.getText()),
                    Float.parseFloat(fieldPreco.getText())));
        } else {

            selectedIngrediente.setCodigo(Long.parseLong(fieldCodigo.getText()));
            selectedIngrediente.setNome(fieldNome.getText());

            try {
                selectedIngrediente.setPCusto(NumberFormat.getNumberInstance(new Locale("pt", "BR"))
                        .parse(fieldPreco.getText()).floatValue());
            } catch (ParseException e) {
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setTitle("Novo Ingrediente");
                dialogoInfo.setHeaderText("Formato de preço inválido");
                dialogoInfo.setContentText("O preço deve ser um número válido, com \",\" como separador de decimal.");
                dialogoInfo.showAndWait();

                e.printStackTrace();
            }

            selectedIngrediente.setQuantidade(Integer.parseInt(fieldQuantidade.getText()));

            estoque.editIng(selectedIngrediente);
        }

        Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
        stage.close();
    }
}

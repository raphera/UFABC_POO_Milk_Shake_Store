package com.ufabc.poo.controllers;

import com.ufabc.poo.domain.Compra;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.ufabc.poo.services.interfaces.ITranscaoService;


public class CompraIngredienteController {
    private final ITranscaoService transacao;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private TextField fieldNome;

    @FXML
    private TextField fieldPreco;

    @FXML
    private TextField fieldQuantidade;

    public CompraIngredienteController(ITranscaoService transacao) {
        this.transacao = transacao;
    }

    @FXML
    private void botaoCancelar(){
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void botaoAdicionar(){
        transacao.efetuaCompra(new Compra(fieldNome.getText(), Integer.parseInt(fieldQuantidade.getText()), Float.parseFloat(fieldPreco.getText())));
        
        Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
        stage.close();
    }
}

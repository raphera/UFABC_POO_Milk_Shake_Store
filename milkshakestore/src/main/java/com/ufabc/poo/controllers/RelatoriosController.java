package com.ufabc.poo.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.ufabc.poo.domain.abstractions.ATransacao;
import com.ufabc.poo.services.interfaces.ITranscaoService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class RelatoriosController implements Initializable {
    private final ITranscaoService transacao;

    @Inject
    public RelatoriosController(ITranscaoService transacao) {
        this.transacao = transacao;
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
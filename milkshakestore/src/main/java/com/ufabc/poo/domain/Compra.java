package com.ufabc.poo.domain;

import com.ufabc.poo.domain.abstractions.ATransacao;

public class Compra extends ATransacao {

    public Compra(String nome, int quantidade, float valor) {
        super(nome, quantidade, valor, "");
    }

    public Compra(String nome, int quantidade, float valor, String data) {
        super(nome, quantidade, valor, data);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade() * getValor();
    }

    @Override
    public String getTipo() {
        return "Compra";
    }
}

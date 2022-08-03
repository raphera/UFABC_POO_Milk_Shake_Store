package com.ufabc.poo.domain;

import com.ufabc.poo.domain.abstractions.ATransacao;

public class Remocao extends ATransacao {
    public Remocao(long codigo, String nome, int quantidade, float valor) {
        super(codigo, nome, quantidade, valor, "");
    }

    public Remocao(long codigo, String nome, int quantidade, float valor, String data) {
        super(codigo, nome, quantidade, valor, data);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }

    @Override
    public String getTipo() {
        return "Remocao";
    }
}


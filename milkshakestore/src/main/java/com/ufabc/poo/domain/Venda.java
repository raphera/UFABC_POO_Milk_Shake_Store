package com.ufabc.poo.domain;

import com.ufabc.poo.domain.abstractions.ATransacao;

public class Venda extends ATransacao {
    public Venda(long codigo, String nome, int quantidade, float valor, float custo) {
        super(codigo, nome, quantidade, valor, custo, "");
    }

    public Venda(long codigo, String nome, int quantidade, float valor, float custo, String data) {
        super(codigo, nome, quantidade, valor, custo, data);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }

    @Override
    public float getCustoTotal() {
        return getQuantidade()*getCusto();
    }
    
    @Override
    public String getTipo() {
        return "Venda";
    }
}


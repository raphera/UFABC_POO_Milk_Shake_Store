package com.ufabc.poo.domain;

import com.ufabc.poo.domain.abstractions.ATransacao;

public class Compra extends ATransacao {

    public Compra(long codigo, String nome, int quantidade, float valor) {
        super(codigo, nome, quantidade, valor, valor, "");
    }

    public Compra(long codigo, String nome, int quantidade, float valor, String data) {
        super(codigo, nome, quantidade, valor, valor, data);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade() * getValor();
    }

    @Override
    public float getCustoTotal() {
        // Por se tratar de uma compra, o custo total é o mesmo do valor da compra.
        return getValorTotal();
    }

    @Override
    public String getTipo() {
        return "Compra";
    }
}

package com.ufabc.poo.domain;

import com.ufabc.poo.domain.abstractions.ATransacao;

public class Remocao extends ATransacao {
    public Remocao(long codigo, String nome, int quantidade, float valor) {
        super(codigo, nome, quantidade, valor, valor, "");
    }

    public Remocao(long codigo, String nome, int quantidade, float valor, String data) {
        super(codigo, nome, quantidade, valor, valor, data);
    }

    @Override
    public float getValorTotal() {
        // Por se tratar de uma remocao, o valor total é negativo (recuperação do
        // valor).
        return getQuantidade() * getValor() * -1;
    }

    @Override
    public float getCustoTotal() {
        return getValorTotal();
    }

    @Override
    public String getTipo() {
        return "Remocao";
    }
}

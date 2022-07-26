package com.ufabc.poo.domain.interfaces;

public abstract class ITransacao {
    private final String nome;
    private final int quantidade;
    private final float valor;
    private String data;

    public String getData() {
        return data;
    }

    protected ITransacao(String nome, int quantidade, float valor, String data) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public abstract float getValorTotal();

    public abstract String getTipo();
}

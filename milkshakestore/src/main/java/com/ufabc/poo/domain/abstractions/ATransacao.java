package com.ufabc.poo.domain.abstractions;

public abstract class ATransacao {
    private final long codigo;
    private final String nome;
    private final int quantidade;
    private final float valor;
    private final float custo;
    private String data;

    public String getData() {
        return data;
    }

    protected ATransacao(long codigo, String nome, int quantidade, float valor, float custo, String data) {
        this.codigo = codigo;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.custo = custo;
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public float getCusto(){
        return custo;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public long getCodigo() {
        return codigo;
    }

    public abstract float getValorTotal();

    public abstract float getCustoTotal();

    public abstract String getTipo();
}

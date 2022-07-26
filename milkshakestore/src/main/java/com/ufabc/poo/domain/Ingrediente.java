package com.ufabc.poo.domain;

import java.util.UUID;
public class Ingrediente {
    private UUID id;
    private String nome;
    private int quantidade;
    private float pCusto;

    public Ingrediente() {
        id = UUID.randomUUID();
    }
    
    public Ingrediente(String nome, int quantidade, float pCusto) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.quantidade = quantidade;
        this.pCusto = pCusto;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public void setPCusto(float pCusto) {
        this.pCusto = pCusto;
    }
    public UUID getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }   
    public int getQuantidade() {
        return quantidade;
    }
    public float getPCusto() {
        return pCusto;
    }
    public void aumentaQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }
}
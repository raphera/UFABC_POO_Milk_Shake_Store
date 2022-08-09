package com.ufabc.poo.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MilkShake {
    private UUID id;
    private long codigo;
    private String sabor;
    private Map<UUID, Integer> ingredientes;
    private float preco;

    public MilkShake() {
        id = UUID.randomUUID();
        ingredientes = new HashMap<>();
        this.codigo = (long) (Math.random() * 1000000000000L);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public Map<UUID, Integer> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Map<UUID, Integer> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void addIngrediente(UUID id, Integer quant) {
        ingredientes.put(id, quant);
    }

    public void remIngrediente(UUID id) {
        ingredientes.remove(id);
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}

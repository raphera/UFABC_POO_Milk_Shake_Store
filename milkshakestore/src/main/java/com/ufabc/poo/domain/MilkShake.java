package com.ufabc.poo.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MilkShake {
    private UUID id;
    private long codigo;
    private String sabor;
    private Map<UUID, Integer> Ingredientes;

    public MilkShake(String sabor) {
        Ingredientes = new HashMap<>();
        this.id = UUID.randomUUID();
        this.sabor = sabor;

        // Iniciando código com 789* para indicar que produto foi registrado no Brasil!
        this.codigo = 7890000000000L;
        // TODO: Melhorar código para gerar código único para cada produto.
        this.codigo = (long) (Math.random() * 1000000000);
    }

    public UUID getId() {
        return id;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getSabor() {
        return sabor;
    }

    public Map<UUID, Integer> getIngredientes() {
        return Ingredientes;
    }

    public void setIngredientes(Map<UUID, Integer> ingredientes) {
        Ingredientes = ingredientes;
    }

    public void addIngrediente(UUID id, Integer quant) {
        Ingredientes.put(id, quant);
    }

    public void AdicionaMP(UUID id, int quantidade) {
        Ingredientes.put(id, quantidade);
    }

    public void RemoveMP(UUID id) {
        Ingredientes.remove(id);
    }
}

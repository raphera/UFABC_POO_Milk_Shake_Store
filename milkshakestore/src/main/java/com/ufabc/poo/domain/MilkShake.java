package com.ufabc.poo.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MilkShake {
    private UUID id;
    private String sabor;
    private Map<UUID, Integer> Ingredientes;

    public MilkShake(String sabor) {
        Ingredientes = new HashMap<>();
        this.id = UUID.randomUUID();
        this.sabor = sabor;
    }
    public UUID getId() {
        return id;
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
    public void addIngrediente(UUID id, Integer quant)
    {
       Ingredientes.put(id, quant);
    }
    public void AdicionaMP(UUID id, int quantidade) {
        Ingredientes.put(id, quantidade);
    }
    public void RemoveMP(UUID id) {
        Ingredientes.remove(id);
    }
}

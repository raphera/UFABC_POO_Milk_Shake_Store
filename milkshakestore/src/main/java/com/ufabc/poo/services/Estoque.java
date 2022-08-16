package com.ufabc.poo.services;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.services.interfaces.IEstoque;
import com.ufabc.poo.services.interfaces.IPersistenceService;

@Singleton
public class Estoque implements IEstoque {
    private ArrayList<Ingrediente> Ingredientes;
    private IPersistenceService persistenceService;

    @Inject
    public Estoque(IPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        Ingredientes = persistenceService.getEstoque();
    }

    public void RemoveIng(UUID id_MP) {
        Ingredientes.removeIf(x -> x.getId().equals(id_MP));

        persistenceService.setEstoque(Ingredientes);
    }

    public void RemoveIng(Ingrediente ingrediente, int quantidade) {
        ingrediente.aumentaQuantidade(quantidade * -1);

        Ingredientes.removeIf(x -> x.getId().equals(ingrediente.getId()));
        Ingredientes.add(ingrediente);

        persistenceService.setEstoque(Ingredientes);
    }

    public void AdicionaIng(long codigo, String nome, int quantidade, float preco) {
        if (Ingredientes.stream().noneMatch(x -> x.getCodigo() == codigo))
            Ingredientes.add(new Ingrediente(codigo, nome, quantidade, preco));
        else {
            Ingrediente mp = getIng(codigo);
            mp.aumentaQuantidade(quantidade);
            mp.setPCusto(preco);
        }

        persistenceService.setEstoque(Ingredientes);
    }

    public void editIng(Ingrediente ingrediente) {
        Ingredientes.removeIf(x -> x.getId().equals(ingrediente.getId()));
        Ingredientes.add(ingrediente);

        persistenceService.setEstoque(Ingredientes);
    }

    public Ingrediente getIng(String nome) {
        return Ingredientes.stream().filter(x -> x.getNome().equals(nome)).findAny().orElse(null);
    }

    public Ingrediente getIng(UUID Id) {
        return Ingredientes.stream().filter(x -> x.getId().equals(Id)).findAny().orElse(null);
    }

    public Ingrediente getIng(long codigo) {
        return Ingredientes.stream().filter(x -> x.getCodigo() == codigo).findAny().orElse(null);
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return Ingredientes;
    }
}
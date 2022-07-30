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
        // Carregar dados do json em Ingredientes
        Ingredientes = persistenceService.getEstoque();
    }

    /* Método remove Ingrediente do estoque */
    public void RemoveMP(UUID id_MP) {
        Ingredientes.removeIf(x -> x.getId().equals(id_MP));

        // Remover do json
        persistenceService.setEstoque(Ingredientes);
    }

    public void RemoveMP(String nome, int quantidade) {
        Ingrediente mp = getMP(nome);
        mp.aumentaQuantidade(quantidade * -1);
        Ingredientes.removeIf(x -> x.getNome().equals(nome));
        Ingredientes.add(mp);

        // Remover do json
        persistenceService.setEstoque(Ingredientes);
    }

    /*
     * Método adiciona matéria prima ao estoque, verifica se já existe alguma
     * com mesmo nome, se já existir apenas atualiza os dados.
     */
    public void AdicionaMP(String nome, int quantidade, float preco) {
        if (Ingredientes.stream().noneMatch(x -> x.getNome().equals(nome)))
            Ingredientes.add(new Ingrediente(nome, quantidade, preco));
        else {
            Ingrediente mp = getMP(nome);
            mp.aumentaQuantidade(quantidade);
            mp.setPCusto(preco);
        }

        persistenceService.setEstoque(Ingredientes);
    }

    /*
     * Método retorna Ingrediente de acordo com nome,
     * caso não encontre retorna nulo.
     */
    public Ingrediente getMP(String nome) {
        return Ingredientes.stream().filter(x -> x.getNome().equals(nome)).findAny().orElse(null);
    }

    /*
     * Método retorna Ingrediente de acordo com nome,
     * caso não encontre retorna nulo.
     */
    public Ingrediente getMP(UUID Id) {
        return Ingredientes.stream().filter(x -> x.getId().equals(Id)).findAny().orElse(null);
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return Ingredientes;
    }
}
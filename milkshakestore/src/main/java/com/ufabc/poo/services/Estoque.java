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
    private ArrayList<Ingrediente> Materiais;
    private IPersistenceService persistenceService;
    @Inject
    public Estoque(IPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        //Carregar dados do json em Materiais
        Materiais = persistenceService.getEstoque();
    }

    /* Método remove Ingrediente do estoque */
    public void RemoveMP(UUID id_MP) {
        Materiais.removeIf(x -> x.getId() == id_MP);

        //Remover do json
        persistenceService.setEstoque(Materiais);
    }

    public void RemoveMP(String nome, int quantidade){
        Ingrediente mp = getMP(nome);
        mp.aumentaQuantidade(quantidade * -1);
        Materiais.removeIf(x -> x.getNome().equals(nome));
        Materiais.add(mp);

        //Remover do json
        persistenceService.setEstoque(Materiais);
    }

    /* Método adiciona matéria prima ao estoque, verifica se já existe alguma
     * com mesmo nome, se já existir apenas atualiza os dados.*/
    public void AdicionaMP(String nome, int quantidade, float preco) {
        if (Materiais.stream().noneMatch(x -> x.getNome().equals(nome)))
            Materiais.add(new Ingrediente(nome, quantidade, preco));
        else {
            Ingrediente mp = getMP(nome);
            mp.aumentaQuantidade(quantidade);
            mp.setPCusto(preco);
        }

        persistenceService.setEstoque(Materiais);
    }

    /* Método retorna Ingrediente de acordo com nome,
     * caso não encontre retorna nulo. */
    public Ingrediente getMP(String nome) {
        return Materiais.stream().filter(x -> x.getNome().equals(nome)).findAny().orElse(null);
    }

    /* Método retorna Ingrediente de acordo com nome,
     * caso não encontre retorna nulo. */
    public Ingrediente getMP(UUID Id) {
        return Materiais.stream().filter(x -> x.getId().equals(Id)).findAny().orElse(null);
    }

    public ArrayList<Ingrediente> getMateriais() {
        return Materiais;
    }
}
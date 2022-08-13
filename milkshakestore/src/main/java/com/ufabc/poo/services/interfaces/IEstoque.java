package com.ufabc.poo.services.interfaces;

import com.ufabc.poo.domain.Ingrediente;

import java.util.ArrayList;
import java.util.UUID;

public interface IEstoque {
    void RemoveIng(UUID id_Ing);

    void RemoveIng(Ingrediente nome, int quantidade);

    void AdicionaIng(long codigo, String nome, int quantidade, float preco);

    void editIng(Ingrediente ingrediente);

    Ingrediente getIng(String nome);

    Ingrediente getIng(UUID Id);

    ArrayList<Ingrediente> getIngredientes();
}

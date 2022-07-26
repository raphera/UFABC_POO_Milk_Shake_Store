package com.ufabc.poo.services.interfaces;

import com.ufabc.poo.domain.Ingrediente;

import java.util.ArrayList;
import java.util.UUID;

public interface IEstoque {
    void RemoveMP(UUID id_MP);

    void RemoveMP(String nome, int quantidade);

    void AdicionaMP(String nome, int quantidade, float preco);

    Ingrediente getMP(String nome);

    Ingrediente getMP(UUID Id);

    ArrayList<Ingrediente> getMateriais();
}

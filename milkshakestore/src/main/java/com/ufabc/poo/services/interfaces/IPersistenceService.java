package com.ufabc.poo.services.interfaces;

import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.domain.MilkShake;

import java.util.ArrayList;

public interface IPersistenceService {
    ArrayList<Ingrediente> getEstoque();

    ArrayList<MilkShake> getBancoMilkShakes();

    void setEstoque(ArrayList<Ingrediente> ingredientes);

    void setBancoMilkShakes(ArrayList<MilkShake> milkShakes);
}

package com.ufabc.poo.services.interfaces;

import com.ufabc.poo.domain.MilkShake;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public interface IBancoDeMilkShakes {
    ArrayList<MilkShake> getMilkShakes();

    MilkShake getMilkShake(UUID Id);

    MilkShake getMilkShake(String NomeMilkShake);

    float ObterPreco(UUID Id);

    float ObterPreco(String NomeMilkShake);

    void AdicionaMilkShake(MilkShake MilkShake);

    void RemoveMilkShake(UUID Id);

    void RemoveMilkShake(String NomeMilkShake);

    int attDisp(UUID Id);

    int obterDisp(UUID Id);

    Map<UUID, Integer> obterDispAll();
}

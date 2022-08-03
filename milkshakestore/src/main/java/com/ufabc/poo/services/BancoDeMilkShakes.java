package com.ufabc.poo.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.ufabc.poo.services.interfaces.IEstoque;
import com.ufabc.poo.services.interfaces.IPersistenceService;

import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;

@Singleton
public class BancoDeMilkShakes implements IBancoDeMilkShakes {

    private final IEstoque estoque;
    private final IPersistenceService persistenceService;
    private ArrayList<MilkShake> MilkShakes;
    private Map<UUID, Integer> produtosFinais;

    @Inject
    public BancoDeMilkShakes(IEstoque estoque, IPersistenceService persistenceService) {
        this.estoque = estoque;
        this.persistenceService = persistenceService;
        this.produtosFinais = new HashMap<>();
        MilkShakes = new ArrayList<>();
        MilkShakes = persistenceService.getBancoMilkShakes();
    }

    public ArrayList<MilkShake> getMilkShakes() {
        return MilkShakes;
    }

    public MilkShake getMilkShake(UUID Id) {
        MilkShake retorno = MilkShakes.stream().filter(x -> x.getId().equals(Id)).findAny().orElse(null);
        return retorno;
    }

    public MilkShake getMilkShake(String NomeMilkShake) {
        MilkShake retorno = MilkShakes.stream().filter(x -> x.getSabor().equals(NomeMilkShake)).findAny()
                .orElse(null);
        return retorno;
    }

    public float ObterPreco(UUID Id) {
        return getMilkShake(Id).getIngredientes().entrySet().stream()
                .map(x -> estoque.getIng(x.getKey()).getPCusto() * x.getValue()).reduce((float) 0.0, (x, y) -> x + y);
    }

    public float ObterPreco(String NomeMilkShake) {
        return getMilkShake(NomeMilkShake).getIngredientes().entrySet().stream()
                .map(x -> estoque.getIng(x.getKey()).getPCusto() * x.getValue()).reduce((float) 0.0, (x, y) -> x + y);
    }

    public void AdicionaMilkShake(MilkShake MilkShake) {
        MilkShakes.removeIf(x -> x.getSabor().equals(MilkShake.getSabor()));
        MilkShakes.add(MilkShake);
        persistenceService.setBancoMilkShakes(MilkShakes);
    }

    public void RemoveMilkShake(UUID Id) {
        MilkShakes.removeIf(x -> x.getId().equals(Id));
        persistenceService.setBancoMilkShakes(MilkShakes);
    }

    public void RemoveMilkShake(String NomeMilkShake) {
        MilkShakes.removeIf(x -> x.getSabor().equals(NomeMilkShake));
        persistenceService.setBancoMilkShakes(MilkShakes);
    }

    public int attDisp(UUID Id) {
        return getMilkShake(Id)
                .getIngredientes()
                .entrySet()
                .stream()
                .map(x -> estoque.getIng(x.getKey()).getQuantidade() / x.getValue())
                .min(Comparator.comparing(Float::valueOf))
                .orElse(0);
    }

    public int obterDisp(UUID Id) {
        return this.attDisp(Id);
    }

    public Map<UUID, Integer> obterDispAll() {
        return produtosFinais;
    }
}
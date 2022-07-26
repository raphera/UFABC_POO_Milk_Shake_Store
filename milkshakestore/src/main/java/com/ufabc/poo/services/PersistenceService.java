package com.ufabc.poo.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.domain.MilkShake;
import javafx.scene.control.Alert;
import com.ufabc.poo.services.interfaces.IPersistenceService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class PersistenceService implements IPersistenceService {
    @Override
    public ArrayList<Ingrediente> getEstoque() {
        return getJsonEstoque("Estoque.json");
    }

    @Override
    public ArrayList<MilkShake> getBancoMilkShakes() {
        return getJsonMilkShakes("BancoMilkShakes.json");
    }

    @Override
    public void setEstoque(ArrayList<Ingrediente> Ingredientes) {
        setJSON(Ingredientes, "Estoque.json");
    }

    @Override
    public void setBancoMilkShakes(ArrayList<MilkShake> MilkShakes) {
        setJSON(MilkShakes, "BancoMilkShakes.json");
    }

    private void setJSON(Object src, String arquivo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(src);

        try {
            FileWriter writer = new FileWriter(arquivo);
            writer.write(json);
            writer.close();

        } catch (Exception e) {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Erro Inesperado");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText(e.getMessage());
            dialogoInfo.showAndWait();
            e.printStackTrace();
        }
    }

    private ArrayList<MilkShake> getJsonMilkShakes(String arquivo)
    {
        Gson gson = new Gson();
        ArrayList<MilkShake> obj = null;
        try {
            obj = gson.fromJson(new BufferedReader(new FileReader(arquivo)), new TypeToken<ArrayList<MilkShake>>() {}.getType());
        }
        finally {
            if(obj == null)
                return new ArrayList<MilkShake>();
            return obj;
        }
    }

    private ArrayList<Ingrediente> getJsonEstoque(String arquivo)
    {
        Gson gson = new Gson();
        ArrayList<Ingrediente> obj = null;
        try {
            obj = gson.fromJson(new BufferedReader(new FileReader(arquivo)), new TypeToken<ArrayList<Ingrediente>>() {}.getType());
        }
        finally {
            if(obj == null)
                return new ArrayList<Ingrediente>();
            return obj;
        }
    }
}

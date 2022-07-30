package com.ufabc.poo.services;

import com.fasterxml.jackson.core.json.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(src);
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

    private ArrayList<MilkShake> getJsonMilkShakes(String arquivo) {

        ArrayList<MilkShake> obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(new FileReader(arquivo), new TypeReference<ArrayList<MilkShake>>(){});
        } finally {
            if (obj == null)
                return new ArrayList<MilkShake>();
            return obj;
        }
    }

    private ArrayList<Ingrediente> getJsonEstoque(String arquivo) {
        ArrayList<Ingrediente> obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(new FileReader(arquivo), new TypeReference<ArrayList<Ingrediente>>(){});
        } finally {
            if (obj == null)
                return new ArrayList<Ingrediente>();
            return obj;
        }
    }
}

package com.ufabc.poo.services;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.services.interfaces.IPersistenceService;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class PersistenceService implements IPersistenceService {
    @Override
    public ArrayList<Ingrediente> getEstoque() {
        return getJsonIngredientes("Estoque.json");
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
            dialogoInfo.initStyle(StageStyle.UTILITY);
            dialogoInfo.setTitle("Erro Inesperado");
            dialogoInfo.setHeaderText("Erro ao salvar arquivo de persistência.");
            dialogoInfo.setContentText(e.getMessage());
            dialogoInfo.showAndWait();
            e.printStackTrace();
        }
    }

    private ArrayList<MilkShake> getJsonMilkShakes(String arquivo) {
        ArrayList<MilkShake> obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(new FileReader(arquivo), new TypeReference<ArrayList<MilkShake>>() {
            });
        } catch (Exception e) {
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.initStyle(StageStyle.UTILITY);
            dialogoInfo.setTitle("Aviso");
            dialogoInfo.setHeaderText("Não foi possível ler o arquivo de MilkShakes.");
            dialogoInfo.setContentText("Caso seja uma nova instalação, o aviso poderá ser dispensado.");
            dialogoInfo.showAndWait();
        } finally {
            if (obj == null)
                obj = new ArrayList<MilkShake>();
        }

        return obj;
    }

    private ArrayList<Ingrediente> getJsonIngredientes(String arquivo) {
        ArrayList<Ingrediente> obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(new FileReader(arquivo), new TypeReference<ArrayList<Ingrediente>>() {
            });
        } catch (Exception e) {
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.initStyle(StageStyle.UTILITY);
            dialogoInfo.setTitle("Aviso");
            dialogoInfo.setHeaderText("Não foi possível ler o arquivo de Ingredientes.");
            dialogoInfo.setContentText("Caso seja uma nova instalação, o aviso poderá ser dispensado.");
            dialogoInfo.showAndWait();
        } finally {
            if (obj == null)
                obj = new ArrayList<Ingrediente>();
        }

        return obj;
    }
}

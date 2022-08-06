package com.ufabc.poo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.services.interfaces.IEstoque;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SelIngredienteController implements Initializable {
    private final IEstoque estoque;
    private ArrayList<String> mps = new ArrayList<>();

    @FXML
    private TextField fieldMP;

    @FXML
    TextField fielQtd;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoAdicionar;

    @Inject
    public SelIngredienteController(IEstoque estoque) {
        this.estoque = estoque;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Ingrediente x : estoque.getIngredientes()) {
            mps.add(x.getNome());
        }

        //Textfields.bindAutoCompletion(fieldMP, mps).setMaxWidth(90);
    }

    @FXML
    public void botaoAdicionar() {
        if (!fieldMP.getText().isEmpty() && !fielQtd.getText().isEmpty()) {
            if(estoque.getIng(fieldMP.getText()) == null)
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Matéria prima");
                dialogoInfo.setHeaderText("Matéria prima não existente");
                dialogoInfo.setContentText("Favor realizar a compra da matéria prima antes\nde adicionar a receita.");
                dialogoInfo.showAndWait();
                fieldMP.setText("");
                fielQtd.setText("");
            }
            else {
                NovoSaborController.IngredientesNovaMilkShake.removeIf(x -> x.getProduto().equals(fieldMP.getText()));
                NovoSaborController.IngredientesNovaMilkShake.add(new NovoSaborController.NovoMP(fieldMP.getText(), fielQtd.getText()));

                Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void botaoCancelar() {
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }
}

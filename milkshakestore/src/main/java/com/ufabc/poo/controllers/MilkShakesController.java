package com.ufabc.poo.controllers;

import com.ufabc.poo.App;
import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.helpers.DI;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class MilkShakesController implements Initializable {
    private final IBancoDeMilkShakes milkshakes;
    public static MilkShake selectedMilkShake;

    // Injeta o estoque
    @Inject
    public MilkShakesController(IBancoDeMilkShakes milkshakes) {
        this.milkshakes = milkshakes;
    }

    @FXML
    private TableView<MilkShake> tbData;

    @FXML
    public TableColumn<MilkShake, String> colNome;

    @FXML
    public TableColumn<MilkShake, String> colPreco;

    @FXML
    TableColumn<MilkShake, String> colDisp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSabor()));
        colPreco.setCellValueFactory(
                c -> new SimpleStringProperty(Float.toString(milkshakes.ObterPreco(c.getValue().getSabor()))));
        colDisp.setCellValueFactory(
                c -> new SimpleStringProperty(Integer.toString(milkshakes.obterDisp(c.getValue().getId()))));

        tbData.getItems().addAll(milkshakes.getMilkShakes());
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(milkshakes.getMilkShakes());
        tbData.getSortOrder().add(colNome);
    }

    @FXML
    public void adicionarSab() {
        try {
            selectedMilkShake = null;
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("NovoSabor.fxml"));
            fxmlLoader.setControllerFactory(DI.injector::getInstance);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Criar sabor");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                reloadList();
            });

        } catch (Exception e) {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Erro Inesperado");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText(e.getMessage());
            dialogoInfo.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void editSab() {
        try {
            selectedMilkShake = tbData.getSelectionModel().getSelectedItem();
            if (selectedMilkShake != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("NovoSabor.fxml"));
                fxmlLoader.setControllerFactory(DI.injector::getInstance);
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Editar sabor");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                stage.setOnHiding(event -> {
                    reloadList();
                });
            } else {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Editar sabor");
                dialogoInfo.setHeaderText("Nenhum sabor selecionada");
                dialogoInfo.setContentText("Você deve selecionar um sabor para poder\neditar.");
                dialogoInfo.showAndWait();
            }

        } catch (Exception e) {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Erro Inesperado");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText(e.getMessage());
            dialogoInfo.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void removeSab() {
        if (tbData.getSelectionModel().getSelectedItem() != null) {
            milkshakes.RemoveMilkShake(tbData.getSelectionModel().getSelectedItem().getId());
            reloadList();
        } else {
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Editar MilkShake");
            dialogoInfo.setHeaderText("Nenhuma MilkShake selecionada");
            dialogoInfo.setContentText("Você deve selecionar uma MilkShake para poder\nremover.");
            dialogoInfo.showAndWait();
        }
    }
}
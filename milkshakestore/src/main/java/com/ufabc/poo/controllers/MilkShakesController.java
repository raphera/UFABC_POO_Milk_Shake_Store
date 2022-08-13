package com.ufabc.poo.controllers;

import com.ufabc.poo.App;
import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.domain.Venda;
import com.ufabc.poo.helpers.DI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;
import com.ufabc.poo.services.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class MilkShakesController implements Initializable {
    private final ITranscaoService transacao;
    private final IBancoDeMilkShakes milkshakes;

    // Injeta o estoque
    @Inject
    public MilkShakesController(IBancoDeMilkShakes milkshakes, ITranscaoService transacao) {
        this.milkshakes = milkshakes;
        this.transacao = transacao;
    }

    @FXML
    private TableView<MilkShake> tbData;

    @FXML
    public TableColumn<MilkShake, String> colNome, colCodigo, colPreco, colDisp;

    @FXML
    private Button editBtn, removeBtn, regVendaBtn;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(Long.toString(c.getValue().getCodigo())));
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSabor()));
        colPreco.setCellValueFactory(
                c -> new SimpleStringProperty(
                        String.format("R$ %.2f", c.getValue().getPreco())));
        colDisp.setCellValueFactory(
                c -> new SimpleStringProperty(Integer.toString(milkshakes.obterDisp(c.getValue().getId()))));

        tbData.getItems().addAll(milkshakes.getMilkShakes());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            search();
        });

        editBtn.disableProperty().bind(tbData.getSelectionModel().selectedItemProperty().isNull());
        removeBtn.disableProperty().bind(tbData.getSelectionModel().selectedItemProperty().isNull());
        regVendaBtn.disableProperty().bind(tbData.getSelectionModel().selectedItemProperty().isNull());
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(milkshakes.getMilkShakes());
        tbData.getSortOrder().add(colNome);
    }

    @FXML
    public void regVenda() {
        try {
            MilkShake vendaMilkShake = tbData.getSelectionModel().getSelectedItem();
            if (vendaMilkShake != null) {
                if (milkshakes.obterDisp(vendaMilkShake.getId()) > 0) {
                    transacao.efetuaVenda(
                            new Venda(vendaMilkShake.getCodigo(), vendaMilkShake.getSabor(), 1,
                                    vendaMilkShake.getPreco()));

                    reloadList();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erro");
                    alert.setHeaderText(
                            "Esse sabor não está disponível mais disponível.\nNão há ingredientes suficientes no estoque.");
                    alert.showAndWait();
                }

            } else {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Registrar Venda");
                dialogoInfo.setHeaderText("Nenhum sabor de milkshake selecionado.");
                dialogoInfo.setContentText("Você deve selecionar um sabor para registrar a venda.");
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
    public void adicionarSab() {
        try {
            NovoSaborController.selectedMilkShake = null;
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
            NovoSaborController.selectedMilkShake = tbData.getSelectionModel().getSelectedItem();
            if (NovoSaborController.selectedMilkShake != null) {
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
                dialogoInfo.setHeaderText("Nenhum sabor selecionado");
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
            dialogoInfo.setHeaderText("Nenhuma MilkShake selecionado");
            dialogoInfo.setContentText("Você deve selecionar uma MilkShake para poder\nremover.");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void search() {
        String keyword = searchField.getText();
        if (keyword.equals("")) {
            tbData.getItems().clear();
            tbData.getItems().addAll(milkshakes.getMilkShakes());
        } else {
            ObservableList<MilkShake> filteredData = FXCollections.observableArrayList();
            for (MilkShake ingrediente : milkshakes.getMilkShakes()) {
                if (ingrediente.getSabor().toUpperCase().contains(keyword.toUpperCase())
                        || String.valueOf(ingrediente.getCodigo()).contains(keyword)) {
                    filteredData.add(ingrediente);
                }

                tbData.setItems(filteredData);
            }
        }
    }
}
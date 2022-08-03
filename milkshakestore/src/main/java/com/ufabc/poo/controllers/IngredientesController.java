package com.ufabc.poo.controllers;

import com.ufabc.poo.App;
import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.helpers.DI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;
import com.ufabc.poo.services.interfaces.IEstoque;
import com.ufabc.poo.services.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class IngredientesController implements Initializable {
    private final ITranscaoService transacao;
    private final IEstoque estoque;

    @Inject
    public IngredientesController(IEstoque estoque, ITranscaoService transacao, IBancoDeMilkShakes receitas) {
        this.transacao = transacao;
        this.estoque = estoque;
    }

    @FXML
    TableView<Ingrediente> tbData;

    @FXML
    TableColumn<Ingrediente, String> colCodigo;

    @FXML
    TableColumn<Ingrediente, String> colNome;

    @FXML
    TableColumn<Ingrediente, String> colPreco;

    @FXML
    TableColumn<Ingrediente, String> colQtd;

    @FXML
    TextField searchField;

    @FXML
    Button editBtn, removeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(Long.toString(c.getValue().getCodigo())));
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colPreco.setCellValueFactory(c -> new SimpleStringProperty(Float.toString(c.getValue().getPCusto())));
        colQtd.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().getQuantidade())));

        reloadList();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            search();
        });

        editBtn.disableProperty().bind(tbData.getSelectionModel().selectedItemProperty().isNull());
        removeBtn.disableProperty().bind(tbData.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void search() {
        String keyword = searchField.getText();
        if (keyword.equals("")) {
            tbData.getItems().clear();
            tbData.getItems().addAll(estoque.getIngredientes());
        } else {
            ObservableList<Ingrediente> filteredData = FXCollections.observableArrayList();
            for (Ingrediente ingrediente : estoque.getIngredientes()) {
                if (ingrediente.getNome().toUpperCase().contains(keyword.toUpperCase())
                        || String.valueOf(ingrediente.getCodigo()).contains(keyword)) {
                    filteredData.add(ingrediente);
                }

                tbData.setItems(filteredData);
            }
        }
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(estoque.getIngredientes());
        tbData.getSortOrder().add(colNome);
    }

    @FXML
    public void adicionarIng() {
        CompraIngredienteController.selectedIngrediente = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CompraIngrediente.fxml"));
            fxmlLoader.setControllerFactory(DI.injector::getInstance);
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Comprar Produto");
            stage.setScene(new Scene(root1));
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
    public void removeIng() {
        if (tbData.getSelectionModel().getSelectedItem() != null) {
            transacao.removeCompra(tbData.getSelectionModel().getSelectedItem().getId());
            reloadList();
        } else {
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Remover Produto");
            dialogoInfo.setHeaderText("Nenhum produto selecionado");
            dialogoInfo.setContentText("Você deve selecionar um produto para poder\nremover.");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    public void editIng() {
        CompraIngredienteController.selectedIngrediente = tbData.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CompraIngrediente.fxml"));
            fxmlLoader.setControllerFactory(DI.injector::getInstance);
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Comprar Produto");
            stage.setScene(new Scene(root1));
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

}
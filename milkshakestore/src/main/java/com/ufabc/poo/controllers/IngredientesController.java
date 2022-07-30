package com.ufabc.poo.controllers;

import com.ufabc.poo.App;
import com.ufabc.poo.domain.Ingrediente;
import com.ufabc.poo.helpers.DI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView<Ingrediente> tbData;

    @FXML
    public TableColumn<Ingrediente, String> nome;

    @FXML
    public TableColumn<Ingrediente, String> preco;

    @FXML
    TableColumn<Ingrediente, String> qtd;

    @FXML
    TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        preco.setCellValueFactory(c -> new SimpleStringProperty(Float.toString(c.getValue().getPCusto())));
        qtd.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().getQuantidade())));

        tbData.getItems().addAll(estoque.getIngredientes());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            search();
        });
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
                if (ingrediente.getNome().toUpperCase().contains(keyword.toUpperCase()))
                    filteredData.add(ingrediente);
            }

            tbData.setItems(filteredData);
        }
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(estoque.getIngredientes());
        tbData.getSortOrder().add(nome);
    }

    @FXML
    public void adicionarMP() {
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
    public void removeMP() {
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
}
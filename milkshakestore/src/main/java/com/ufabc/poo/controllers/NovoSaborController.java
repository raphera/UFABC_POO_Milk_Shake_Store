package com.ufabc.poo.controllers;

import com.ufabc.poo.App;
import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.helpers.DI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;
import com.ufabc.poo.services.interfaces.IEstoque;

import javax.inject.Inject;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class NovoSaborController implements Initializable {
    private final IEstoque estoque;
    private final IBancoDeMilkShakes MilkShakes;
    public static ObservableList<NovoIng> IngredientesNovaMilkShake = FXCollections.observableArrayList();
    

    //Injeta o estoque
    @Inject
    public NovoSaborController(IEstoque estoque, IBancoDeMilkShakes MilkShakes) {
        this.estoque = estoque;
        this.MilkShakes = MilkShakes;
        IngredientesNovaMilkShake.clear();
    }

    @FXML
    private TableView<NovoIng> tbData;

    @FXML
    public TableColumn<NovoIng, String> colIng;

    @FXML
    public TableColumn<NovoIng, String> colQtd;

    @FXML
    public TextField nomeMilkShake;

    @FXML
    public Button botaoCriarMilkShake;

    @FXML
    public Button botaoCancelar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Coluna 1 - Nome do produto
        colIng.setCellValueFactory(new PropertyValueFactory<>("colIng"));

        // Coluna 2 - quantidade de produto
        colQtd.setCellValueFactory(new PropertyValueFactory<>("colQtd"));

        tbData.setItems(IngredientesNovaMilkShake);
        tbData.getColumns().clear();
        tbData.getColumns().addAll(colIng, colQtd);

        if(MilkShakesController.selectedMilkShake != null)
            editarSabor(MilkShakesController.selectedMilkShake);
    }

    @FXML
    public void criarSabor(){
        if (nomeMilkShake.getText() != null) {
            MilkShake nova = new MilkShake(nomeMilkShake.getText());

            if (!IngredientesNovaMilkShake.isEmpty()) {
                for (NovoIng x : IngredientesNovaMilkShake)
                    nova.addIngrediente(estoque.getIng(x.getProduto()).getId(), Integer.parseInt(x.getQtd()));
                MilkShakes.AdicionaMilkShake(nova);

                Stage stage = (Stage) botaoCriarMilkShake.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void botaoCancelar() {
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void adicionarIng() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("SelIngrediente.fxml"));
            fxmlLoader.setControllerFactory(DI.injector::getInstance);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Selecionar Ingrediente");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
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
    public void removerIng() {
        IngredientesNovaMilkShake.remove(tbData.getSelectionModel().getSelectedItem());
    }

    public void editarSabor(MilkShake MilkShake){
        nomeMilkShake.setText(MilkShake.getSabor());

        for(Map.Entry<UUID, Integer> x : MilkShake.getIngredientes().entrySet()){
            NovoIng mp = new NovoSaborController.NovoIng(estoque.getIng(x.getKey()).getNome(), Integer.toString(x.getValue()));
            IngredientesNovaMilkShake.add(mp);
        }
    }

    public static class NovoIng {

        private final SimpleStringProperty ingrediente;
        private final SimpleStringProperty qtd;

        public NovoIng(String produto, String qtd) {
            this.ingrediente = new SimpleStringProperty(produto);
            this.qtd = new SimpleStringProperty(qtd);
        }

        public String getProduto() {
            return ingrediente.get();
        }

        public void setProduto(String nome) {
            this.ingrediente.set(nome);
        }

        public String getQtd() {
            return qtd.get();
        }

        public void setQtd(String qtd) {
            this.qtd.set(qtd);
        }
    }
}
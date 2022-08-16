package com.ufabc.poo.controllers;

import com.ufabc.poo.App;
import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.helpers.DI;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;
import com.ufabc.poo.services.interfaces.IEstoque;

import javax.inject.Inject;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class NovoSaborController implements Initializable {
    private final IEstoque estoque;
    private final IBancoDeMilkShakes MilkShakes;
    public static ObservableList<NovoIng> IngredientesNovoMilkShake = FXCollections.observableArrayList();
    public static MilkShake selectedMilkShake;

    @FXML
    private TableView<NovoIng> tbData;

    @FXML
    public TableColumn<NovoIng, String> colIng, colQtd;

    @FXML
    public TextField nomeMilkShake, fieldPreco;

    @FXML
    public Button botaoCriarMilkShake, botaoCancelar;

    @FXML
    private Label labelSugPrice;

    @Inject
    public NovoSaborController(IEstoque estoque, IBancoDeMilkShakes MilkShakes) {
        this.estoque = estoque;
        this.MilkShakes = MilkShakes;
        IngredientesNovoMilkShake.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Coluna 1 - Nome do produto
        colIng.setCellValueFactory(new PropertyValueFactory<NovoIng, String>("colIng"));

        // Coluna 2 - quantidade de produto
        colQtd.setCellValueFactory(new PropertyValueFactory<NovoIng, String>("colQtd"));

        tbData.setItems(IngredientesNovoMilkShake);

        // Adiciona um listener para atualizar o preço sugerido do novo sabor de
        // milkshake
        IngredientesNovoMilkShake.addListener((ListChangeListener<NovoIng>) c -> {
            float precoSugerido = 0;

            for (NovoIng x : IngredientesNovoMilkShake)
                precoSugerido += estoque.getIng(x.getProduto()).getPCusto() * Integer.parseInt(x.getQtd());

            // imprimir preço com duas casas
            labelSugPrice.setText(String.format("R$ %.2f", precoSugerido));
        });

        fieldPreco.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("[0-9,.]*") ? c : null));

        if (selectedMilkShake != null)
            editarSabor(selectedMilkShake);
    }

    @FXML
    public void criarSabor() {

        if (nomeMilkShake.getText() != null) {
            MilkShake milkshake;

            if (selectedMilkShake == null) {
                milkshake = new MilkShake();
            } else {
                milkshake = selectedMilkShake;
                milkshake.getIngredientes().clear();
            }

            milkshake.setSabor(nomeMilkShake.getText());

            if (!IngredientesNovoMilkShake.isEmpty()) {
                for (NovoIng x : IngredientesNovoMilkShake)
                    milkshake.addIngrediente(estoque.getIng(x.getProduto()).getId(), Integer.parseInt(x.getQtd()));
            }

            try {
                milkshake.setPreco(NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(fieldPreco.getText())
                        .floatValue());
            } catch (ParseException e) {
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.initStyle(StageStyle.UTILITY);
                dialogoInfo.setTitle("Novo Ingrediente");
                dialogoInfo.setHeaderText("Formato de preço inválido");
                dialogoInfo.setContentText("O preço deve ser um número válido, com \",\" como separador de decimal.");
                dialogoInfo.showAndWait();

                e.printStackTrace();
            }

            MilkShakes.AdicionaMilkShake(milkshake);

            Stage stage = (Stage) botaoCriarMilkShake.getScene().getWindow();
            stage.close();
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
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.initStyle(StageStyle.UTILITY);
            dialogoInfo.setTitle("Erro Inesperado");
            dialogoInfo.setHeaderText("Erro ao abrir tela de seleção de ingrediente");
            dialogoInfo.setContentText(e.getMessage());
            dialogoInfo.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void removerIng() {
        IngredientesNovoMilkShake.remove(tbData.getSelectionModel().getSelectedItem());
    }

    public void editarSabor(MilkShake MilkShake) {
        nomeMilkShake.setText(MilkShake.getSabor());

        for (Map.Entry<UUID, Integer> x : MilkShake.getIngredientes().entrySet()) {
            NovoIng mp = new NovoSaborController.NovoIng(estoque.getIng(x.getKey()).getNome(),
                    Integer.toString(x.getValue()));
            IngredientesNovoMilkShake.add(mp);
        }

        fieldPreco.setText(String.format("%.2f", MilkShake.getPreco()));
    }

    public static class NovoIng {

        private final StringProperty colIng;
        private final StringProperty colQtd;

        public NovoIng(String colIng, String colQtd) {
            this.colIng = new SimpleStringProperty(colIng);
            this.colQtd = new SimpleStringProperty(colQtd);
        }

        public final StringProperty colIngProperty() {
            return this.colIng;
        }

        public final String getProduto() {
            return colIng.get();
        }

        public final void setProduto(String nome) {
            this.colIng.set(nome);
        }

        public final StringProperty colQtdProperty() {
            return this.colQtd;
        }

        public final String getQtd() {
            return colQtd.get();
        }

        public final void setQtd(String qtd) {
            this.colQtd.set(qtd);
        }
    }
}
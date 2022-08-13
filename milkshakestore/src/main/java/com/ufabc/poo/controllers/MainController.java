package com.ufabc.poo.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ufabc.poo.App;
import com.ufabc.poo.helpers.DI;

public class MainController implements Initializable {

    @FXML
    private AnchorPane opacityPane, drawerPane;

    @FXML
    Pane mainPane;

    @FXML
    private Label drawerImage;

    @FXML
    private ImageView exit, menuIcon;

    @FXML
    private Button ingredientesButton, milkShakesButton, relatoriosButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        opacityPane.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), drawerPane);
        // translateTransition.setByX(-600);
        translateTransition.setToX(-600);
        translateTransition.play();

        drawerImage.setOnMouseClicked(event -> {
            opacityPane.setVisible(true);

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
            translateTransition1.setToX(0);
            translateTransition1.play();
            menuIcon.setImage(new Image(App.class.getResourceAsStream("imagens/menu_open.png")));

        });

        opacityPane.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                opacityPane.setVisible(false);
            });

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
            translateTransition1.setByX(-600);
            translateTransition1.play();

            menuIcon.setImage(new Image(App.class.getResourceAsStream("imagens/menu.png")));

        });

        ingredientesButton.setOnMouseClicked(event -> {
            Pane newLoadedPane;

            try {
                FXMLLoader IngredientesScreenLoader = new FXMLLoader();
                IngredientesScreenLoader.setLocation(App.class.getResource("Ingredientes.fxml"));
                IngredientesScreenLoader.setControllerFactory(DI.injector::getInstance);
                newLoadedPane = IngredientesScreenLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            // mainPane.getChildren().clear();
            mainPane.getChildren().setAll(newLoadedPane);
        });

        milkShakesButton.setOnMouseClicked(event -> {
            Pane newLoadedPane;

            try {
                FXMLLoader IngredientesScreenLoader = new FXMLLoader();
                IngredientesScreenLoader.setLocation(App.class.getResource("MilkShakes.fxml"));
                IngredientesScreenLoader.setControllerFactory(DI.injector::getInstance);
                newLoadedPane = IngredientesScreenLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            // mainPane.getChildren().clear();
            mainPane.getChildren().setAll(newLoadedPane);
        });

        relatoriosButton.setOnMouseClicked(event -> {
            Pane newLoadedPane;

            try {
                FXMLLoader IngredientesScreenLoader = new FXMLLoader();
                IngredientesScreenLoader.setLocation(App.class.getResource("Relatorios.fxml"));
                IngredientesScreenLoader.setControllerFactory(DI.injector::getInstance);
                newLoadedPane = IngredientesScreenLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            // mainPane.getChildren().clear();
            mainPane.getChildren().setAll(newLoadedPane);
        });

    }
}

package com.ufabc.poo;

import com.ufabc.poo.helpers.DI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    double x, y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainScreenLoader = new FXMLLoader();
        mainScreenLoader.setLocation(App.class.getResource("Main.fxml"));
        mainScreenLoader.setControllerFactory(DI.injector::getInstance);

        Parent root = mainScreenLoader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
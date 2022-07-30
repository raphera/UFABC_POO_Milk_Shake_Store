package com.ufabc.poo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Timestamp;

import com.ufabc.poo.domain.Compra;
import com.ufabc.poo.domain.MilkShake;
import com.ufabc.poo.domain.Venda;
import com.ufabc.poo.helpers.DI;
import com.ufabc.poo.services.BancoDeMilkShakes;
import com.ufabc.poo.services.Estoque;
import com.ufabc.poo.services.TransacaoService;

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
        // BancoDeMilkShakes a = DI.injector.getInstance(BancoDeMilkShakes.class);
        // Estoque e = DI.injector.getInstance(Estoque.class);
        // TransacaoService t = DI.injector.getInstance(TransacaoService.class);

        // t.efetuaCompra(new Compra("Cafe", 5, 4));
        // t.efetuaCompra(new Compra("Leite", 10, 6));
        // t.efetuaCompra(new Compra("Avelã", 15, 8));

        // MilkShake cafecleite = new MilkShake("cafecomleite");

        // cafecleite.addIngrediente(e.getMP("Cafe").getId(), 1);
        // cafecleite.addIngrediente(e.getMP("Leite").getId(), 1);

        // a.AdicionaMilkShake(cafecleite);

        // t.efetuaVenda(new Venda("cafecomleite", 1, 15));

        // var co = t.getCompras(new Timestamp(System.currentTimeMillis()));
        // var ce = t.getVendas(new Timestamp(System.currentTimeMillis()));

        launch(args);
    }

}
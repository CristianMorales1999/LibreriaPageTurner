package com.pageturner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("PageTurner");
        stage.setScene(scene);

        /* 🔥 Tamaño fijo */
        stage.setWidth(1000);
        stage.setHeight(600);

        /* 🔥 BLOQUEAR redimension */
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
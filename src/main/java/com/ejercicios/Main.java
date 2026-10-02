package com.ejercicios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    final int WINDOW_WIDTH = 300;
    final int WINDOW_HEIGHT = 350;

    @Override
    public void start(Stage stage) throws Exception {

        // Carrega la vista inicial des del fitxer FXML
        Parent root = FXMLLoader.load(getClass().getResource("/assets/layout.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Calculadora");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setResizable(false);
        stage.show();

        // Afegeix una icona només si no és un Mac
        if (!System.getProperty("os.name").contains("Mac")) {
            var iconUrl = getClass().getResource("/icons/icon.png");
            if (iconUrl != null) {
                stage.getIcons().add(new Image(iconUrl.toExternalForm()));
            } else {
                System.err.println("Icon not found: /icons/icon.png");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

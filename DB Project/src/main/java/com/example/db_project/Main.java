package com.example.db_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image appIcon = new Image("file:resources/ahly logo.png");
        stage.getIcons().add(appIcon);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("homeScreen.fxml"));
        Scene homePage = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("AL Ahly jr's ");
        stage.setResizable(false);
        stage.setScene(homePage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
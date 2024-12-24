package com.example.db_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        

       FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("homeScreen.fxml"));
        Scene homePage = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("AL Ahly jr's ");
        stage.setResizable(false);
        stage.setScene(homePage);

        stage.show();
    }

    public static void main(String[] args) {
        Connection conn = DatabaseConnector.connect();
        if (conn != null) {
            System.out.println("Database connection successful!");
        } else {
            System.out.println("Database connection failed!");
        }
        launch();

    }
}





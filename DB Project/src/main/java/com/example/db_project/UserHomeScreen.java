package com.example.db_project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserHomeScreen implements Initializable {

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane Slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set initial position of the slider
        Slider.setTranslateX(-200);

        // Bind methods to the Menu and MenuBack clicks
        Menu.setOnMouseClicked(event -> openMenu());
        MenuBack.setOnMouseClicked(event -> closeMenu());
    }

    // Method to open the menu
    private void openMenu() {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.4), Slider);
        slide.setToX(0);
        slide.play();

        slide.setOnFinished(e -> {
            Menu.setVisible(false);
            MenuBack.setVisible(true);
        });
    }

    // Method to close the menu
    private void closeMenu() {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.4), Slider);
        slide.setToX(-200);
        slide.play();

        slide.setOnFinished(e -> {
            Menu.setVisible(true);
            MenuBack.setVisible(false);
        });
    }

    @FXML
    private void SwitchToSub(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user fxml/UserSub.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMember(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user fxml/UserHomeMember.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void Logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homeScreen.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

package com.example.db_project;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class OwnerHomeScreen  implements Initializable {
    @FXML
    private Button CoachesAssignment;

    @FXML
    private Button Expenses;

    @FXML
    private Label MenuBack;

    @FXML
    private Label MenuOpen;

    @FXML
    private Button Reporting;

    @FXML
    private AnchorPane Slider;

    @FXML
    private Button Subsriptions;

    @FXML
    private Button memberManagement;

    @FXML
    private Button teamManagement;

    @FXML
    private Button userAccount;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        MenuOpen.setOnMouseClicked(event ->
                {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(Slider);

            slide.setToX(0);
            slide.play();

            Slider.setTranslateX(-200);

            slide.setOnFinished((ActionEvent e )->
            {
                MenuOpen.setVisible(false);
                MenuBack.setVisible(true);
            });
                }
        );

        MenuBack.setOnMouseClicked(event ->
                {
                    TranslateTransition slide = new TranslateTransition();
                    slide.setDuration(Duration.seconds(0.4));
                    slide.setNode(Slider);

                    slide.setToX(-200);
                    slide.play();

                    Slider.setTranslateX(0);

                    slide.setOnFinished((ActionEvent e )->
                    {
                        MenuOpen.setVisible(true);
                        MenuBack.setVisible(false);
                    });
                }
        );


    }
}

package com.example.db_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class UserSubScreen {

    @FXML
    private Button Expenses;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane Slider;

    @FXML
    private Button Subsriptions;

    @FXML
    private Button memberManagement;

    @FXML
    private TextField subAmount;

    @FXML
    private Label subDate;

    @FXML
    private DatePicker subDatePicker;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colFname;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLname;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colSubStatus;

    @FXML
    private TableView<?> memberTable;


    @FXML
    private Button subDone;

    @FXML
    private Label subIdLabel1;

    @FXML
    private ChoiceBox<String> subPlanChoice;



    @FXML
    private Button teamManagement;

    @FXML
    private Label trackSubLabel;

    @FXML
    private Button trackSubRefresh;
    CurrentUser currentUser;
    @FXML
    private TextField trackSubText;
    @FXML
    private Label subPlanLabel;
    @FXML
    private Button userAccount;

    private boolean on = true;

    @FXML
    private void initialize() {
        // Populate the ChoiceBox with predefined options
        ObservableList<String> planOptions = FXCollections.observableArrayList(
                "Monthly $5", "Quarterly $12", "Yearly $50"
        );
        subPlanChoice.setItems(planOptions);
    }
    public void SwitchToSub(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user fxml/UserSub.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToMember(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("owner fxml/OwnerHomeMember.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleSubDone(ActionEvent event) {
        String planType = subPlanChoice.getValue();
        String amountText = subAmount.getText();
        String startDateString = (subDatePicker.getValue() != null) ? subDatePicker.getValue().toString() : null;

        // Validate inputs
        if (planType == null || amountText.isEmpty() || startDateString == null) {
            System.out.println("All fields must be filled.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("All fields must be filled.");
            alert.show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountText); // Parse amount to double

            // Calculate end date based on plan type
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = null;

            // Using a more robust switch or if-else statement for better readability
            switch (planType) {
                case "Monthly $5":
                    endDate = startDate.plusMonths(1);
                    break;
                case "Quarterly $12":
                    endDate = startDate.plusMonths(3);
                    break;
                case "Yearly $50":
                    endDate = startDate.plusYears(1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid plan type: " + planType); // Provide more informative error message
            }

            // If endDate is still null, this means the planType was invalid
            if (endDate == null) {
                throw new IllegalArgumentException("Invalid plan type: " + planType);
            }

            // Insert data into the database
            String insertQuery = "INSERT INTO subscriptions (MemberID, SPlanType, SAmount, SStartDate, SEndDate) VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = DatabaseConnector.connect();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, CurrentUser.getUserID());  // Assuming MemberID is an integer
                preparedStatement.setString(2, planType);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setString(4, startDate.toString());
                preparedStatement.setString(5, endDate.toString());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Subscription added successfully!");
                    System.out.println("Subscription added successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add subscription.");
                    System.out.println("Failed to add subscription.");
                }
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid MemberID or Amount. Please enter numeric values.");
            System.out.println("Invalid MemberID or Amount. Please enter numeric values.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while inserting data into the database.");
        } catch (IllegalArgumentException e) {
            // Catch invalid plan type and show appropriate error message
            showAlert(Alert.AlertType.ERROR, "Invalid Plan", e.getMessage());
            System.out.println("Invalid plan type: " + e.getMessage());
        }
    }
    // Method to display alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void Logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homeScreen.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void refreshTableView(ActionEvent actionEvent) {
    }
}
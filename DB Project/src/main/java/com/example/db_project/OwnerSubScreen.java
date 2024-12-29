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

public class OwnerSubScreen {

    @FXML
    private Button Expenses;


    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private TableColumn<?, ?> subPlanType;

    @FXML
    private Button Reporting;

    @FXML
    private AnchorPane Slider;

    

    @FXML
    private Button Subsriptions;

    @FXML
    private Button TrackSub;

    @FXML
    private Button assignSub;

    @FXML
    private Button memberManagement;

    @FXML
    private Label subDate;

    @FXML
    private DatePicker subDatePicker;

    @FXML
    private Button subDone;

    @FXML
    private TextField subId;

    @FXML
    private TextField subAmount;

    @FXML
    private Label subIdLabel;

    @FXML
    private Label subIdLabel1;

    @FXML
    private ChoiceBox<String> subPlanChoice;

    @FXML
    private TableView<Subscriptions> trackSubTable;

    @FXML
    private TableColumn<Subscriptions, Integer> SubID;

    @FXML
    private TableColumn<Subscriptions, Integer> MemberID;

    @FXML
    private TableColumn<Subscriptions, String> PlanType;

    @FXML
    private TableColumn<Subscriptions, Double> Amount;

    @FXML
    private TableColumn<Subscriptions, String> StartDate;

    @FXML
    private TableColumn<Subscriptions, String> EndDate;

    @FXML
    private Button teamManagement;

    @FXML
    private Label trackSubLabel;

    @FXML
    private Button trackSubRefresh;


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

        // Initialize table columns
        SubID.setCellValueFactory(new PropertyValueFactory<>("subID"));
        MemberID.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        PlanType.setCellValueFactory(new PropertyValueFactory<>("planType"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        StartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        EndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        // Load data into the table
        loadSubscriptionData();
    }
    public void SwitchToSub(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("owner fxml/OwnerSub.fxml")));

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
    public void switchTrackVisable(ActionEvent event) {

    SwitchTrack(on);
    SwitchAssign(!on);
    }
    @FXML
    public void SwitchAssignVisable(ActionEvent event) {
        SwitchAssign(on);
        SwitchTrack(!on);
    }
    public void SwitchTrack(boolean on) {
        if (on == true) {
            trackSubTable.setVisible(on);
            trackSubLabel.setVisible(on);
            trackSubRefresh.setVisible(on);
            trackSubText.setVisible(on);
            SubID.setVisible(on);
            StartDate.setVisible(on);
            EndDate.setVisible(on);
            PlanType.setVisible(on);
            Amount.setVisible(on);
            MemberID.setVisible(on);
        }
        else {
            trackSubTable.setVisible(on);
            trackSubLabel.setVisible(on);
            trackSubRefresh.setVisible(on);
            trackSubText.setVisible(on);
            SubID.setVisible(on);
            StartDate.setVisible(on);
            EndDate.setVisible(on);
            PlanType.setVisible(on);
            Amount.setVisible(on);
            MemberID.setVisible(on);
        }
    }
    public void SwitchAssign(boolean on) {
        if (on == true) {
            subPlanLabel.setVisible(on);
            subIdLabel.setVisible(on);
            subIdLabel1.setVisible(on);
            subAmount.setVisible(on);
            subPlanChoice.setVisible(on);
            subId.setVisible(on);
            subIdLabel.setVisible(on);
            subDate.setVisible(on);
            subDatePicker.setVisible(on);
            subDone.setVisible(on);

        }
        else {
            subPlanLabel.setVisible(on);
            subIdLabel.setVisible(on);
            subIdLabel1.setVisible(on);
            subAmount.setVisible(on);
            subPlanChoice.setVisible(on);
            subId.setVisible(on);
            subIdLabel.setVisible(on);
            subDate.setVisible(on);
            subDatePicker.setVisible(on);
            subDone.setVisible(on);
        }
    }
    @FXML
    private void handleSubDone(ActionEvent event) {
        String memberIdText = subId.getText(); // Use getText() to get user input
        String planType = subPlanChoice.getValue();
        String amountText = subAmount.getText();
        String startDateString = (subDatePicker.getValue() != null) ? subDatePicker.getValue().toString() : null;

        // Validate inputs
        if (memberIdText.isEmpty() || planType == null || amountText.isEmpty() || startDateString == null) {
            System.out.println("All fields must be filled.");
            return;
        }
        try {
            int memberId = CurrentUser.getUserID(); // Parse MemberID to int
            double amount = Double.parseDouble(amountText); // Parse amount to double

            // Calculate end date based on plan type
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = switch (planType.split(" ")[0].toLowerCase()) {
                case "monthly" -> startDate.plusMonths(1);
                case "quarterly" -> startDate.plusMonths(3);
                case "yearly" -> startDate.plusYears(1);
                default -> throw new IllegalArgumentException("Invalid plan type.");
            };

            // Insert data into the database
            String insertQuery = "INSERT INTO subscriptions (MemberID, SPlanType, SAmount, SStartDate, SEndDate) VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = DatabaseConnector.connect();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setInt(1, memberId);
                preparedStatement.setString(2, planType.split(" ")[0]);
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

        private void loadSubscriptionData() {
            ObservableList<Subscriptions> subscriptions = FXCollections.observableArrayList();

            String query = "SELECT * FROM subscriptions";

            try (Connection connection = DatabaseConnector.connect();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int subID = resultSet.getInt("SubscriptionID");
                    int memberID = resultSet.getInt("MemberID");
                    String planType = resultSet.getString("SPlanType");
                    double amount = resultSet.getDouble("SAmount");
                    String startDate = resultSet.getString("SStartDate");
                    String endDate = resultSet.getString("SEndDate");

                    subscriptions.add(new Subscriptions(subID, memberID, planType, amount, startDate, endDate));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load subscriptions from the database.");
            }

            trackSubTable.setItems(subscriptions);
        }


}


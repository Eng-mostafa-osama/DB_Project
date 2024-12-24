package com.example.db_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Stack;
import java.io.IOException;
public class sceneSwitch {
    @FXML
    private TextField loginName;
    @FXML
    private PasswordField loginPassword;

    @FXML
    private TextField signFname;
    @FXML
    private TextField signLname;
    @FXML
    private TextField signAge;
    @FXML
    private TextField signUsername;
    @FXML
    private TextField signEmail;
    @FXML
    private TextField signnumber;
    @FXML
    private PasswordField signinPassword;
    // Stack to keep track of navigation history
    private static final Stack<Scene> sceneStack = new Stack<>();
    private Stage stage;

    // Switch to SignIn Scene
    @FXML
    public void SwitchToSignIn(ActionEvent e) throws IOException {
        saveCurrentScene(e); // Save the current scene to the stack

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignIn.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Switch to LogIn Scene
    @FXML
    public void SwitchToLogIn(ActionEvent e) throws IOException {
        saveCurrentScene(e); // Save the current scene to the stack

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("logIn.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Go to the previous page
    @FXML
    public void GoToPreviousPage(MouseEvent e) {
        if (!sceneStack.isEmpty()) {
            Scene previousScene = sceneStack.pop(); // Retrieve the previous scene
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        } else {
            System.out.println("No previous page available!");
        }
    }

    // Helper method to save the current scene to the stack
    private void saveCurrentScene(ActionEvent e) {
        Scene currentScene = ((Node) e.getSource()).getScene();
        sceneStack.push(currentScene);
    }

    //method to take user data
    @FXML
    public void registerUser(ActionEvent event) {
        String firstName = signFname.getText();
        String lastName = signLname.getText();
        String username = signUsername.getText();
        String email = signEmail.getText();
        String phoneNumber = signnumber.getText();
        String password = signinPassword.getText();
        String age = signAge.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        try (Connection conn = DatabaseConnector.connect()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            String query = "INSERT INTO users (UFname, ULname, Uusername,UAge, UEmail, UPhoneNumber, UPassword) VALUES (?, ?, ?, ?, ? ,?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, username);
                stmt.setString(4, age);
                stmt.setString(5, email);
                stmt.setString(6, phoneNumber);
                stmt.setString(7, password);
                stmt.executeUpdate();
                System.out.println("User registered successfully!");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage());
        }
    }

    @FXML
    public void validateLogin(ActionEvent event) {

        String username = loginName.getText();

        String password = loginPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty!");
            return;
        }
        try (Connection conn = DatabaseConnector.connect()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }
            String query = "SELECT UPassword FROM users WHERE Uusername = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("UPassword");

                    // Compare stored password with user input
                    if (storedPassword.equals(password)) {
                        System.out.println("Login successful!");
                        System.out.println("true");
                        // Redirect to the next scene or perform actions on successful login
                    } else {
                        System.out.println("Invalid username or password!");
                        System.out.println("false");
                    }
                } else {
                    System.out.println("Username not found!");
                    System.out.println("false");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during login: " + e.getMessage());
        }
    }
}




package com.example.db_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Member;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class OwnerMembersScreen implements Initializable {

    @FXML
    private Button AddMembers;

    @FXML
    private Button CoachesAssignment;

    @FXML
    private Button DeleteMembers;

    @FXML
    private Button EditMembers;

    @FXML
    private Button Expenses;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private Button Reporting;

    @FXML
    private AnchorPane Slider;

    @FXML
    private Button Subsriptions;

    @FXML
    private Button ViewMembers;

    @FXML
    private TextField addEmail;

    @FXML
    private Label addEmailLabel;

    @FXML
    private Button addFinish;

    @FXML
    private TextField addFirstName;

    @FXML
    private Label addFnameLabel;

    @FXML
    private TextField addLastName;

    @FXML
    private Label addLnameLabel;

    @FXML
    private TextField addPhone;

    @FXML
    private Label addPhoneLabel;

    @FXML
    private Button delMember;

    @FXML
    private Label delMemberLabel;

    @FXML
    private Label editSearchLabel;

    @FXML
    private TextField delMemberSearch;

    @FXML
    private TableView<?> delMemberTable;

    @FXML
    private Button delRefresh;

    @FXML
    private Label editChoice;

    @FXML
    private TextField editEmail;

    @FXML
    private Label editEmailLabel;
    @FXML
    private Button viewRefresh;

    @FXML
    private RadioButton editEmailR;

    @FXML
    private Button editFinish;

    @FXML
    private TextField editFirstName;

    @FXML
    private Label editFnameLabel;

    @FXML
    private RadioButton editFnameR;

    @FXML
    private TextField editLastName;

    @FXML
    private Label editLnameLabel;

    @FXML
    private RadioButton editLnameR;

    @FXML
    private TextField editPhone;

    @FXML
    private Label editPhoneLabel;

    @FXML
    private RadioButton editPhoneR;

    @FXML
    private TextField editSearch;

    @FXML
    private ChoiceBox<String> editSub;
    private final String[] SubChoices = {"Monthly_$5", "Yearly_$50","Quarterly_$12" };


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
    private TableColumn<?, ?> colName;
    @FXML
    private Label editSubLabel;

    @FXML
    private RadioButton editSubR;

    @FXML
    private Button memberManagement;

    @FXML
    private TextField memberSearch;

    @FXML
    private TableView<Members> memberTable;

    @FXML
    private Label searchLabel;

    @FXML
    private ChoiceBox<String> subType;

    @FXML
    private Label subTypeLabel;

    @FXML
    private Button teamManagement;

    @FXML
    private Button userAccount;
    @FXML
    private ObservableList<Members> memberList = FXCollections.observableArrayList();

    private boolean on = true;

    //---------------------------------------

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        subType.getItems().addAll(SubChoices);
        editSub.getItems().addAll(SubChoices);
    }

    @FXML
    private void viewMembers(ActionEvent event) {
        delSwitchVisable(!on);
        addSwitchVisable(!on);
        editSwitchVisable(!on);
        viewSwitchVisable(on);
        //-------------------------


    }

    @FXML
    public void addMembers(ActionEvent event) {
        delSwitchVisable(!on);
        addSwitchVisable(on);
        editSwitchVisable(!on);
        viewSwitchVisable(!on);
    }

    @FXML
    public void deleteMembers(ActionEvent event) {
        delSwitchVisable(on);
        addSwitchVisable(!on);
        editSwitchVisable(!on);
        viewSwitchVisable(!on);

    }

    @FXML
    public void editMembers(ActionEvent event) {
        delSwitchVisable(!on);
        addSwitchVisable(!on);
        editSwitchVisable(on);
        viewSwitchVisable(!on);
    }

    /** View Members **/
    @FXML
    private void refreshTableView() {

        memberList.clear();
        String query = "SELECT * FROM members";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ahly", "root", "12345678910");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                memberList.add
                        (
                               new Members(
                                rs.getInt("MemberID"),
                                rs.getString("MfName"),
                                rs.getString("MlName"),
                                rs.getString("MEmail"),
                                rs.getInt("MPhoneNumber"),
                                        rs.getString("MsubStatus")
                                )

                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("mfName"));
        colLname.setCellValueFactory(new PropertyValueFactory<>("mlName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("mPhoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("mEmail"));
        colSubStatus.setCellValueFactory(new PropertyValueFactory<>("mSubStatus"));
        memberTable.setItems(memberList);
    }

    @FXML
    private void deleteMemberFromDatabase(ActionEvent event) {
        // Get the Member ID from the input field
        String memberId = delMemberSearch.getText();

        // Validate that the input is not empty
        if (memberId == null || memberId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a Member ID to delete.");
            alert.show();
            return;
        }

        // SQL query to delete the member
        String query = "DELETE FROM members WHERE MemberID = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ahly", "root", "12345678910");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the Member ID in the query
            stmt.setInt(1, Integer.parseInt(memberId));

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            // Check if any rows were affected
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Member deleted successfully!");
                alert.show();

                // Refresh the table view to reflect changes
                refreshTableView();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No member found with the given ID.");
                alert.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred while deleting the member.");
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Member ID. Please enter a numeric value.");
            alert.show();
        }
    }


    @FXML
    private void addMemberToDatabase(ActionEvent event) {
        String firstName = addFirstName.getText();
        String lastName = addLastName.getText();
        String email = addEmail.getText();
        String phone = addPhone.getText();
        String Subscription = subType.getValue();



        String query = "INSERT INTO members (MfName, MlName, MEmail,MPhoneNumber,MSubscriptions) VALUES ( ?,?, ?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ahly", "root", "12345678910");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, Subscription);
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Member Added Successfully!");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editMemberInDatabase(ActionEvent event) {
        String searchId = editSearch.getText();
        String firstName = editFirstName.getText();
        String lastName = editLastName.getText();
        String phone = editPhone.getText();
        String email = editEmail.getText();
        String subscription = editSub.getValue();

        // Constructing the dynamic SQL query
        StringBuilder queryBuilder = new StringBuilder("UPDATE members SET ");
        boolean isFirstField = true;

        // Adding fields to update only if they are not empty or null
        if (firstName != null && !firstName.isEmpty()) {
            queryBuilder.append("MfName = ?");
            isFirstField = false;
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (!isFirstField) queryBuilder.append(", ");
            queryBuilder.append("MlName = ?");
            isFirstField = false;
        }
        if (phone != null && !phone.isEmpty()) {
            if (!isFirstField) queryBuilder.append(", ");
            queryBuilder.append("MPhoneNumber = ?");
            isFirstField = false;
        }
        if (email != null && !email.isEmpty()) {
            if (!isFirstField) queryBuilder.append(", ");
            queryBuilder.append("MEmail = ?");
            isFirstField = false;
        }
        if (subscription != null && !subscription.isEmpty()) {
            if (!isFirstField) queryBuilder.append(", ");
            queryBuilder.append("MSubscriptions = ?");
        }
        queryBuilder.append(" WHERE MemberID = ?");

        // If no fields are provided, show an alert and exit
        if (isFirstField) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No fields to update. Please provide at least one field.");
            alert.show();
            return;
        }

        // Executing the query
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ahly", "root", "12345678910");
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;

            if (firstName != null && !firstName.isEmpty()) {
                stmt.setString(paramIndex++, firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                stmt.setString(paramIndex++, lastName);
            }
            if (phone != null && !phone.isEmpty()) {
                stmt.setString(paramIndex++, phone);
            }
            if (email != null && !email.isEmpty()) {
                stmt.setString(paramIndex++, email);
            }
            if (subscription != null && !subscription.isEmpty()) {
                stmt.setString(paramIndex++, subscription);
            }

            stmt.setInt(paramIndex, Integer.parseInt(searchId));
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Member updated successfully!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No member found with the given ID.");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred while updating the member.");
            alert.show();
        }
    }

    private void addSwitchVisable(boolean on) {
        if (on == true) {
            addFnameLabel.setVisible(on);
            addFirstName.setVisible(on);
            addLastName.setVisible(on);
            addLnameLabel.setVisible(on);
            addPhone.setVisible(on);
            addPhoneLabel.setVisible(on);
            addEmail.setVisible(on);
            addEmailLabel.setVisible(on);
            subType.setVisible(on);
            subTypeLabel.setVisible(on);
            addFinish.setVisible(on);
        } else {
            addFnameLabel.setVisible(on);
            addFirstName.setVisible(on);
            addLastName.setVisible(on);
            addLnameLabel.setVisible(on);
            addPhone.setVisible(on);
            addPhoneLabel.setVisible(on);
            addEmail.setVisible(on);
            addEmailLabel.setVisible(on);
            subType.setVisible(on);
            subTypeLabel.setVisible(on);
            addFinish.setVisible(on);
        }
    }

    private void delSwitchVisable(boolean on) {
        if (on == true) {
            delMemberLabel.setVisible(on);
            delMemberSearch.setVisible(on);
            delRefresh.setVisible(on);
            delMemberTable.setVisible(on);
            delMember.setVisible(on);

        } else {
            delMemberLabel.setVisible(on);
            delMemberSearch.setVisible(on);
            delRefresh.setVisible(on);
            delMemberTable.setVisible(on);
            delMember.setVisible(on);
        }
    }

    private void editSwitchVisable(boolean on) {
        if (on == true) {
            editSearch.setVisible(on);
            editSearchLabel.setVisible(on);
            editSubR.setVisible(on);
            editEmailR.setVisible(on);
            editPhoneR.setVisible(on);
            editFnameR.setVisible(on);
            editLnameR.setVisible(on);
            editFinish.setVisible(on);
        } else {
            editSubR.setVisible(on);
            editFinish.setVisible(on);
            editEmailR.setVisible(on);
            editPhoneR.setVisible(on);
            editFnameR.setVisible(on);
            editLnameR.setVisible(on);
            editSearch.setVisible(on);
            editSearchLabel.setVisible(on);
        }
    }
    @FXML
    private void viewEdit(ActionEvent event) {
     if (editFnameR.isSelected()){
         editFirstName.setVisible(true);
         editFnameLabel.setVisible(true);
     }
    if(editLnameR.isSelected()){
        editLastName.setVisible(true);
        editLnameLabel.setVisible(true);
    }
    if(editEmailR.isSelected()){
        editEmail.setVisible(true);
        editEmailLabel.setVisible(true);
    }
    if(editPhoneR.isSelected()){
        editPhone.setVisible(true);
        editPhoneLabel.setVisible(true);
    }
    if(editSubR.isSelected()){
        editSub.setVisible(true);
        editSubLabel.setVisible(true);
    }
     }

    @FXML
    public void SwitchToSub(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("owner fxml/OwnerSub.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void viewSwitchVisable(boolean on) {
        if (on == true) {

            memberSearch.setVisible(on);
            searchLabel.setVisible(on);
            memberTable.setVisible(on);
        } else {
            memberSearch.setVisible(on);
            searchLabel.setVisible(on);
            memberTable.setVisible(on);
        }
    }
}




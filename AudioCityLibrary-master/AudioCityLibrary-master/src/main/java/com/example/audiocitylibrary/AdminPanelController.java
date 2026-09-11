package com.example.audiocitylibrary;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.*;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;




public class AdminPanelController {

    @FXML
    private void handlePromoteButtonAction(ActionEvent event) {
        AdminPanelDane selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            int currentPermissions = selectedUser.uprawnieniaProperty().get();
            int newPermissions =  1; // Example: increase permissions by 1

            // Update the user's permissions in the database
            String updateQuery = "UPDATE users SET uprawnienia = ? WHERE idUzytkownika = ?";
            try (Connection connection = databaseHandler.connectDb();
                 PreparedStatement statement = connection.prepareStatement(updateQuery)) {

                statement.setInt(1, newPermissions);
                statement.setInt(2, selectedUser.idUzytkownikaProperty().get());
                statement.executeUpdate();

                // Update the user's permissions in the observable list
                selectedUser.uprawnieniaProperty().set(newPermissions);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wybranego użytkownika");
            alert.setHeaderText("Proszę wybrać użytkownika do awansu.");
            alert.setContentText("Nie wybrano żadnego użytkownika w tabeli.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDemoteButtonAction(ActionEvent event) {
            AdminPanelDane selectedUser = userTable.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                int currentPermissions = selectedUser.uprawnieniaProperty().get();
                int newPermissions = 0; // Degrade permissions to 0

                // Check if the user is not the first user
                if (selectedUser.czyPierwszyProperty().get() == 0) {
                    // Update the user's permissions in the database
                    String updateQuery = "UPDATE users SET uprawnienia = ? WHERE idUzytkownika = ?";
                    try (Connection connection = databaseHandler.connectDb();
                         PreparedStatement statement = connection.prepareStatement(updateQuery)) {

                        statement.setInt(1, newPermissions);
                        statement.setInt(2, selectedUser.idUzytkownikaProperty().get());
                        statement.executeUpdate();

                        // Update the user's permissions in the observable list
                        selectedUser.uprawnieniaProperty().set(newPermissions);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Nieprawidłowy użytkownik");
                    alert.setHeaderText("Proszę wybrać użytkownika do degradacji.");
                    alert.setContentText("Wybrany użytkownik nie może zostać zdegradowany.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Brak wybranego użytkownika");
                alert.setHeaderText("Proszę wybrać użytkownika do degradacji.");
                alert.setContentText("Nie wybrano żadnego użytkownika w tabeli.");
                alert.showAndWait();
            }
        }

    @FXML
    private void handleResignButtonAction() {
        // Sprawdź czy istnieje co najmniej jeden użytkownik z uprawnieniami "1" oprócz aktualnie zalogowanego użytkownika
        boolean hasOtherAdmin = false;
        try {
            String query = "SELECT COUNT(*) FROM users WHERE uprawnienia = 1 AND idUzytkownika != ?";
            try (Connection connection = databaseHandler.connectDb();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, LoginController.loggedUserId);
                try (ResultSet rs = statement.executeQuery()) {

                    if (rs.next()) {
                        hasOtherAdmin = rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking for other admin users: " + e.getMessage());
        }

        // Jeśli nie ma użytkowników z uprawnieniami "1" oprócz aktualnie zalogowanego użytkownika, wyświetl alert
        if (!hasOtherAdmin) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga");
            alert.setHeaderText("Należy nadać co najmniej jednemu innemu użytkownikowi uprawnienia Administratora");
            alert.showAndWait();
        } else {
            // Zaktualizuj uprawnienia aktualnie zalogowanego użytkownika do wartości "0"
            try {
                String updateQuery = "UPDATE users SET uprawnienia = 0 WHERE idUzytkownika = ?";
                try (Connection connection = databaseHandler.connectDb();
                     PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {

                    pstmt.setInt(1, LoginController.loggedUserId);
                    int rowsUpdated = pstmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        // Wyłącz program w całości
                        Platform.exit();
                    } else {
                        System.out.println("Error updating user permissions: No rows were updated");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error updating user permissions: " + e.getMessage());
            }
        }
    }


    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;

    private Statement statement;

    @FXML
    private TableColumn<AdminPanelDane, String> addressColumn;

    @FXML
    private TableColumn<AdminPanelDane, String> emailColumn;

    @FXML
    private TableColumn<AdminPanelDane, Integer> firstColumn;

    @FXML
    private TableColumn<AdminPanelDane, Integer> idColumn;

    @FXML
    private TableColumn<AdminPanelDane, String> loginColumn;

    @FXML
    private TableColumn<AdminPanelDane, String> passwordColumn;

    @FXML
    private TableColumn<AdminPanelDane, Integer> permissionsColumn;

    @FXML
    private TableColumn<AdminPanelDane, Integer> saldoColumn;

    @FXML
    private TableView<AdminPanelDane> userTable;

    private ObservableList<AdminPanelDane> UsersData;
    private ObservableList<AdminPanelDane> addUsersData(){
        ObservableList<AdminPanelDane> UsersData = FXCollections.observableArrayList();
        String sql = "SELECT idUzytkownika, login, password, uprawnienia, saldo, email, adres, czyPierwszy FROM users";
        try (Connection connection = databaseHandler.connectDb();
             PreparedStatement prepare = connection.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                AdminPanelDane APD = new AdminPanelDane(
                        result.getInt("idUzytkownika"),
                        result.getString("login"),
                        result.getString("password"),
                        result.getInt("uprawnienia"),
                        result.getInt("saldo"),
                        result.getString("email"),
                        result.getString("adres"),
                        result.getInt("czyPierwszy")
                );
                UsersData.add(APD);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UsersData;
    }


    private ObservableList<AdminPanelDane> addListUser;
    public void wyswietlanieDotabeli() {
        addListUser = addUsersData();

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idUzytkownikaProperty().asObject());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        permissionsColumn.setCellValueFactory(cellData -> cellData.getValue().uprawnieniaProperty().asObject());
        saldoColumn.setCellValueFactory(cellData -> cellData.getValue().saldoProperty().asObject());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().adresProperty());
        firstColumn.setCellValueFactory(cellData -> cellData.getValue().czyPierwszyProperty().asObject());

        userTable.setItems(addListUser);
    }
    @FXML
    public void initialize() {

        wyswietlanieDotabeli();
    }

}

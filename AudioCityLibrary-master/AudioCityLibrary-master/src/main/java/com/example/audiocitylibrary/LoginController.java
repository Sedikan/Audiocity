package com.example.audiocitylibrary;

import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import static com.example.audiocitylibrary.Controllers.pathToIcon;

public class LoginController {


    @FXML
    private Button go_to_register_btn_id;

    @FXML
    private Button login_btn_id;

    @FXML
    private TextField login_text_id;

    @FXML
    private AnchorPane logowanko;


    @FXML
    private PasswordField passwd_text_id;

    @FXML
    private AnchorPane register;

    @FXML
    private TextField register_adress_text_id;

    @FXML
    private Button register_btn_id;

    @FXML
    private TextField register_email_text_id;

    @FXML
    private TextField register_login_text_id;

    @FXML
    private TextField register_passwd_text_id;

    @FXML
    private Button regulamin_btn_id;

    @FXML
    private Button regulamin_btn_id1;


    // DATABASE TOOLS

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public static int getIdUzytkownika() {
        return idUzytkownika;
    }

    public static String getUsersName() {
        return login;
    }

    public static int getUsersSaldo() {
        return saldo;
    }

    public static int getCzyPierwszy(){
        return czyPierwszy;
    }
    public static int getUsersPermissions() {
        return uprawnienia;
    }

    public static String getPermissionName(int permissionsValue) {

        if (permissionsValue == 1) {
            return "Administrator";
        } else {
            return "Zwykły użytkownik";
        }
    }

    //  logowanie uzytkownika

    private static int idUzytkownika;
    private static String login;
    private static int uprawnienia;
    private static int saldo;
    private static int czyPierwszy;

    public static int loggedUserId;


    private static int idZalogowanegoUzytkownika;

    public void loginUser() {
        String sql = "SELECT idUzytkownika, login, uprawnienia, saldo, czyPierwszy FROM users WHERE login = ? AND password = ?";
        connect = databaseHandler.connectDb();

        try {
            Alert alert;

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, login_text_id.getText());
            prepare.setString(2, passwd_text_id.getText());

            result = prepare.executeQuery();

            if (login_text_id.getText().isEmpty() || passwd_text_id.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd!");
                alert.setHeaderText(null);
                alert.setContentText("Wypełnij puste pola");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    //odczytuje dane z bazy danych
                    idUzytkownika = result.getInt("idUzytkownika");
                    login = result.getString("login");
                    uprawnienia = result.getInt("uprawnienia");
                    saldo = result.getInt("saldo");
                    czyPierwszy = result.getInt("czyPierwszy");

                    loggedUserId = idUzytkownika;
                    idZalogowanegoUzytkownika = idUzytkownika;
                    uprawnieniaZalogowanegoUzytkownika = uprawnienia;
                    //przejscie do glownego ekranu
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacja");
                    alert.setHeaderText(null);
                    alert.setContentText("Zalogowano pomyślnie!");
                    alert.showAndWait();

                    login_btn_id.getScene().getWindow().hide(); // ukryta login form

                    loadWindow("/com/example/audiocitylibrary/Home.fxml", "Audio Town", icon);
                } else {
                    //then error message
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd!");
                    alert.setHeaderText(null);
                    alert.setContentText("Zły login / haslo!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static int uprawnieniaZalogowanegoUzytkownika;
    public static int getUprawnieniaZalogowanegoUzytkownika(){

        return uprawnieniaZalogowanegoUzytkownika;
    }
    public static int getIdZalogowanegoUzytkownika() {
        return idZalogowanegoUzytkownika;
    }

    public void registerUser() {
        String checkUserSql = "SELECT * FROM `users` WHERE `login`=?";
        String insertUserSql = "INSERT INTO `users` (`idUzytkownika`, `login`, `password`, `uprawnienia`, `saldo`, `email`, `adres`, `czyPierwszy`) VALUES (NULL, ?, ?, ?, '500', ?, ?, ?)";
        String checkTableEmptySql = "SELECT COUNT(*) AS liczba_rekordow FROM `users`";
        connect = databaseHandler.connectDb();

        try {
            // Sprawdzenie, czy tabela 'users' jest pusta
            prepare = connect.prepareStatement(checkTableEmptySql);
            ResultSet tableEmptyResult = prepare.executeQuery();

            if (tableEmptyResult.next()) {
                int liczbaRekordow = tableEmptyResult.getInt("liczba_rekordow");

                // Sprawdzenie, czy tabela 'users' jest pusta
                if (liczbaRekordow == 0) {
                    // Tabela jest pusta, dodanie pierwszego użytkownika z uprawnieniem "1"
                    prepare = connect.prepareStatement(insertUserSql);
                    prepare.setString(1, register_login_text_id.getText());
                    prepare.setString(2, register_passwd_text_id.getText());
                    prepare.setString(3, "1"); // Uprawnienia "1" dla pierwszego użytkownika
                    prepare.setString(4, register_email_text_id.getText());
                    prepare.setString(5, register_adress_text_id.getText());
                    prepare.setInt(6, 1); // Ustawienie czyPierwszy na 1

                } else {
                    // Tabela nie jest pusta, sprawdzenie, czy użytkownik o danym nicku już istnieje
                    prepare = connect.prepareStatement(checkUserSql);
                    prepare.setString(1, register_login_text_id.getText());
                    ResultSet resultSet = prepare.executeQuery();

                    // Sprawdzenie, czy istnieje wynik zapytania (czy użytkownik już istnieje)
                    if (resultSet.next()) {
                        // Wyświetlenie komunikatu o błędzie
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błędne dane!");
                        alert.setHeaderText(null);
                        alert.setContentText("Użytkownik o podanym loginie już istnieje!");
                        alert.showAndWait();
                        return; // Zakończenie metody, aby nie kontynuować rejestracji
                    }

                    // Tabela nie jest pusta, dodanie użytkownika z uprawnieniem "0"
                    prepare = connect.prepareStatement(insertUserSql);

                    // Sprawdzenie, czy jakiekolwiek pole danych do rejestracji jest puste
                    if (register_login_text_id.getText().isEmpty() || register_passwd_text_id.getText().isEmpty() ||
                            register_email_text_id.getText().isEmpty() || register_adress_text_id.getText().isEmpty()) {
                        // Wyświetlenie komunikatu o błędzie
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błędne dane!");
                        alert.setHeaderText(null);
                        alert.setContentText("Wszystkie pola danych do rejestracji muszą być wypełnione!");
                        alert.showAndWait();
                        return; // Zakończenie metody, aby nie kontynuować rejestracji
                    }

                    // Wypełnienie reszty pól danych do rejestracji
                    prepare.setString(1, register_login_text_id.getText());
                    prepare.setString(2, register_passwd_text_id.getText());
                    prepare.setString(3, "0"); // Uprawnienia "0" dla kolejnych użytkowników
                    prepare.setString(4, register_email_text_id.getText());
                    prepare.setString(5, register_adress_text_id.getText());
                    prepare.setInt(6, 0); // Ustawienie czyPierwszy na 0
                }

                int affectedRows = prepare.executeUpdate();

                if (affectedRows > 0) {
                    // Przejście do głównego ekranu po udanej rejestracji
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacja");
                    alert.setHeaderText(null);
                    alert.setContentText("Zarejestrowano pomyślnie, nastąpi zamknięcie okna, przy następnym uruchomieniu programu zaloguj się!");
                    alert.showAndWait();

                    register_btn_id.getScene().getWindow().hide(); // Ukrycie okna rejestracji
                    // loadWindow("/com/example/audiocitylibrary/Home.fxml", "Audio Town");
                } else {
                    // Wyświetlenie komunikatu o błędzie
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błędne dane!");
                    alert.setHeaderText(null);
                    alert.setContentText("Błąd podczas rejestracji!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    void go_to_register(ActionEvent event) {
        register.setVisible(true);
        logowanko.setVisible(false);


    }
    @FXML
    private ScrollPane regulationScrollPane;

    @FXML
    private VBox regulationVbox;
/*
    @FXML
    void log_in(ActionEvent event) {
        // obsługa logowania
        loadWindow("/com/example/audiocitylibrary/Home.fxml", "Audio Town");
    }
*/
    @FXML
    void login_text(ActionEvent event) {

    }

    @FXML
    void passwd_text(ActionEvent event) {

    }
    private Image icon;
    @FXML
    void register(ActionEvent event) {
        // obsługa rejestracji  ;)
        loadWindow("/com/example/audiocitylibrary/Home.fxml", "Audio Town", icon);
    }
    public void loadRegulationsText() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/regulations.txt")));
            regulationVbox.getChildren().add(new Text(content));
        } catch (IOException e) {
            System.err.println("Error reading regulations.txt file: " + e.getMessage());
        }
    }
    @FXML
    private ScrollPane loginScrollPane;

    @FXML
    private VBox loginVbox;

    @FXML
    private Label regulationLabel;

    public void setRegulationText(String text) {
        regulationLabel.setText(text);
    }
    @FXML
    void regulamin_show(ActionEvent event) {
        System.out.println("Regulamin");
        loadWindow("/com/example/audiocitylibrary/regulaminek.fxml","Regulations", icon);
    }
    public static void setDefaultIcons(Stage stage, Image icon) {

        stage.getIcons().add(new Image(pathToIcon));
    }

    private String loadRegulations() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/com/example/audiocitylibrary/regulations.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if there's an error loading the regulations
        }
        return sb.toString();
    }

    void loadWindow(String loc, String title, Image icon) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loc));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(parent));

            setDefaultIcons(stage, icon);

            Controllers controllersController = loader.getController();
            int uprawnieniaValue = getUprawnieniaZalogowanegoUzytkownika(); // Replace with your actual method or variable

            controllersController.setSongButtonVisibility(uprawnieniaValue == 1);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

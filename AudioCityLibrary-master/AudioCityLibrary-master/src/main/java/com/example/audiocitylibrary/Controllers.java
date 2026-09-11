package com.example.audiocitylibrary;

import com.example.audiocitylibrary.ShopController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import static java.sql.Types.NULL;

public class Controllers   {


    public void setSongButtonVisibility(boolean isVisible) {
        if (songButton != null) {
            songButton.setVisible(isVisible);
        }
    }
    public static void setDefaultIcons(Stage stage) {

        stage.getIcons().add(new Image("/AudioTownSmallIcon.png"));
    }

    @FXML
    private ScrollPane regulationScrollPane;

    @FXML
    private VBox regulationVbox;
    @FXML
    private Button LibraryButton;

    @FXML
    private HBox buttonContainer;
    @FXML
    private HBox buttonContainer1;

    @FXML
    private Button homeButton;

    @FXML
    private BorderPane homePage;

    @FXML
    private BorderPane library;

    @FXML
    private TextField libSearch;

    @FXML
    private Button settingsButton;

    @FXML
    private Button settingsButton1;

    @FXML
    private Button shopButton;

    @FXML
    private Button shopButton1;

    @FXML
    private Button songButton;

    @FXML
    private Button songButton1;

    @FXML
    private Button userButton;

    @FXML
    private Button userButton1;

    @FXML
    private Label usernameDisplay;

    private static List<Stage> openStages = new ArrayList<>();
    public Controllers() {
        // Konstruktor bezargumentowy
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public Controllers(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



    @FXML
    void browseIcon(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        Stage stage = (Stage) iconPathTextField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            iconPathTextField.setText(selectedFile.getAbsolutePath());
            pathToIcon = selectedFile.getAbsolutePath();
            currentIcon = new Image(selectedFile.toURI().toString());
            updateIcons(); // Aktualizuj ikony

        }
    }
    private Image currentIcon;

    public static String pathToIcon = "/AudioTownSmallIcon.png";
    public void saveSettings() {
        if (primaryStage != null) {
            homeWindowsTitle = studioNameTextField.getText();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            try {
                if (selectedFile != null) {
                    currentIcon = new Image(selectedFile.toURI().toString());
                }
                // Add this line to retain the currentIcon when the textfield is empty
                else if (currentIcon != null) {
                    // Do nothing, retain the currentIcon
                } else {
                    currentIcon = new Image("/AudioTownSmallIcon.png");
                }

                primaryStage.getIcons().clear();
                primaryStage.getIcons().add(currentIcon);

                // Add the rest of the settings saving logic here
                updateIcons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("primaryStage is null. Cannot save settings.");
        }
        updateIcons();  //może usuń jeśli się bedzie walić
    }


    public static String homeWindowsTitle;


    @FXML
    public String getPermissionsText() {
        int permissionsValue = LoginController.getUsersPermissions();
        return LoginController.getPermissionName(permissionsValue);
    }

    @FXML
    public static String getPermissionName(int permissionValue) {
        if (permissionValue == 1) {
            return "Administrator";
        } else {
            return "Zwykły użytkownik";
        }
    }
    private Stage primaryStage;
    public static String finalPermissionName;
    public int getIdUzytkownika() {
        return LoginController.getIdUzytkownika();
    }

    public String getUsersName(){
        return LoginController.getUsersName();
    };
    public int getUsersSaldo(){
        return LoginController.getUsersSaldo();
    }

    public int getCzyPierwszy(){
        return LoginController.getCzyPierwszy();
    }
    public int getUsersPermissions(){
        return LoginController.getUsersPermissions();
    }
    @FXML
    private ToggleGroup visualMode;


    @FXML
    private ToggleGroup language;

    @FXML
    private Label saldo_id;

    @FXML
    private Label permissionsLabel;

    @FXML
    private Label username_id;

    private Image icon;
    // DATABASE TOOLS

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;


    @FXML
    void changeTheme(ActionEvent event) {

    }
    @FXML
    void go_to_Lib(ActionEvent event) {
        library.setVisible(true);
        homePage.setVisible(false);
        //addButtons();
    }

    @FXML
    void libSearchSong(ActionEvent event) {

    }

    @FXML
    void addSong(ActionEvent event) {
        System.out.println("Song");
        loadWindow("/com/example/audiocitylibrary/addSong.fxml","AudioTown - Add Song", icon);
    }

    @FXML
    void goHome(ActionEvent event) {
        library.setVisible(false);
        homePage.setVisible(true);
    }

    @FXML
    void openShop(ActionEvent event) {
        System.out.println("Shop");
        loadWindow("/com/example/audiocitylibrary/shop.fxml","AudioTown - Shop", icon);
    }
    @FXML
    void openAdminPanel(ActionEvent event) {
        System.out.println("Admin Panel");
        loadWindow("/com/example/audiocitylibrary/AdminPanel.fxml","Admin Panel", icon);
    }

    @FXML
    void openUser(ActionEvent event) {

        /*System.out.println("permissionsLabel: " + permissionsLabel);
        if (permissionsLabel != null) {
            int permissionsValue = LoginController.getUsersPermissions();
            permissionsLabel.setText(LoginController.getPermissionName(permissionsValue));
        } else {
            System.err.println("permissionsLabel is null. Cannot set text.");
        }*/
        // Pobierz wartość uprawnień użytkownika
        System.out.println("User");
        loadWindow("/com/example/audiocitylibrary/user.fxml", "user", icon);

        int permissionsValue = LoginController.getUsersPermissions();
        permissionsLabel.setText(LoginController.getPermissionName(permissionsValue));
    }



    @FXML
    void settings(ActionEvent event) {
        System.out.println("Settings");
        Image icon = new Image(getClass().getResourceAsStream("/AudioTownSmallIcon.png"));
        loadWindow("/com/example/audiocitylibrary/Settings.fxml", "Settings", icon);
    }
    /*private Connection connection;
    private PreparedStatement prepare1;
    private ResultSet result1;*/


    /*public ObservableList<LibraryButton> addButtons(boolean userSongs) throws SQLException {
        if (userSongs) {
            int idAlbumu = NULL;
            int idUzytkownika = LoginController.getIdZalogowanegoUzytkownika();
            String sql = "SELECT id_albumu FROM zakupioneAlbumy " +
                    "WHERE idUzytkownika = ?" ;
            Connection connection = databaseHandler.connectDb();
            prepare = connection.prepareStatement(sql);
            prepare.setInt(1, idUzytkownika);
            prepare.executeUpdate();
            if (result.next()) {
                idAlbumu = result.getInt("id_albumu");
            }
            ObservableList<LibraryButton> buttonList = FXCollections.observableArrayList();
            String sql1 = "SELECT album.nazwaAlbumu, autor.nazwaAutora, album.image FROM album " +
                    "JOIN autor ON album.idAutora = autor.idAutora" +
                    " JOIN utwor ON utwor.idAlbumu = album.idAlbum WHERE idAlbum = GROUP BY album.idAlbum;";

                 PreparedStatement prepare = connection.prepareStatement(sql1);
                prepare.setInt(1, idAlbumu);
                 ResultSet result = prepare.executeQuery();

                while (result.next()) {
                    LibraryButton button = new LibraryButton(result.getString("nazwaAlbumu"),
                            result.getString("nazwaAutora"),
                            result.getString("image"));
                    buttonList.add(button);
                }
            return buttonList;
        } else {
            ObservableList<LibraryButton> buttonList = FXCollections.observableArrayList();
            String sql = "SELECT album.nazwaAlbumu, autor.nazwaAutora, album.image FROM album " +
                    "JOIN autor ON album.idAutora = autor.idAutora" +
                    " JOIN utwor ON utwor.idAlbumu = album.idAlbum GROUP BY album.idAlbum;";
            Connection connection = databaseHandler.connectDb();
                 PreparedStatement prepare = connection.prepareStatement(sql);
                 ResultSet result = prepare.executeQuery();

                while (result.next()) {
                    LibraryButton button = new LibraryButton(result.getString("nazwaAlbumu"),
                            result.getString("nazwaAutora"),
                            result.getString("image"));
                    buttonList.add(button);
                }


            return buttonList;
        }
    }*/

    public ObservableList<LibraryButton> addButtons(boolean userSongs) throws SQLException {
        ObservableList<LibraryButton> buttonList = FXCollections.observableArrayList();
        Connection connection = databaseHandler.connectDb();
        try {
            if (userSongs) {
                int idUzytkownika = LoginController.getIdZalogowanegoUzytkownika();
                String sql = "SELECT id_albumu FROM zakupioneAlbumy WHERE idUzytkownika = ?";
                PreparedStatement prepare = connection.prepareStatement(sql);
                prepare.setInt(1, idUzytkownika);
                ResultSet result = prepare.executeQuery();
                while (result.next()) {
                    int idAlbumu = result.getInt("id_albumu");
                    String sql1 = "SELECT album.nazwaAlbumu, autor.nazwaAutora, album.image FROM album " +
                            "JOIN autor ON album.idAutora = autor.idAutora " +
                            "JOIN utwor ON utwor.idAlbumu = album.idAlbum WHERE album.idAlbum = ? " +
                            "GROUP BY album.idAlbum";
                    PreparedStatement prepare1 = connection.prepareStatement(sql1);
                    prepare1.setInt(1, idAlbumu);
                    ResultSet result1 = prepare1.executeQuery();
                    while (result1.next()) {
                        LibraryButton button = new LibraryButton(result1.getString("nazwaAlbumu"),
                                result1.getString("nazwaAutora"),
                                result1.getString("image"));
                        buttonList.add(button);
                    }
                }
            } else {
                String sql = "SELECT album.nazwaAlbumu, autor.nazwaAutora, album.image FROM album " +
                        "JOIN autor ON album.idAutora = autor.idAutora " +
                        "JOIN utwor ON utwor.idAlbumu = album.idAlbum GROUP BY album.idAlbum";
                PreparedStatement prepare = connection.prepareStatement(sql);
                ResultSet result = prepare.executeQuery();
                while (result.next()) {
                    LibraryButton button = new LibraryButton(result.getString("nazwaAlbumu"),
                            result.getString("nazwaAutora"),
                            result.getString("image"));
                    buttonList.add(button);
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return buttonList;
    }


    @FXML
    private Button panelAdministratoraButton;
    @FXML
    private RadioButton darkRadioButton;

    @FXML
    private RadioButton englishRadioButton;

    @FXML
    private TextField iconPathTextField;

    @FXML
    private RadioButton lightRadioButton;

    @FXML
    private RadioButton polishRadioButton;

    @FXML
    private TextField studioNameTextField;

    @FXML
    private GridPane settingsGridPane;

    private void updateIcons() {
        for (Window window : Stage.getWindows()) {
            Stage stage = (Stage) window;
            stage.getIcons().clear();
            stage.getIcons().add(currentIcon);
        }
    }


    @FXML
    private void changeTheme() {
        if (darkRadioButton.isSelected()) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    }

    private void applyDarkTheme() {


        settingsGridPane.getStylesheets().clear();
        settingsGridPane.getStylesheets().add(getClass().getResource("/darkStyle.css").toExternalForm());
    }

    private void applyLightTheme() {
        settingsGridPane.getStylesheets().clear();
        settingsGridPane.getStylesheets().add(getClass().getResource("/lightStyle.css").toExternalForm());
    }

    void loadWindow(String loc, String title, Image icon) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loc));
            Parent parent = loader.load();
            Stage stage = new Stage();
            Controllers.setDefaultIcons(stage);

            if (loc.equals("/com/example/audiocitylibrary/shop.fxml")) {
                ShopController shopController = loader.getController();
                HBox firstWindow = shopController.getFirstWindow();
                firstWindow.setVisible(true);

                // Set visibility of shop_first_btn_add based on user permissions
                int userPermissions = LoginController.getUprawnieniaZalogowanegoUzytkownika();
                boolean isShopFirstBtnAddVisible = (userPermissions == 1);
                shopController.setShopFirstBtnAddVisibility(isShopFirstBtnAddVisible);
                shopController.setUserPermissions(userPermissions); // Set userPermissions

                // Other code specific to shop.fxml
            }

            // Ustaw ikonę na poziomie Stage
            if (icon != null) {
                stage.getIcons().add(icon);
            }

            stage.setTitle(title);
            stage.setScene(new Scene(parent));

            // Set visibility of panelAdministratoraButton based on permissions
            int myIntVariable = LoginController.getUprawnieniaZalogowanegoUzytkownika();
            Button panelAdministratoraButton = (Button) loader.getNamespace().get("panelAdministratoraButton");
            if (panelAdministratoraButton != null) {
                panelAdministratoraButton.setVisible(myIntVariable == 1);
            }

            stage.show();

            // Dodaj referencję do otwartego okna do listy
            openStages.add(stage);
        } catch (IOException ex) {
            Logger.getLogger(Controllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initialize() {
        try {
            if (buttonContainer != null) {
                ObservableList<LibraryButton> buttons = addButtons(false);
                buttonContainer.getChildren().addAll(buttons);
            }
            if (buttonContainer1 != null) {
                ObservableList<LibraryButton> buttons1 = addButtons(true);
                buttonContainer1.getChildren().addAll(buttons1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}




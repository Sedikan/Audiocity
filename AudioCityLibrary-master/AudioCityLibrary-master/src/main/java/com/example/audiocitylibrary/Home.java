package com.example.audiocitylibrary;

import com.example.audiocitylibrary.Controllers;
import com.example.audiocitylibrary.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends Application {
    private static final String ICON_PATH = "/AudioTownSmallIcon.png";
    private static final String GLOBAL_STYLE = "/lightStyle.css";

    @Override
    public void start(Stage stage) throws IOException {

        //Controllers.setDefaultIcons(stage);

        FXMLLoader loader = new FXMLLoader(Home.class.getResource("login.fxml"));
        Parent root = loader.load();



        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource(GLOBAL_STYLE).toExternalForm());

        stage.setTitle("AudioTown - login page");
        stage.setScene(scene);


        // Pobierz kontroler z załadowanego pliku FXML
        Object controllerObject = loader.getController();

        System.out.println("Controller Type: " + controllerObject.getClass().getName());

        /*/ Sprawdź typ kontrolera i odpowiednio przypisz
        if (controllerObject instanceof Controllers) {
            Controllers controllers = (Controllers) controllerObject;
            controllers.setPrimaryStage(stage);
        } else if (controllerObject instanceof LoginController) {
            LoginController loginController = (LoginController) controllerObject;
            // Tutaj możesz wykonywać operacje specyficzne dla LoginController
        } else {
            throw new RuntimeException("Unsupported controller type");
        }
    */
        setDefaultIcons(stage);
        stage.show();
    }
    public static void setDefaultIcons(Stage stage) {
        stage.getIcons().add(new Image(Home.class.getResourceAsStream(ICON_PATH)));
    }
    public static void main(String[] args) {
        launch();
    }

}

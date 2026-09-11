package com.example.audiocitylibrary;

import javafx.fxml.FXML;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RegulaminController {
    @FXML
    private TextFlow textFlow;

    private String selectedFilePath;

    // Metoda do ustawiania tekstu w TextFlow
    public void setTextInTextFlow(String text) {
        textFlow.getChildren().clear(); // Wyczyść aktualny tekst
        textFlow.getChildren().add(new javafx.scene.text.Text(text)); // Dodaj nowy tekst
    }

    // Metoda do wczytywania zawartości pliku tekstowego
    public void loadFileContent() {
        if (selectedFilePath != null) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(selectedFilePath)));
                setTextInTextFlow(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSelectedFilePath(String selectedFilePath) {
        this.selectedFilePath = selectedFilePath;
    }
}
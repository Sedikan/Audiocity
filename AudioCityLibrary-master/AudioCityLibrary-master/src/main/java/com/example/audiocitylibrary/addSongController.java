package com.example.audiocitylibrary;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
        import javafx.scene.control.TextField;

import java.sql.*;

public class addSongController {

    @FXML
    private TextField addSong_album;

    @FXML
    private TextField addSong_artist;

    @FXML
    private Button addSong_clear;

    @FXML
    private TextField addSong_name;

    @FXML
    private Button addSong_safe;

    public void setAddSong_safe() {/*
        Alert alert;
        if (addSong_name.getText().isEmpty() || addSong_artist.getText().isEmpty() || addSong_album.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Pola nie mogą być puste");
            alert.showAndWait();
        }else {
            String sql = "SELECT album.idAlbumu, album.nazwaAlbumu,autor.idAutora ,autor.nazwaAutora " +
            " FROM album JOIN autor ON album.idAutora = autor.idAutora"+
                    "  GROUP BY album.idAlbum WHERE autor.nazwaAutora = "+  addSong_artist.getText()+" AND album.nazwaAlbumu "+addSong_album.getText();
            Connection connection = databaseHandler.connectDb();
            PreparedStatement prepare = connection.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                result.getInt("idAlbumu"),
                        result.getInt("idAutora"),
                        result.getString("nazwaAlbumu"),
                        result.getString("nazwaAutora"),
                listAlbum.add(sD);
            }*/
    }

    public void addAlbumAdmin() {
        Connection connection = null;
        PreparedStatement prepare = null;
        ResultSet resultAutor = null;
        ResultSet resultAlbum = null;
        Statement statement = null;
        String insertDataAutor = "INSERT INTO `autor` (`idAutora`, `nazwaAutora`) VALUES (NULL,?)";
        String insertDataAlbum = "INSERT INTO `album` (`idAlbum`, `nazwaAlbumu`, `idAutora`, `cena`, `image`) VALUES (NULL, ?, ?, NULL, '')";
        String insertDataUtwor = "INSERT INTO `utwor` (`idUtworu`, `nazwaUtworu`,`idAutora`,`idAlbumu`) VALUES (NULL,?,?,?)";

        try {
            connection = databaseHandler.connectDb();

            Alert alert;
            if (addSong_name.getText().isEmpty() || addSong_artist.getText().isEmpty() || addSong_album.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Pola nie mogą być puste");
                alert.showAndWait();
            } else {
                String checkAlbum = "SELECT nazwaAlbumu FROM album WHERE nazwaAlbumu = ?";
                String checkArtist = "SELECT idAutora FROM autor WHERE nazwaAutora = ?";
                statement = connection.createStatement();

                // Check if artist exists
                prepare = connection.prepareStatement(checkArtist);
                prepare.setString(1, addSong_artist.getText());
                resultAutor = prepare.executeQuery();
                if (resultAutor.next()) {
                    // Check if album exists
                    prepare = connection.prepareStatement(checkAlbum);
                    prepare.setString(1, addSong_album.getText());
                    resultAlbum = prepare.executeQuery();
                    if (resultAlbum.next()) {
                        // Album and artist exist, proceed with adding song
                        String id_Autor = "SELECT idAutora FROM autor WHERE nazwaAutora = ?";
                        prepare = connection.prepareStatement(id_Autor);
                        prepare.setString(1, addSong_artist.getText());
                        resultAutor = prepare.executeQuery();
                        String id_album = "SELECT idAlbum FROM album WHERE nazwaAlbumu = ?";
                        prepare = connection.prepareStatement(id_album);
                        prepare.setString(1, addSong_album.getText());
                        resultAlbum = prepare.executeQuery();
                        if (resultAutor.next() && resultAlbum.next()) {
                            int temp1 = resultAutor.getInt("idAutora");
                            int temp2 = resultAlbum.getInt("idAlbum");
                            prepare = connection.prepareStatement(insertDataUtwor);
                            prepare.setString(1, addSong_name.getText());
                            prepare.setInt(2, temp1);
                            prepare.setInt(3, temp2);
                            prepare.executeUpdate();
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Info");
                            alert.setHeaderText(null);
                            alert.setContentText("Dodano utwór");
                            alert.showAndWait();
                        }
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Info");
                        alert.setHeaderText(null);
                        alert.setContentText("Podaj istniejący album");
                        alert.showAndWait();
                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Info");
                    alert.setHeaderText(null);
                    alert.setContentText("Podaj istniejącego Artystę oraz Album");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources in finally block to ensure they're properly released
            try {
                if (resultAlbum != null) {
                    resultAlbum.close();
                }
                if (resultAutor != null) {
                    resultAutor.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}





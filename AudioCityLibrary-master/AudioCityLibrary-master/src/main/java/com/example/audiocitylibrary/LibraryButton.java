package com.example.audiocitylibrary;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class LibraryButton extends Button {
    private String albumName;
    private String nazwaAutora;
    private String image;

    public LibraryButton(String albumName, String nazwaAutora, String image) {
        super();
        this.albumName = albumName;
        this.nazwaAutora = nazwaAutora;
        this.image = image;
        setPrefSize(300, 200);
        StackPane layout = new StackPane();
        String url = "file:" + image;
        ImageView imageView = new ImageView(url);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        setGraphic(imageView);
        setText(albumName + "\n" + nazwaAutora);

    }

    public String getAlbumName() {
        return albumName;
    }

    public String getNazwaAutora() {
        return nazwaAutora;
    }

    public String getImageButton() {
        return image;
    }
}

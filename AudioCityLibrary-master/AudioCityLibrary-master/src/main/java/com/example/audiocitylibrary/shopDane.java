package com.example.audiocitylibrary;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
public class shopDane {
    private final StringProperty nazwaAlbumu;
    private final StringProperty artysta;
    private final IntegerProperty iloscUtworow;
    private final IntegerProperty cenaAlbumu;
    private final StringProperty image;
    private int quantity;

    public shopDane(String nazwaAlbumu, String artysta , int iloscUtworow, int cenaAlbumu, String image) {
        this.nazwaAlbumu = new SimpleStringProperty(nazwaAlbumu);
        this.artysta = new SimpleStringProperty(artysta);
        this.iloscUtworow = new SimpleIntegerProperty(iloscUtworow);
        this.cenaAlbumu = new SimpleIntegerProperty(cenaAlbumu);
        this.image = new SimpleStringProperty(image);
    }

    public StringProperty nazwaAlbumuProperty() {
        return nazwaAlbumu;
    }

    public StringProperty artystaProperty() {
        return artysta;
    }

    public IntegerProperty iloscUtworowProperty() {
        return iloscUtworow;
    }

    public IntegerProperty cenaAlbumuProperty() {
        return cenaAlbumu;
    }
    public StringProperty imageProperty() {
        return image;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}


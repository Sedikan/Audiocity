package com.example.audiocitylibrary;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
public class AdminPanelDane {
    private final IntegerProperty idUzytkownika;
    private final StringProperty login;
    private final StringProperty password;
    private final IntegerProperty uprawnienia;
    private final IntegerProperty saldo;
    private final StringProperty email;
    private final StringProperty adres;
    private final IntegerProperty czyPierwszy;

    public AdminPanelDane(int idUzytkownika, String login , String password, int uprawnienia, int saldo, String email, String adres, int czyPierwszy) {
        this.idUzytkownika = new SimpleIntegerProperty(idUzytkownika);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.uprawnienia = new SimpleIntegerProperty(uprawnienia);
        this.saldo = new SimpleIntegerProperty(saldo);
        this.email = new SimpleStringProperty(email);
        this.adres = new SimpleStringProperty(adres);
        this.czyPierwszy = new SimpleIntegerProperty(czyPierwszy);
    }
    public IntegerProperty idUzytkownikaProperty() {
        return idUzytkownika;
    }
    public StringProperty loginProperty() {
        return login;
    }
    public StringProperty passwordProperty() {
        return password;
    }
    public IntegerProperty uprawnieniaProperty() {
        return uprawnienia;
    }
    public IntegerProperty saldoProperty() {
        return saldo;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty adresProperty() {
        return adres;
    }
    public IntegerProperty czyPierwszyProperty() {
        return czyPierwszy;
    }
}

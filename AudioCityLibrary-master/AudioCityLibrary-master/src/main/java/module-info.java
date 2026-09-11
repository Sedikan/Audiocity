module com.example.audiocitylibrary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires java.sql;
    requires java.desktop;


    opens com.example.audiocitylibrary to javafx.fxml;
    exports com.example.audiocitylibrary;
}
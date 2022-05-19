module ija.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens ija.projekt.js to com.google.gson;
    exports ija.projekt;
}
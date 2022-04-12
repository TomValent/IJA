module ija.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens ija.projekt to javafx.fxml;
    exports ija.projekt;
}
module ija.projekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens ija.projekt to javafx.fxml;
    exports ija.projekt;
}
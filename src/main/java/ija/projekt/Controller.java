package ija.projekt;

import ija.projekt.uml.Association;
import ija.projekt.uml.UMLAttribute;
import ija.projekt.uml.UMLClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;

import static ija.projekt.Application.input;
import static ija.projekt.Parser.classDiagram;

/**
 * @author Magdalena Bellayova
 * Controller for our GUI
 * Will include methods for GUI
 */
public class Controller {

    @FXML
    Pane class_pane;

    public void initialize() throws IOException {
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 1;\n";

        int x = 10;
        int y = 10;
        for (UMLClass classes : classDiagram.getClasses()){
            ListView new_class = new ListView<>();
            Label name = new Label();
            name.setFont(Font.font(15));
            name.setText(classes.getName());
            for(UMLAttribute attribute : classes.getAttributes()){
                new_class.getItems().add(attribute.toString());
            }

            VBox container = new VBox();
            container.getChildren().addAll(name, new_class);
            container.setAlignment(Pos.CENTER);
            container.setStyle(cssLayout);
            container.setPrefSize(200, 100);

            class_pane.getChildren().add(container);
            container.relocate(x,y);
            x += 240;
            if (x > 400){
                y += 140;
                x = 10;
            }
        }

    }
    /*


    public void initialize() throws IOException {
        ListView new_class = new ListView<>();
        class_pane.getChildren().add(new_class);
                TitledPane new_class = FXMLLoader.load(getClass().getResource("class.fxml"));
        class_pane.getChildren().add(new_class);
        List<UMLClass> classes = input.classDiagram.getClasses();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("class.fxml"));
        Node new_class  =  loader.load();
        ClassController new_controller = loader.getController();
        new_controller.setText(classes.get(0));
        class_pane.getChildren().add(new_class);
    }
     */

}
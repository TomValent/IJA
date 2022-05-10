package ija.projekt;

import ija.projekt.js.JSMessage;
import ija.projekt.uml.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.security.KeyException;
import java.util.List;
import java.util.Map;

import static ija.projekt.Parser.classDiagram;
import static ija.projekt.Parser.sequenceDiagram;

/**
 * @author Magdalena Bellayova
 * Controller for our GUI
 * Will include methods for GUI
 */
public class Controller {

    ObservableList<Object> list = FXCollections.observableArrayList();
    @FXML
    Pane class_pane;
    @FXML
    Pane seq_pane;

    public void initialize() throws IOException, KeyException {
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 1;\n";

        int x = 10;
        int y = 10;
        for (UMLClass classes : classDiagram.getClasses()){
            ListView<Object> new_class = new ListView<>();
            Label name = new Label();
            name.setFont(Font.font(15));
            name.setText(classes.getName());
            for(UMLAttribute attribute : classes.getAttributes()){
                list.add(attribute.toString());
            }
            new_class.getItems().addAll(list);

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
        //--------------------------------------------------------------
        x = 10;
        y = 20;

        for(UMLQuaestor kvestor : sequenceDiagram.getAllQuaestors()){
            ListView<Object> newQ = new ListView<>();
            Label nameSeq = new Label();
            newQ.getItems().addAll(list);

            nameSeq.setFont(Font.font(15));
            nameSeq.setText(kvestor.getName());

            VBox containerSeq = new VBox();

            containerSeq.getChildren().addAll(nameSeq, newQ);
            containerSeq.setAlignment(Pos.CENTER);
            containerSeq.setStyle(cssLayout);
            containerSeq.setPrefSize(90, 30);

            containerSeq.setAlignment(Pos.TOP_CENTER);
            containerSeq.setStyle(cssLayout);

            Line line = new Line(x+45, 48, x+45, 700);

            sequenceDiagram.getQ(kvestor.getName()).setX(x+45);     //ulozim si kde je vykresleny


            seq_pane.getChildren().add(containerSeq);
            seq_pane.getChildren().add(line);
            Rectangle rectangle = new Rectangle(x+40, 80, 10, 400);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.BLACK);
            seq_pane.getChildren().add(rectangle);
            containerSeq.relocate(x, y);

            x += 120;
        }
        int n = 1;
        for(UMLQuaestor kvestor : sequenceDiagram.getAllQuaestors()){
            for(LifelineObject message : kvestor.getObjects()){
                if(message.getTo().contains(message)){      //does not work - receiver of message cannot make arrow
                    message.setX2(kvestor);
                    continue;
                }
                message.setX1(kvestor);     //sender
                Arrow arrow = new Arrow();

                arrow.setEndX(message.getX2());         //not correct probably
                arrow.setEndY((n)*20+90);

                arrow.setStartX(message.getX1());
                arrow.setStartY((n++)*20+90);
                seq_pane.getChildren().add(arrow);
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
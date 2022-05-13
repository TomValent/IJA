package ija.projekt;

import ija.projekt.js.JSMessage;
import ija.projekt.uml.*;
import ija.projekt.GUI_Creator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyException;

import static ija.projekt.Parser.*;

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
    @FXML
    Label labelClass;
    @FXML
    Label labelSeq;

    public boolean isOn = false;

    public void initialize() throws IOException, KeyException {
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 1;\n";

        GUI_Creator gui_creator = new GUI_Creator();
        labelClass.setText(classDiagram.getName());
        class_pane = gui_creator.createClasses(class_pane);
        class_pane = gui_creator.createAssociations(class_pane);
        class_pane = gui_creator.createGenspecs(class_pane);
        class_pane = gui_creator.createAggrComps(class_pane);
        //--------------------------------------------------------------
        int x = 10;
        int y = 30;

        labelSeq.setText(sequenceDiagram.getName());
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
            containerSeq.setPrefSize(90, 20);

            containerSeq.setAlignment(Pos.TOP_CENTER);
            Line line = new Line(x+45, 52, x+45, 700);

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

                Arrow arrow = new Arrow();

                arrow.setEndX(message.getTarget().getX());      //receiver
                arrow.setEndY((n)*20+90);

                arrow.setStartX(kvestor.getX());                //sender
                arrow.setStartY((n++)*20+90);
                seq_pane.getChildren().add(arrow);
            }
        }
    }

    public void newSeqD(){              //vymaze cast pre sekvencny diagram aby sa dal kreslit novy
        seq_pane.getChildren().clear();
    }

    public void newClassD(){              //vymaze cast pre class diagram aby sa dal kreslit novy
        class_pane.getChildren().clear();
    }

    public void rmBoth(){
        seq_pane.getChildren().clear();
        class_pane.getChildren().clear();
        loadData.removeEverything();
    }

    public void saveToJson() throws IOException, InterruptedException {
        new SaveStage(this);
        Parser output = new Parser();
        output.save();
    }
}
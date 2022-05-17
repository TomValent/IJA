package ija.projekt;

import ija.projekt.uml.*;
import ija.projekt.js.JSMessage;
import ija.projekt.uml.*;
import ija.projekt.GUI_Creator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.security.KeyException;
import java.util.Arrays;
import java.util.HashMap;

import static ija.projekt.Application.globalStage;
import static ija.projekt.Parser.classDiagram;
import static ija.projekt.Parser.*;

/**
 * @author Magdalena Bellayova
 * Controller for our GUI
 * Will include methods for GUI
 */
public class Controller {
    private ObservableList<UMLClass> list = FXCollections.observableArrayList();
    ObservableList<UMLClass> classes = (ObservableList<UMLClass>) classDiagram.getClasses();
    private ListChangeListener<UMLAttribute> attribute_listener = null;
    private ListChangeListener<UMLOperation> method_listener = null;
    private ListChangeListener<UMLClassRelationship> relationship_listener = null;
    private HashMap<VBox, UMLClass> container_class_map = new HashMap<>();
    private HashMap<UMLClass, VBox> class_container_map = new HashMap<>();
    private UMLClass selected_class = null;
    private UMLAttribute selected_attribute = null;
    private UMLOperation selected_method = null;
    private Double startX;
    private Double startY;
    private int x = 20;
    private int y = 20;

    @FXML
    Pane class_pane;
    @FXML
    Pane seq_pane;
    @FXML
    Label labelClass;
    @FXML
    Label labelSeq;
    @FXML
    TextField tf_class_name;
    @FXML
    TextField tf_attribute_name;
    @FXML
    TextField tf_attribute_type;
    @FXML
    ChoiceBox<UMLAttributeModifier> cb_attribute_modifier;
    @FXML
    TextField tf_method_name;
    @FXML
    TextField tf_method_type;
    @FXML
    ListView<UMLAttribute> lv_attributes;
    @FXML
    ListView<UMLOperation> lv_methods;

    private String filename;

    public void initialize() throws IOException, KeyException {
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 1;\n";

        for (UMLClass classes : classDiagram.getClasses()){
            new_class(classes);
        }
        classes.addListener(new ListChangeListener<UMLClass>() {
            @Override
            public void onChanged(Change<? extends UMLClass> change) {
                while(change.next()) {
                    if(change.wasAdded()) {
                        for(UMLClass classs : change.getAddedSubList()) {
                            new_class(classs);
                        }
                    }
                    else if(change.wasRemoved()) {
                        for(UMLClass classs : change.getRemoved()) {
                            if(selected_class==classs) {
                                set_selected_class(null);
                            }
                            class_pane.getChildren().remove(class_container_map.get(classs));
                        }
                    }
                }
            }
        });
        lv_attributes.setOnMouseClicked(event -> {
            set_selected_attribute(lv_attributes.getSelectionModel().getSelectedItem());
        });
        lv_methods.setOnMouseClicked(event -> {
            set_selected_method(lv_methods.getSelectionModel().getSelectedItem());
        });
        /*
        GUI_Creator gui_creator = new GUI_Creator();
        labelClass.setText(classDiagram.getName());
        class_pane = gui_creator.createClasses(class_pane);
        class_pane = gui_creator.createAssociations(class_pane);
        class_pane = gui_creator.createGenspecs(class_pane);
        class_pane = gui_creator.createAggrComps(class_pane);
        */

        cb_attribute_modifier.setItems(FXCollections.observableList(Arrays.asList(UMLAttributeModifier.values())));

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

    public void saveToJson(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./data"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save");

        File savingFile = fileChooser.showSaveDialog(globalStage);
        filename = savingFile.getName();

        Parser output = new Parser();
        try {
            output.save(savingFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveClassName(){
        selected_class.rename(tf_class_name.getText());
    }

    public void createClass(){
        classDiagram.createClass(tf_class_name.getText());
    }

    public void deleteClass(){
        classDiagram.removeClass(selected_class);
    }

    public void new_class(UMLClass classs){
        selected_class = classs;
        VBox class_container = new VBox();
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 1;\n";
        class_container.setAlignment(Pos.CENTER);
        class_container.setStyle(cssLayout);
        class_container.setPrefWidth(200);
        mouseActions(class_container);
        class_pane.getChildren().add(class_container);
        class_container.relocate(x, y);

        class_container_map.put(classs,class_container);
        container_class_map.put(class_container,classs);

        Label name = new Label();
        name.setFont(Font.font(15));
        name.setText(classs.getName());
        classs.addNameListener((observable, old_value, new_value) -> {
            Platform.runLater(() -> {
                name.setText(new_value);
            });
        });
        class_container.getChildren().add(name);

        ObservableList<UMLAttribute> attributes = (ObservableList<UMLAttribute>) classs.getAttributes();
        ListView<UMLAttribute> attribute_list = new ListView<UMLAttribute>(attributes);
        attribute_list.setPrefHeight(attributes.size() * 22 + 5);
        attributes.addListener(new ListChangeListener<UMLAttribute>() {
            @Override
            public void onChanged(Change<? extends UMLAttribute> change) {
                attribute_list.setPrefHeight(attributes.size() * 22 + 5);
            }
        });
        attribute_list.setMouseTransparent(true);
        attribute_list.setFocusTraversable(false);
        class_container.getChildren().add(attribute_list);

        ObservableList<UMLOperation> methods = (ObservableList<UMLOperation>) classs.getMethods();
        ListView<UMLOperation> method_list = new ListView<UMLOperation>(methods);
        method_list.setPrefHeight(methods.size() * 22 + 5);
        methods.addListener(new ListChangeListener<UMLOperation>() {
            @Override
            public void onChanged(Change<? extends UMLOperation> change) {
                method_list.setPrefHeight(methods.size() * 22 + 5);
            }
        });
        method_list.setMouseTransparent(true);
        method_list.setFocusTraversable(false);
        class_container.getChildren().add(method_list);

        x += 300;
        if (x > 400){
            y += 200;
            x = 20;
        }

    }

    public void add_attribute(){
        if (selected_class != null){
            UMLClassifier new_classifier = new UMLClassifier(tf_attribute_type.getText());
            UMLAttribute new_attribute = new UMLAttribute(tf_attribute_name.getText(),new_classifier,cb_attribute_modifier.getValue());
            selected_class.addAttribute(new_attribute);
            tf_attribute_name.clear();
            tf_attribute_type.clear();
            cb_attribute_modifier.setValue(null);
            lv_attributes.getItems().add(new_attribute);
        }
    }

    public void delete_attribute(){
        if(selected_attribute!=null){
            selected_class.removeAttr(selected_attribute);
            lv_attributes.getItems().remove(selected_attribute);
            set_selected_attribute(null);
        }
    }

    public void add_method(){
        if (selected_class != null){
            UMLClassifier new_classifier = new UMLClassifier(tf_method_type.getText());
            UMLOperation new_method = new UMLOperation(tf_method_name.getText(),new_classifier);
            selected_class.addMethod(new_method);
            tf_method_name.clear();
            tf_method_type.clear();
            lv_methods.getItems().add(new_method);
        }
    }

    public void delete_method(){
        if (selected_method != null){
            selected_class.removeMethod(selected_method);
            lv_methods.getItems().remove(selected_method);
            set_selected_method(null);
        }
    }

    public void mouseActions(VBox vbox){
        vbox.setOnMousePressed(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                // Set event start position
                startX = e.getSceneX();
                startY = e.getSceneY();
            }
            if(e.getButton() == MouseButton.SECONDARY) {
                // Show object detail
                selected_class = container_class_map.get(vbox);
                set_selected_class(selected_class);
            }
        });

    }

    public void set_selected_class(UMLClass classs){
        if (classs != null){
            tf_class_name.setText(classs.getName());
            lv_attributes.setItems((ObservableList<UMLAttribute>) classs.getAttributes());
            lv_methods.setItems((ObservableList<UMLOperation>) classs.getMethods());
        }
        else {
            tf_class_name.clear();
            lv_attributes.setItems(null);
            lv_methods.setItems(null);
        }

    }

    public void set_selected_attribute(UMLAttribute attribute){
        if (attribute != null){
            tf_attribute_name.setText(attribute.getName());
            tf_attribute_type.setText(attribute.getType().toString());
            cb_attribute_modifier.setValue(attribute.getModifier());
        }
        else{
            tf_attribute_name.clear();
            tf_attribute_type.clear();
            cb_attribute_modifier.setValue(null);
        }
        selected_attribute = attribute;
    }
    public void set_selected_method(UMLOperation method){
        if (method != null){
            tf_method_name.setText(method.getName());
            tf_method_type.setText(method.getType().getName());
        }
        else{
            tf_method_name.clear();
            tf_method_type.clear();
        }
        selected_method = method;
    }
}
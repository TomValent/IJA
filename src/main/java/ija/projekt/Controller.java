package ija.projekt;

import ija.projekt.js.InOut;
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
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.security.KeyException;
import java.util.*;

import static ija.projekt.Application.globalStage;
import static ija.projekt.Parser.classDiagram;
import static ija.projekt.Parser.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Magdalena Bellayova
 * Controller for our GUI
 * Will include methods for GUI
 */
public class Controller {
    private ObservableList<UMLClass> list = FXCollections.observableArrayList();
    private ObservableList<UMLClass> classes = (ObservableList<UMLClass>) classDiagram.getClasses();
    private ObservableList<Association> associations = (ObservableList<Association>) classDiagram.getAssociations();
    private ObservableList<GeneralizationSpecification> generalizations = (ObservableList<GeneralizationSpecification>) classDiagram.getGenspecs();
    private ObservableList<AggregationComposition> aggrcomps = (ObservableList<AggregationComposition>) classDiagram.getAggrcomps();
    private ObservableList<String> cardinality =
            FXCollections.observableArrayList(
                    "1",
                    "0..1",
                    "0..*",
                    "1..*",
                    "*"
            );
    private ObservableList<String> aggrcomp_type =
            FXCollections.observableArrayList(
                    "aggregation",
                    "composition"
            );


    private HashMap<Group, UMLClass> line_start_map = new HashMap<>();
    private HashMap<Group, UMLClass> line_end_map = new HashMap<>();
    private HashMap<UMLClass,ArrayList<Group>> start_line_map = new HashMap<>();
    private HashMap<UMLClass,ArrayList<Group>> end_line_map = new HashMap<>();

    private HashMap<Group, Association> line_association_map = new HashMap<>();
    private HashMap<Association, Group> association_line_map = new HashMap<>();

    private HashMap<Group, GeneralizationSpecification> line_generalization_map = new HashMap<>();
    private HashMap<GeneralizationSpecification, Group> generalization_line_map = new HashMap<>();
    private HashMap<Group, AggregationComposition> line_aggrcomp_map = new HashMap<>();
    private HashMap<AggregationComposition, Group> aggrcomp_line_map = new HashMap<>();

    private HashMap<VBox, UMLClass> container_class_map = new HashMap<>();
    private HashMap<UMLClass, VBox> class_container_map = new HashMap<>();
    private UMLClass selected_class = null;
    private UMLAttribute selected_attribute = null;
    private UMLOperation selected_method = null;
    private Association selected_association = null;
    private GeneralizationSpecification selected_generalization = null;
    private AggregationComposition selected_aggrcomp = null;
    private Double mouseX;
    private Double mouseY;
    private int x = 20;
    private int y = 20;
    private GUI_Creator gui_creator=new GUI_Creator();

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

    @FXML
    TextField tf_association_name;
    @FXML
    ComboBox<UMLClass> cb_association_class1;
    @FXML
    ComboBox<UMLClass> cb_association_class2;
    @FXML
    ComboBox<String> cb_association_cardinality1;
    @FXML
    ComboBox<String> cb_association_cardinality2;
    @FXML
    ListView<Association> lv_associations;


    @FXML
    ComboBox<UMLClass> cb_generalization_parent;
    @FXML
    ComboBox<UMLClass> cb_generalization_child;
    @FXML
    ListView<GeneralizationSpecification> lv_generalizations;

    @FXML
    ComboBox<UMLClass> cb_aggrcomps_parent;
    @FXML
    ComboBox<UMLClass> cb_aggrcomps_child;
    @FXML
    ComboBox<String> cb_aggrcomps_cardinality;
    @FXML
    ComboBox<String> cb_aggrcomps_type;
    @FXML
    ListView<AggregationComposition> lv_aggrcomps;


    public static Stack<InOut> history = new Stack<InOut>();
    private String filename;

    public void initialize() throws IOException, KeyException {
        labelClass.setText(classDiagram.getName());
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 1;\n";

        x=20;
        y=20;
        for (UMLClass classes : classDiagram.getClasses()){
            new_class(classes);
        }
        set_selected_class(null);
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

                            ArrayList<Group> starts = start_line_map.get(classs);
                            ArrayList<Group> ends = end_line_map.get(classs);
                            if(start_line_map.get(classs)!=null){
                                class_pane.getChildren().removeAll(starts);
                                start_line_map.get(classs).removeAll(starts);
                            }
                            if (end_line_map.get(classs)!=null){
                                class_pane.getChildren().removeAll(ends);
                                end_line_map.get(classs).removeAll(ends);
                            }

                            removeRelationship(classs);



                        }
                    }
                }
            }
        });
        associations.addListener(new ListChangeListener<Association>() {
            @Override
            public void onChanged(Change<? extends Association> change) {
                while(change.next()) {
                    if(change.wasAdded()) {
                        for(Association association : change.getAddedSubList()) {
                            ArrayList<VBox> containers = new ArrayList<>();
                            ArrayList<String> cardinality = new ArrayList<>();
                            for (Map.Entry<UMLClass, String> class_cardinality : association.getClassCardinality().entrySet()) {
                                containers.add(class_container_map.get(class_cardinality.getKey()));
                                cardinality.add(class_cardinality.getValue());
                            }
                            String cardinality1 = cardinality.get(0);
                            String cardinality2 = cardinality.get(1);
                            VBox start = containers.get(0);
                            VBox end = containers.get(1);
                            Group line = createLine(start, end, 4, cardinality1,cardinality2);
                            mouseActions_assoc(line);
                            class_pane.getChildren().add(line);
                            if(start_line_map.get(container_class_map.get(start)) == null){
                                start_line_map.put(container_class_map.get(start),new ArrayList<Group>());
                                start_line_map.get(container_class_map.get(start)).add(line);
                            }
                            else {
                                start_line_map.get(container_class_map.get(start)).add(line);
                            }
                            if(end_line_map.get(container_class_map.get(end)) == null){
                                end_line_map.put(container_class_map.get(end),new ArrayList<Group>());
                                end_line_map.get(container_class_map.get(end)).add(line);
                            }
                            else {
                                end_line_map.get(container_class_map.get(end)).add(line);
                            }
                            line_end_map.put(line, container_class_map.get(start));
                            line_start_map.put(line, container_class_map.get(start));
                            association_line_map.put(association,line);
                            line_association_map.put(line,association);

                        }
                    }
                    /*
                    else if(change.wasRemoved()) {
                        for(Association association : change.getRemoved()) {
                            if(selected_association==association) {
                                set_selected_association(null);
                            }
                            if (class_pane.getChildren().contains(association_line_map.get(association))) {
                                class_pane.getChildren().remove(association_line_map.get(association));
                            }
                        }
                    }

                     */
                }
            }
        });
        generalizations.addListener(new ListChangeListener<GeneralizationSpecification>() {
            @Override
            public void onChanged(Change<? extends GeneralizationSpecification> change) {
                while(change.next()) {
                    if(change.wasAdded()) {
                        for(GeneralizationSpecification generalization : change.getAddedSubList()) {

                            String cardinality1 = "";
                            String cardinality2 = "";
                            VBox start = class_container_map.get(generalization.getParent());
                            VBox end = class_container_map.get(generalization.getChildren().get(0));
                            Group line = createLine(start, end, 2, cardinality1,cardinality2);
                            mouseActions_gen(line);
                            class_pane.getChildren().add(line);
                            if(start_line_map.get(container_class_map.get(start)) == null){
                                start_line_map.put(container_class_map.get(start),new ArrayList<Group>());
                                start_line_map.get(container_class_map.get(start)).add(line);
                            }
                            else {
                                start_line_map.get(container_class_map.get(start)).add(line);
                            }
                            if(end_line_map.get(container_class_map.get(end)) == null){
                                end_line_map.put(container_class_map.get(end),new ArrayList<Group>());
                                end_line_map.get(container_class_map.get(end)).add(line);
                            }
                            else {
                                end_line_map.get(container_class_map.get(end)).add(line);
                            }
                            line_end_map.put(line, container_class_map.get(start));
                            line_start_map.put(line, container_class_map.get(start));
                            generalization_line_map.put(generalization,line);
                            line_generalization_map.put(line,generalization);

                        }
                    }
                    /*
                    else if(change.wasRemoved()) {
                        for(GeneralizationSpecification generalization : change.getRemoved()) {
                            if(selected_generalization==generalization) {
                                set_selected_generalization(null);
                            }
                            if (class_pane.getChildren().contains(generalization_line_map.get(generalization))){
                                class_pane.getChildren().remove(generalization_line_map.get(generalization));
                            }
                        }
                    }

                     */
                }
            }
        });
        aggrcomps.addListener(new ListChangeListener<AggregationComposition>() {
            @Override
            public void onChanged(Change<? extends AggregationComposition> change) {
                while(change.next()) {
                    if(change.wasAdded()) {
                        for(AggregationComposition aggrcomp : change.getAddedSubList()) {
                            String cardinality1 = aggrcomp.getChildCardinality();
                            String cardinality2 = "";
                            VBox start = class_container_map.get(aggrcomp.getParent());
                            VBox end = class_container_map.get(aggrcomp.getChild());
                            int type = aggrcomp.getType();
                            Group line = createLine(start, end, type, cardinality1, cardinality2);
                            mouseActions_aggrcomp(line);
                            class_pane.getChildren().add(line);
                            if(start_line_map.get(container_class_map.get(start)) == null){
                                start_line_map.put(container_class_map.get(start),new ArrayList<Group>());
                                start_line_map.get(container_class_map.get(start)).add(line);
                            }
                            else {
                                start_line_map.get(container_class_map.get(start)).add(line);
                            }
                            if(end_line_map.get(container_class_map.get(end)) == null){
                                end_line_map.put(container_class_map.get(end),new ArrayList<Group>());
                                end_line_map.get(container_class_map.get(end)).add(line);
                            }
                            else {
                                end_line_map.get(container_class_map.get(end)).add(line);
                            }
                            line_end_map.put(line, container_class_map.get(start));
                            line_start_map.put(line, container_class_map.get(start));
                            aggrcomp_line_map.put(aggrcomp,line);
                            line_aggrcomp_map.put(line,aggrcomp);

                        }
                    }
                    /*
                    else if(change.wasRemoved()) {
                        for(AggregationComposition aggrcomp : change.getRemoved()) {
                            if(selected_aggrcomp==aggrcomp) {
                                set_selected_aggrcomp(null);
                            }
                            if (class_pane.getChildren().contains(aggrcomp_line_map.get(aggrcomp))) {
                                class_pane.getChildren().remove(aggrcomp_line_map.get(aggrcomp));
                            }
                        }
                    }

                     */
                }
            }
        });
        lv_attributes.setOnMouseClicked(event -> {
            set_selected_attribute(lv_attributes.getSelectionModel().getSelectedItem());
        });
        lv_methods.setOnMouseClicked(event -> {
            set_selected_method(lv_methods.getSelectionModel().getSelectedItem());
        });
        lv_associations.setOnMouseClicked(mouseEvent -> {
            set_selected_association(lv_associations.getSelectionModel().getSelectedItem());
        });
        lv_generalizations.setOnMouseClicked(mouseEvent -> {
            set_selected_generalization(lv_generalizations.getSelectionModel().getSelectedItem());
        });
        lv_aggrcomps.setOnMouseClicked(mouseEvent -> {
            set_selected_aggrcomp(lv_aggrcomps.getSelectionModel().getSelectedItem());
        });

        createAssociations();
        createGenspecs();
        createAggrComps();
        //class_pane = gui_creator.createAssociations(class_pane, class_container_map);
        //class_pane = gui_creator.createGenspecs(class_pane, class_container_map);
        //class_pane = gui_creator.createAggrComps(class_pane, class_container_map);

        cb_attribute_modifier.setItems(FXCollections.observableList(Arrays.asList(UMLAttributeModifier.values())));
        cb_association_class1.setItems(classes);
        cb_association_class2.setItems(classes);


        cb_association_cardinality1.setItems(cardinality);
        cb_association_cardinality2.setItems(cardinality);
        lv_associations.setItems(associations);

        cb_generalization_parent.setItems(classes);
        cb_generalization_child.setItems(classes);
        lv_generalizations.setItems(generalizations);

        cb_aggrcomps_parent.setItems(classes);
        cb_aggrcomps_child.setItems(classes);
        cb_aggrcomps_cardinality.setItems(cardinality);
        cb_aggrcomps_type.setItems(aggrcomp_type);
        lv_aggrcomps.setItems(aggrcomps);

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
                arrow.setStartY((n)*20+90);
                seq_pane.getChildren().add(arrow);

                Label msgText = new Label(message.getDesc());
                if(kvestor.getX() < message.getTarget().getX()) {
                    msgText.relocate(kvestor.getX()+20, (n++)*20+75);
                    seq_pane.getChildren().add(msgText);
                }
                else {
                    msgText.relocate(kvestor.getX()-50, (n++)*20+75);
                    seq_pane.getChildren().add(msgText);
                }
            }
        }
    }

    public void openFile(ActionEvent actionEvent) throws IOException, KeyException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./data"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open");
        File newFile = fileChooser.showOpenDialog(globalStage);
        filename = newFile.getName();

        rmBoth();

        Parser input = new Parser();
        input.parse("./data/" + filename);
        this.initialize();
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

    public void undo() throws IOException, KeyException {
        if(!history.empty()){
            loadData = history.pop();
            rmBoth();
            initialize();
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
        class_container.setUserData(classs.getName());
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
        set_selected_class(classs);
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

    public void add_association(){
            Association association = new Association(tf_association_name.getText());
            association.addClasswithCardinality(cb_association_class1.getValue(), cb_association_cardinality1.getValue());
            association.addClasswithCardinality(cb_association_class2.getValue(), cb_association_cardinality2.getValue());
            classDiagram.addAssociation(association);
            selected_association = association;
            tf_association_name.clear();
            cb_association_class1.setValue(null);
            cb_association_class2.setValue(null);
            cb_association_cardinality1.setValue(null);
            cb_association_cardinality2.setValue(null);
            lv_associations.getItems().add(association);
    }

    public void delete_association(){
        if (selected_association != null) {
            classDiagram.removeAssociation(selected_association);
            lv_associations.getItems().remove(selected_association);
            if (class_pane.getChildren().contains(association_line_map.get(selected_association))){
                class_pane.getChildren().remove(association_line_map.get(selected_association));
            }
            set_selected_association(null);
        }
    }

    public void add_generalization(){
        GeneralizationSpecification generalization = new GeneralizationSpecification("genspec", cb_generalization_parent.getValue(),2);
        generalization.addChild(cb_generalization_child.getValue());
        classDiagram.addGeneralization(generalization);
        cb_generalization_parent.setValue(null);
        cb_generalization_child.setValue(null);
    }

    public void delete_generalization(){
        if (selected_generalization!=null){
            classDiagram.removeGeneralization(selected_generalization);
            lv_generalizations.getItems().remove(selected_generalization);
            if (class_pane.getChildren().contains(generalization_line_map.get(selected_generalization))){
                class_pane.getChildren().remove(generalization_line_map.get(selected_generalization));
            }
            set_selected_generalization(null);
        }
    }

    public void add_aggrcomp(){
        AggregationComposition aggrcomp = new AggregationComposition("genspec", cb_aggrcomps_parent.getValue(),cb_aggrcomps_child.getValue(),cb_aggrcomps_cardinality.getValue(),aggrcomp_type.indexOf(cb_aggrcomps_type.getValue()));
        classDiagram.addAggrcomp(aggrcomp);
        cb_aggrcomps_parent.setValue(null);
        cb_aggrcomps_child.setValue(null);
        cb_aggrcomps_type.setValue(null);
        cb_aggrcomps_cardinality.setValue(null);
    }

    public void delete_aggrcomp(){
        if (selected_aggrcomp!=null){
            classDiagram.removeAggrcomp(selected_aggrcomp);
            lv_aggrcomps.getItems().remove(selected_aggrcomp);
            if (class_pane.getChildren().contains(aggrcomp_line_map.get(selected_aggrcomp))){
                class_pane.getChildren().remove(aggrcomp_line_map.get(selected_aggrcomp));
            }
            set_selected_aggrcomp(null);
        }
    }

    public void removeRelationship(UMLClass classs){
        ArrayList<Association> remove_assoc = new ArrayList<>();
        ArrayList<GeneralizationSpecification> remove_gen = new ArrayList<>();
        ArrayList<AggregationComposition> remove_aggr = new ArrayList<>();
        for(Association association: classDiagram.getAssociations()){
            System.out.println(association);
            for (Map.Entry<UMLClass, String> class_cardinality : association.getClassCardinality().entrySet()) {
                if(class_cardinality.getKey()==classs){
                    remove_assoc.add(association);
                }
            }
        }
        System.out.println("generalizations");
        for(GeneralizationSpecification generalization: classDiagram.getGenspecs()){
            System.out.println(generalization);
            if(generalization.getParent()==classs){
                remove_gen.add(generalization);
                System.out.println("removed");
            }
            else if(generalization.getChildren().get(0)==classs){
                remove_gen.add(generalization);
                System.out.println("removed");
            }
        }
        System.out.println("aggrcomps");
        for(AggregationComposition aggrcomp: classDiagram.getAggrcomps()){
            System.out.println(aggrcomp);
            if(aggrcomp.getParent()==classs){
                remove_aggr.add(aggrcomp);
                System.out.println("removed");
            }
            else if(aggrcomp.getChild()==classs){
                remove_aggr.add(aggrcomp);
                System.out.println("removed");
            }
        }
        classDiagram.removeRelationships(remove_assoc,remove_gen,remove_aggr);
    }
    public void mouseActions(VBox vbox){
        vbox.setOnMousePressed(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                mouseX = mouseEvent.getSceneX();
                mouseY = mouseEvent.getSceneY();
            }
            if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                selected_class = container_class_map.get(vbox);
                set_selected_class(selected_class);
            }
        });
        vbox.setOnMouseDragged(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                double posX = mouseEvent.getSceneX() - mouseX;
                double posY = mouseEvent.getSceneY() - mouseY;
                Bounds bounds = vbox.getBoundsInParent();
                double x_min = bounds.getMinX();
                double y_min = bounds.getMinY();
                double x_max = bounds.getMaxX();
                double y_max = bounds.getMaxY();

                if(x_min + posX >= 0 && x_max + posX <= class_pane.getWidth()) {
                    vbox.setLayoutX(vbox.getLayoutX() + posX);
                }
                if(y_min + posY >= 0 && y_max + posY <= class_pane.getHeight()) {
                    vbox.setLayoutY(vbox.getLayoutY() + posY);
                }
                mouseX = mouseEvent.getSceneX();
                mouseY = mouseEvent.getSceneY();
            }
        });

    }
    public void mouseActions_assoc(Group line){
        line.setOnMousePressed(e -> {
            selected_association = line_association_map.get(line);
            set_selected_association(selected_association);
        });
    }
    public void mouseActions_gen(Group line){
        line.setOnMousePressed(e -> {
            selected_generalization = line_generalization_map.get(line);
            set_selected_generalization(selected_generalization);
        });
    }
    public void mouseActions_aggrcomp(Group line){
        line.setOnMousePressed(e -> {
            selected_aggrcomp = line_aggrcomp_map.get(line);
            set_selected_aggrcomp(selected_aggrcomp);
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

    public void set_selected_association(Association association){
        if (association != null){
            tf_association_name.setText(association.getName());
            ArrayList<UMLClass> classes = new ArrayList<>();
            ArrayList<String> cardinality = new ArrayList<>();
            for (Map.Entry<UMLClass, String> class_cardinality : association.getClassCardinality().entrySet()) {
                classes.add(class_cardinality.getKey());
                cardinality.add(class_cardinality.getValue());
            }
            String cardinality1 = cardinality.get(0);
            String cardinality2 = cardinality.get(1);
            UMLClass start = classes.get(0);
            UMLClass end = classes.get(1);
            cb_association_class1.setValue(start);
            cb_association_cardinality1.setValue(cardinality1);
            cb_association_class2.setValue(end);
            cb_association_cardinality2.setValue(cardinality2);
        }
        else{
            tf_association_name.clear();
            cb_association_class1.setValue(null);
            cb_association_class2.setValue(null);
            cb_association_cardinality1.setValue(null);
            cb_association_cardinality2.setValue(null);
        }
        selected_association = association;

    }

    public void set_selected_generalization(GeneralizationSpecification generalization){
        if (generalization != null){
            cb_generalization_parent.setValue(generalization.getParent());
            cb_generalization_child.setValue(generalization.getChildren().get(0));
        }
        else{
            cb_generalization_parent.setValue(null);
            cb_generalization_child.setValue(null);
        }
        selected_generalization = generalization;
    }

    public void set_selected_aggrcomp(AggregationComposition aggrcomp){
        if (aggrcomp != null){
            cb_aggrcomps_parent.setValue(aggrcomp.getParent());
            cb_aggrcomps_child.setValue(aggrcomp.getChild());
            cb_aggrcomps_cardinality.setValue(aggrcomp.getChildCardinality());
            cb_aggrcomps_type.setValue(aggrcomp_type.get(aggrcomp.getType()));
        }
        else{
            cb_aggrcomps_parent.setValue(null);
            cb_aggrcomps_child.setValue(null);
            cb_aggrcomps_cardinality.setValue(null);
            cb_aggrcomps_type.setValue(null);
        }
        selected_aggrcomp = aggrcomp;
    }

    public void createAssociations(){
        for( Association new_assoc : classDiagram.getAssociations()) {
            ArrayList<VBox> containers = new ArrayList<>();
            ArrayList<String> cardinality = new ArrayList<>();
            for (Map.Entry<UMLClass, String> class_cardinality : new_assoc.getClassCardinality().entrySet()) {
                for (Node n : class_pane.getChildren()) {
                    if (class_cardinality.getKey().getName().equals(n.getUserData())) {
                        containers.add(class_container_map.get(class_cardinality.getKey()));
                        cardinality.add(class_cardinality.getValue());
                    }
                }
            }
            String cardinality1 = cardinality.get(0);
            String cardinality2 = cardinality.get(1);
            VBox start = containers.get(0);
            VBox end = containers.get(1);
            Group root = new Group();
            Scene scene = new Scene(root);
            root.getChildren().addAll(start,end);

            root.applyCss();
            root.layout();

            Group line = createLine(start, end, 4, cardinality1,cardinality2);
            mouseActions_assoc(line);
            class_pane.getChildren().add(line);
            class_pane.getChildren().remove(root);
            class_pane.getChildren().addAll(start,end);
            if(start_line_map.get(container_class_map.get(start)) == null){
                start_line_map.put(container_class_map.get(start),new ArrayList<Group>());
                start_line_map.get(container_class_map.get(start)).add(line);
            }
            else {
                start_line_map.get(container_class_map.get(start)).add(line);
            }
            if(end_line_map.get(container_class_map.get(end)) == null){
                end_line_map.put(container_class_map.get(end),new ArrayList<Group>());
                end_line_map.get(container_class_map.get(end)).add(line);
            }
            else {
                end_line_map.get(container_class_map.get(end)).add(line);
            }
            line_end_map.put(line, container_class_map.get(start));
            line_start_map.put(line, container_class_map.get(start));
            association_line_map.put(new_assoc,line);
            line_association_map.put(line,new_assoc);
        }
    }
    public void createGenspecs(){
        for( GeneralizationSpecification new_genspec : classDiagram.getGenspecs()) {
            ArrayList<VBox> containers = new ArrayList<>();
            for (Node n : class_pane.getChildren()) {
                if (new_genspec.getParent().getName().equals(n.getUserData())) {
                    containers.add(class_container_map.get(new_genspec.getParent()));
                }
            }
            for (UMLClass child: new_genspec.getChildren()) {
                for (Node n : class_pane.getChildren()) {
                    if (child.getName().equals(n.getUserData())) {
                        containers.add(class_container_map.get(child));
                    }
                }
            }
            int type = new_genspec.getType() + 2;
            VBox parent = containers.get(0);
            containers.remove(0);
            while (containers.size()>0){
                VBox child = containers.get(0);
                Group root = new Group();
                Scene scene = new Scene(root);
                root.getChildren().addAll(parent,child);

                root.applyCss();
                root.layout();

                Group line = this.createLine(parent,child,  type,"","");
                mouseActions_gen(line);
                class_pane.getChildren().add(line);
                class_pane.getChildren().remove(root);
                class_pane.getChildren().addAll(parent,child);
                if(start_line_map.get(container_class_map.get(parent)) == null){
                    start_line_map.put(container_class_map.get(parent),new ArrayList<Group>());
                    start_line_map.get(container_class_map.get(parent)).add(line);
                }
                else {
                    start_line_map.get(container_class_map.get(parent)).add(line);
                }
                if(end_line_map.get(container_class_map.get(child)) == null){
                    end_line_map.put(container_class_map.get(child),new ArrayList<Group>());
                    end_line_map.get(container_class_map.get(child)).add(line);
                }
                else {
                    end_line_map.get(container_class_map.get(child)).add(line);
                }
                line_end_map.put(line, container_class_map.get(parent));
                line_start_map.put(line, container_class_map.get(child));
                generalization_line_map.put(new_genspec,line);
                line_generalization_map.put(line,new_genspec);
                containers.remove(0);
            }
        }
    }

    public void createAggrComps(){
        for( AggregationComposition new_aggrcomp : classDiagram.getAggrcomps()) {
            ArrayList<VBox> containers = new ArrayList<>();
            String child_cardinality = "";
            for (Node n : class_pane.getChildren()) {
                if (new_aggrcomp.getParent().getName().equals(n.getUserData()))  {
                    containers.add(class_container_map.get(new_aggrcomp.getParent()));
                }
            }
            for (Node n : class_pane.getChildren()) {
                if (new_aggrcomp.getChild().getName().equals(n.getUserData()))  {
                    containers.add(class_container_map.get(new_aggrcomp.getChild()));
                    child_cardinality = new_aggrcomp.getChildCardinality();
                }
            }
            int type = new_aggrcomp.getType();
            VBox parent = containers.get(0);
            VBox child = containers.get(1);
            Group root = new Group();
            Scene scene = new Scene(root);
            root.getChildren().addAll(parent,child);

            root.applyCss();
            root.layout();
            Group line = this.createLine(parent,child, type,child_cardinality, "");
            mouseActions_aggrcomp(line);
            class_pane.getChildren().add(line);
            class_pane.getChildren().remove(root);
            class_pane.getChildren().addAll(parent,child);
            if(start_line_map.get(container_class_map.get(parent)) == null){
                start_line_map.put(container_class_map.get(parent),new ArrayList<Group>());
                start_line_map.get(container_class_map.get(parent)).add(line);
            }
            else {
                start_line_map.get(container_class_map.get(parent)).add(line);
            }
            if(end_line_map.get(container_class_map.get(child)) == null){
                end_line_map.put(container_class_map.get(child),new ArrayList<Group>());
                end_line_map.get(container_class_map.get(child)).add(line);
            }
            else {
                end_line_map.get(container_class_map.get(child)).add(line);
            }
            line_end_map.put(line, container_class_map.get(parent));
            line_start_map.put(line, container_class_map.get(child));
            aggrcomp_line_map.put(new_aggrcomp,line);
            line_aggrcomp_map.put(line,new_aggrcomp);
        }
    }
    public Group createLine(VBox start, VBox end , int type, String cardinality1, String cardinality2){
        Line line = new Line();

        double height1 = start.getHeight();
        double width1 = start.getPrefWidth();
        double height2 = end.getHeight();
        double width2 = end.getPrefWidth();

        double x1 = start.getLayoutX();
        double y1 = start.getLayoutY();
        double x2 = end.getLayoutX();
        double y2 = end.getLayoutY();


        double help_x1 = x1;
        double help_y1 = y1;
        double help_x2 = x2;
        double help_y2 = y2;
        x2 +=  width2/2;
        y2 += height2/2;
        double x11 = x1+ width1/2;
        double y11 =y1+height1/2;
        double x22 =x2;
        double y22 = y2;

        double x3 = x1;
        double y3 = y1;
        double x4 = x1+width1;
        double y4= y1;

        double help_x = x1 +  width1/2;
        double help_y = y1 + height1/2;
        double point1_x=0;
        double point1_y=0;
        double point2_x=0;
        double point2_y=0;
        double point3_x=0;
        double point3_y=0;
        double point4_x=0;
        double point4_y=0;

        double d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point1_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point1_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x1;
        y3 = y1 + height1;
        x4 = x1 + width1;
        y4= y1 + height1;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point2_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point2_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x1;
        y3 = y1;
        x4 = x1;
        y4= y1 +height1;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point3_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point3_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x1 + width1;
        y3 = y1;
        x4 = x1 + width1;
        y4= y1 + height1;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point4_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point4_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x1 = help_x;
        y1 = help_y;


        double secondpath_x = 0;
        double secondpath_y = 0;
        double path11=0;
        double path22=0;

        double path1 = sqrt((point1_y - y1) * (point1_y - y1) + (point1_x - x1) * (point1_x - x1));
        double minpath = path1;
        help_x = point1_x;
        help_y = point1_y;
        double path2 = sqrt((point2_y - y1) * (point2_y - y1) + (point2_x - x1) * (point2_x - x1));
        if (path2 == minpath){
            secondpath_x = point2_x;
            secondpath_y = point2_y;
        }
        if(path2<minpath){
            minpath = path2;
            help_x = point2_x;
            help_y = point2_y;
        }
        double path3 = sqrt((point3_y - y1) * (point3_y - y1) + (point3_x - x1) * (point3_x - x1));
        if (path3 == minpath){
            secondpath_x = point3_x;
            secondpath_y = point3_y;
        }
        if(path3<minpath){
            minpath = path3;
            help_x = point3_x;
            help_y = point3_y;
        }
        double path4 = sqrt((point4_y - y1) * (point4_y - y1) + (point4_x - x1) * (point4_x - x1));
        if (path4 == minpath){
            secondpath_x = point4_x;
            secondpath_y = point4_y;
        }
        if(path4<minpath){
            minpath = path4;
            help_x = point4_x;
            help_y = point4_y;
        }

        if (secondpath_y!=0 && secondpath_x!=0){
            path11= sqrt((help_y - y2) * (help_y - y2) + (help_x - x2) * (help_x - x2));
            path22= sqrt((secondpath_y - y2) * (secondpath_y - y2) + (secondpath_x - x2) * (secondpath_x - x2));
            if (path11 > path22){
                help_x=secondpath_x;
                help_y=secondpath_y;
            }
        }

        x1 = help_x;
        y1 = help_y;

        help_x = x2;
        help_y = y2;
        x2 -=  width2/2;
        y2 -= height2/2;

        x11 = help_x1+ width1/2;
        y11 =help_y1+height1/2;
        x22 =x2+ width2/2;
        y22 = y2+ height2/2;
        x3 = x2;
        y3 = y2;
        x4 = x2+width2;
        y4= y2;
        point1_x=0;
        point1_y=0;
        point2_x=0;
        point2_y=0;
        point3_x=0;
        point3_y=0;
        point4_x=0;
        point4_y=0;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point1_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point1_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x2;
        y3 = y2 + height2;
        x4 = x2 + width2;
        y4= y2 +height2;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point2_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point2_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x2;
        y3 = y2;
        x4 = x2;
        y4= y2 +height2;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point3_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point3_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x2 + width2;
        y3 = y2;
        x4 = x2 + width2;
        y4= y2 + height2;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point4_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point4_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }
        x2 = help_x;
        y2 = help_y;

        secondpath_x =0;
        secondpath_y =0;
        path1 = sqrt((point1_y - y2) * (point1_y - y2) + (point1_x - x2) * (point1_x - x2));
        minpath = path1;
        help_x2 = point1_x;
        help_y2 = point1_y;
        path2 = sqrt((point2_y - y2) * (point2_y - y2) + (point2_x - x2) * (point2_x - x2));
        if(path2<minpath){
            minpath = path2;
            help_x2 = point2_x;
            help_y2 = point2_y;
        }
        else if (path2 == minpath){
            secondpath_x = point2_x;
            secondpath_y = point2_y;
        }
        path3 = sqrt((point3_y - y2) * (point3_y - y2) + (point3_x - x2) * (point3_x - x2));
        if(path3<minpath){
            minpath = path3;
            help_x2 = point3_x;
            help_y2 = point3_y;
        }
        else if (path3 == minpath){
            secondpath_x = point3_x;
            secondpath_y = point3_y;
        }
        path4 = sqrt((point4_y - y2) * (point4_y - y2) + (point4_x - x2) * (point4_x - x2));
        if(path4 < minpath){
            minpath = path4;
            help_x2 = point4_x;
            help_y2 = point4_y;
        }
        else if (path4 == minpath){
            secondpath_x = point4_x;
            secondpath_y = point4_y;
        }

        if (secondpath_y!=0 && secondpath_x!=0){
            path11= sqrt((help_y2 - y1) * (help_y2 - y1) + (help_x2 - x1) * (help_x2 - x1));
            path22= sqrt((secondpath_y - y1) * (secondpath_y - y1) + (secondpath_x - x1) * (secondpath_x - x1));
            if (path11 > path22){
                help_x2=secondpath_x;
                help_y2=secondpath_y;
            }
        }
        x2 = help_x2;
        y2 = help_y2;

        Group arrow_line = new Group();
        Polygon polygon = new Polygon();
        Double bx = x1;
        Double by = y1;

        if (type == 0) {
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double middlex = (x1 + bx) / 2;
            Double middley = (y1 + by) / 2;
            Double nx = - sy;
            Double ny = sx;
            Double v1x = middlex - (nx * (partition/3));
            Double v1y = middley - (ny * (partition/3));
            Double v2x = middlex + (nx * (partition/3));
            Double v2y = middley + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,bx,by,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0));
            Double cx = x2 - (sx * (partition-0.1));
            Double cy = y2 - (sy * (partition-0.1));
            Text t1 = new Text(cx, cy, cardinality1);
            t1.setFont(new Font(10));
            line.setStartX(bx);
            line.setStartY(by);
            line.setEndX(x2);
            line.setEndY(y2);
            arrow_line.getChildren().addAll(polygon,line,t1);
            return arrow_line;
        }
        if (type == 1){
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double middlex = (x1 + bx) / 2;
            Double middley = (y1 + by) / 2;
            Double nx = - sy;
            Double ny = sx;
            Double v1x = middlex - (nx * (partition/3));
            Double v1y = middley - (ny * (partition/3));
            Double v2x = middlex + (nx * (partition/3));
            Double v2y = middley + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,bx,by,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.BLACK);
            Double cx = x2 - (sx * (partition-0.1));
            Double cy = y2 - (sy * (partition-0.1));
            Text t1 = new Text(cx, cy, cardinality1);
            t1.setFont(new Font(10));
            line.setStartX(bx);
            line.setStartY(by);
            line.setEndX(x2);
            line.setEndY(y2);
            arrow_line.getChildren().addAll(polygon,line,t1);
            return arrow_line;
        }
        if(type == 2){
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double nx = - sy;
            Double ny = sx;
            Double v1x = bx - (nx * (partition/3));
            Double v1y = by - (ny * (partition/3));
            Double v2x = bx + (nx * (partition/3));
            Double v2y = by + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0));
        }
        if(type == 3){
            //Double tmpx=x1;
            //Double tmpy=y1;
            //x1=x2;y1=y2;x2=tmpx;y2=tmpy;
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double nx = - sy;
            Double ny = sx;
            Double v1x = bx - (nx * (partition/3));
            Double v1y = by - (ny * (partition/3));
            Double v2x = bx + (nx * (partition/3));
            Double v2y = by + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0));
        }
        if (type==4){
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 5/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double cx = x2 - (sx * (partition+0.06));
            Double cy = y2 - (sy * (partition+0.06));
            Text t1 = new Text(bx, by, cardinality1);
            t1.setFont(new Font(10));
            Text t2 = new Text(cx, cy, cardinality2);
            t2.setFont(new Font(10));
            line.setStartX(x1);
            line.setStartY(y1);
            line.setEndX(x2);
            line.setEndY(y2);
            arrow_line.getChildren().addAll(t1,t2,line);
            return arrow_line;
        }

        line.setStartX(bx);
        line.setStartY(by);
        line.setEndX(x2);
        line.setEndY(y2);
        arrow_line.getChildren().addAll(polygon,line);
        return arrow_line;
    }

}
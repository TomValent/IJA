package ija.projekt;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class SaveStage {
    Stage getFilename;
    Label filenameField;
    Button submit;
    Button cancel;
    GridPane gridPane;
    public String file;
    TextField textField;
    Controller cont;

    public void createForm(){
        this.filenameField =  new Label("Filename");
        this.textField = new TextField("output");
        this.gridPane.add(this.filenameField, 0, 0);
        this.gridPane.add(this.textField, 1, 0);

        this.submit = new Button("Submit");
        this.cancel = new Button("Cancel");

        this.gridPane.add(this.cancel, 0, 2);
        this.gridPane.add(this.submit, 1, 2);

        createBindings();
    }

    public SaveStage(Controller cont){
        this.cont = cont;
        this.getFilename = new Stage();
        getFilename.setTitle("Insert filename");
        this.gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 25, 25, 15));
        this.createForm();

        Scene newEntityScene = new Scene(gridPane, 300, 120);

        getFilename.setScene(newEntityScene);
        getFilename.show();
    }

    public void createBindings() {
        this.cancel.setOnAction(event -> {
            getFilename.close();
        });

        this.submit.setOnAction(event -> {
            System.out.println(textField + " setFile()");
            this.file = textField.getText();
            getFilename.close();
        });
    }

    public String getFile(){
        return this.file;
    }
}

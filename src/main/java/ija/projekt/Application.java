package ija.projekt;

import ija.projekt.uml.UMLClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    static Parser input;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("DiagramEditor");
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getClassLoader().getResource("welcome-screen.fxml"));
        Scene secondScene = new Scene(fxmlLoader2.load(), 600, 330);
        Stage newWindow = new Stage();
        newWindow.setTitle("Welcome to DiagramEditor");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.setResizable(false);
        newWindow.show();


    }

    public static void main(String[] args) throws IOException {
        parse();
        launch();
    }

    public static void parse() throws IOException {
        input = new Parser();
        try{
            input.parse("./data/example.json");
        }catch(IOException e){
            System.err.println(e);
        }
        input.save();
    }


}
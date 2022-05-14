package ija.projekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.KeyException;


public class Application extends javafx.application.Application {
    @FXML
    public static Stage globalStage;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
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

        globalStage = stage;
    }

    public static void main(String[] args) throws IOException {
        parse();
        launch();
    }

    public static void parse() throws IOException {
        Parser input = new Parser();
        try{
            input.parse("./data/example.json");
        }catch(KeyException | IllegalStateException e){
            System.err.println(e);
        }
    }

}
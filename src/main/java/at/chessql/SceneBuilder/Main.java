package at.chessql.SceneBuilder;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try{
            //URL url = new File("fxml/sample2.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample2.fxml"));
            Scene scene = new Scene(root,800,600);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void main(String args[]){
        launch(args);
    }
}
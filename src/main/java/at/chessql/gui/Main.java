package at.chessql.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{



        //FXMLLoader loader = FXMLLoader.load(getClass().getClassLoader().getResource("sample1.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("sample1.fxml"));
        //AnchorPane root = (AnchorPane)loader.getNamespace().get("AnchorPane");
        //Parent root = loader.load();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample1.fxml"));



        //GridPane window = (GridPane)loader.getNamespace().get("connect-pane");
        //window.add(hallo,0,0);

        primaryStage.setTitle("ChessQL");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }



}

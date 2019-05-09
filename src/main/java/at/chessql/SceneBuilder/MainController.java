package at.chessql.SceneBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

public class MainController {

    private String psw;
    private String user;
    private String ip;
    @FXML
    private TextField ip_textfield;
    @FXML
    private TextField username_textfield;
    @FXML
    private TextField psw_textfield;
    public void handle(ActionEvent event) {
        ip= ip_textfield.getText();
        user=username_textfield.getText();
        if(psw_textfield.getText().isEmpty()){
            psw="";
        }
        else{
            psw=psw_textfield.getText();
        }

    }

}

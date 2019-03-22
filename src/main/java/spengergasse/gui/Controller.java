package spengergasse.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Controller {

    Label hallo = new Label("was los");

    @FXML
    private GridPane fxConnectPane;

    @FXML
    private void initialize(){
        fxConnectPane.add(hallo, 1,1);
    }
}

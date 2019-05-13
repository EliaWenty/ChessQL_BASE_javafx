package at.chessql.depricated;

import at.chessql.logik.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Gui extends Application {
    String auswahl = "hallo";
    Label label = new Label();
    Feld feld = new Feld();
    final int size = 8 ;
    TextField eingabe = new TextField();
    GridPane window = new GridPane();
    GridPane controllP = new GridPane();
    GridPane gridPane = new GridPane();
    int zugCounter = 0;
    HashMap<String,String> oldfield = new HashMap<>();
    List<String> totS = new ArrayList<>();
    List<String> totW = new ArrayList<>();
    String zielfeld="";
    final int windowsize = 13;
    String trennzeichen = "-";
    String ausgabe = "";
    Label ausgewählt = new Label("");
    Label möglich = new Label();
    Label figuraus = new Label();
    Label anleitung = new Label();
    //Image spielanleitung = new Image("C:\Users\Sara\Downloads\ChessQL_v9\ChessQL\Spielanleitung\Bauer.PNG");
    String bAnleitung;
    String tAnleitung;
    String lAnleitung;
    String sAnleitung;
    String dAnleitung;
    String kAnleitung;
    HashMap<String, String> anleitungen= new HashMap<>();
    String POS="";
    String ip="jdbc:mysql://localhost/chessql";
    String psw="";
    String user="root";
    Label amZug = new Label("♔ WEIß ♔");
    Button zugBestätigen = new Button("Confirm");
    Button setCon = new Button("set Connection");
    Button restart = new Button("Restart");
    TextField ipEingabe = new TextField();
    TextField userEingabe =new TextField();
    TextField pswEingabe = new TextField();
    GridPane logfeld = new GridPane();
    Label log1 = new Label(" ");
    Label log2 = new Label(" ");
    Label log3 = new Label(" ");
    Label twLabel = new Label("");
    Label tsLabel = new Label("");
    Label z1 = new Label("mögliche Felder: ");
    Label z2 = new Label("Zielfeld: ");

    HashMap<String, String> field = feld.sit;
    char farbe = 'w';

    @Override
    public void start(Stage primaryStage) {

        //GridPane gridPane = new GridPane();
        createBtn();
        ausgewählt.setFont(new Font("Arial",20));
        möglich.setFont(Font.font("Arial",20));
        figuraus.setFont(Font.font("Arial",20));
        anleitung.setFont(Font.font("Arial",18));
        anleitung.setWrapText(true);
        amZug.setFont(Font.font("Arial",20));
        zugBestätigen.setFont(Font.font("Arial",20));
        setCon.setFont(Font.font("Arial",20));

        logfeld.add(log3,0,1);
        logfeld.add(log2,0,2);
        logfeld.add(log1,0,3);

        //gridPane.setGridLinesVisible(true);

        zugBestätigen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    //alle möglichen Felder in eine Liste einfügen
                    String möglichSt = möglich.getText();
                    String[] möglichArr;
                    möglichArr = möglichSt.split(",");
                    List<String> möglichList = new ArrayList<>();
                    oldfield.clear();
                    oldfield.putAll(field);
                    for(String item: möglichArr){
                        System.out.println(item);
                        möglichList.add(item);
                    }
                    Spiellogik sl = new Spiellogik(ip,user,psw,zugCounter);
                    if(zugBestätigen.getText()=="Restart"){
                        System.out.println("Restarting");
                        sl.reset();
                        field = sl.readField(sl.readField(field));
                        anleitungen = sl.readAnleitung(anleitungen);
                        setAnleitungen(anleitungen);
                        createBtn();
                        zugBestätigen.setText("Confirm");
                        eingabe.setText("");
                        möglich.setText("");
                        ausgewählt.setText("");
                        figuraus.setText("");
                        anleitung.setText("");
                        tsLabel.setText("");
                        twLabel.setText("");
                        restart.setText("Restart");
                        totS.clear();
                        totW.clear();
                        if (farbe == 's') {
                            farbe = 'w';
                            amZug.setText("♔ WEIß ♔");
                        } else if (farbe == 'w') {
                            farbe = 's';
                            amZug.setText("♚ SCHWARZ ♚");
                        }
                    }
                    else if(zugBestätigen.getText()=="Umwandlung"){
                        System.out.println("Bauer hat gegenüberliegende Seite erreicht!");
                        if(farbe=='s'){
                            if(möglich.getText().contains(eingabe.getText())) {
                                sl.umwandeln(zielfeld, eingabe.getText());
                                field = sl.readField(sl.readField(field));
                                createBtn();
                                zugBestätigen.setText("Confirm");
                                eingabe.setText("");
                                möglich.setText("");
                                ausgewählt.setText("");
                                figuraus.setText("");
                                anleitung.setText("");
                                farbe = 'w';
                                amZug.setText("♔ WEIß ♔");
                                z1.setText("mögliche Felder: ");
                            }
                            else{
                                System.out.println("Bitte eine gültige Figur zum Umwandeln eingeben");
                            }
                        }
                        else if(farbe=='w'){
                            if(möglich.getText().contains(eingabe.getText())){
                                sl.umwandeln(zielfeld,eingabe.getText());
                                field = sl.readField(sl.readField(field));
                                createBtn();
                                zugBestätigen.setText("Confirm");
                                eingabe.setText("");
                                möglich.setText("");
                                ausgewählt.setText("");
                                figuraus.setText("");
                                anleitung.setText("");
                                farbe='s';
                                amZug.setText("♚ SCHWARZ ♚");
                                z1.setText("mögliche Felder: ");
                            }
                            else{
                                System.out.println("Bitte eine gültige Figur zum Umwandeln eingeben");
                            }
                        }
                    }
                    else if(zugBestätigen.getText()=="Confirm") {
                        try {
                            if (möglichList.contains(eingabe.getText())) {
                                if (zugCounter == 0) {
                                    sl.reset();
                                    System.out.println("resetting Database");
                                    anleitungen = sl.readAnleitung(anleitungen);
                                    setAnleitungen(anleitungen);
                                }
                                zugCounter++;
                                sl.zugAusführen(POS, eingabe.getText(), field);
                                zielfeld = eingabe.getText();
                                log3.setText(log2.getText());
                                log2.setText(log1.getText());
                                log1.setText(zugCounter+".: "+farbe+" "+ Ausschreiben(field.get(POS)) +"von "+POS + " nach "+ eingabe.getText());

                                Label Titel_log = new Label("Log: ");
                                Titel_log.getStyleClass().add("title");
                                Titel_log.setPrefWidth(50);
                                Titel_log.setFont(new Font("Arial",20));
                                logfeld.add(Titel_log,0,0);
                                logfeld.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
                                field = sl.readField(sl.readField(field));
                                createBtn();
                                eingabe.setText("");
                                möglich.setText("");
                                ausgewählt.setText("");
                                figuraus.setText("");
                                anleitung.setText("");
                                if (farbe == 's') {
                                    List<String> oldFieldList=new ArrayList<>();
                                    oldFieldList.addAll(oldfield.values());
                                    for(String item:oldFieldList){
                                        if(field.containsValue(item)==false){
                                            System.out.println("Figur zu totW hinzugefügt");
                                            totW.add(item);

                                            String toteFigString ="";
                                            for (String s:totW){
                                                toteFigString += ","+s;
                                            }
                                            toteFigString=toteFigString.substring(1);
                                            twLabel.setText(toteFigString);
                                        }
                                    }
                                    if (field.containsValue("kw") == false) {
                                        eingabe.setText("Schwarz gewinnt!");
                                        zugBestätigen.setText("Restart");
                                    }
                                    else {
                                        if (field.get(zielfeld).startsWith("b") && zielfeld.startsWith("a")) {
                                                /*String toteFigString = "";
                                                int nonBauernC=0;
                                                for (String item : totS) {
                                                    if (item.startsWith("b") == false)
                                                        toteFigString += "," + item;
                                                        nonBauernC++;
                                                        System.out.println(toteFigString);
                                                }
                                                if(nonBauernC>0){
                                                toteFigString = toteFigString.substring(1);}
                                                möglich.setText(toteFigString);
                                                if (toteFigString.length() > 1) {*/
                                            String umwandlungsfig ="dsu, tsu, lsu, ssu";
                                            möglich.setText(umwandlungsfig);
                                            zugBestätigen.setText("Umwandlung");
                                            z1.setText("mögliche Figuren");
                                        }

                                                /*else{
                                                    farbe = 'w';
                                                    amZug.setText("WEISS");
                                                }
                                            }*/
                                        else{
                                            farbe='w';
                                            amZug.setText("♔ WEIß ♔");
                                        }
                                    }

                                } else if (farbe == 'w') {
                                    List<String> oldFieldList=new ArrayList<>();
                                    oldFieldList.addAll(oldfield.values());
                                    List<String> fieldList = new ArrayList<>();
                                    fieldList.addAll(field.values());
                                    for(int i=0;i<field.size();i++){
                                        System.out.println(oldFieldList.get(i)+" : "+fieldList.get(i));
                                    }
                                    for(String item:oldFieldList){
                                        if(field.containsValue(item)==false){
                                            System.out.println("Figur totS hinzugefügt");
                                            totS.add(item);

                                            String toteFigString ="";
                                            for (String s:totS){
                                                toteFigString += ","+s;
                                            }
                                            toteFigString=toteFigString.substring(1);
                                            tsLabel.setText(toteFigString);
                                        }
                                    }
                                    if (field.containsValue("ks") == false) {
                                        eingabe.setText("Weiß gewinnt!");
                                        zugBestätigen.setText("Restart");
                                    }
                                    else{
                                        if(field.get(zielfeld).startsWith("b") && zielfeld.startsWith("h")){
                                                /*String toteFigString ="";
                                                int nonBauernC=0;
                                                for (String item:totW){
                                                    if(item.startsWith("b")==false)
                                                        toteFigString += ","+item;
                                                        nonBauernC++;
                                                        System.out.println(toteFigString);
                                                }
                                                if(nonBauernC>0){
                                                toteFigString=toteFigString.substring(1);}
                                                möglich.setText(toteFigString);
                                                if (toteFigString.length() > 1) {*/
                                            String umwandlungsfig ="dwu, twu, lwu, swu";
                                            möglich.setText(umwandlungsfig);
                                            zugBestätigen.setText("Umwandlung");
                                            z1.setText("mögliche Figuren");
                                                /*}
                                                else{
                                                    farbe = 's';
                                                    amZug.setText("SCHWARZ");
                                                }*/
                                        }
                                        else {
                                            farbe = 's';
                                            amZug.setText("♚ SCHWARZ ♚");
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Bitte geben Sie ein gültiges Zielfeld ein!");
                            }

                        } catch (java.lang.IndexOutOfBoundsException e) {
                            System.out.println("Bitte Zielfeld eingeben!");
                            e.printStackTrace();
                        }
                    }
                }
                catch (java.lang.Exception e){
                    e.printStackTrace();
                }



            }
        });

        setCon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ip=ipEingabe.getText();
                user=userEingabe.getText();
                if(pswEingabe.getText().isEmpty()){
                    psw="";
                }
                else{
                    psw=pswEingabe.getText();
                }

            }
        });

        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(zugBestätigen.getText() == "Confirm"){
                    zugBestätigen.setText("Restart");
                    restart.setText("Cancel Restart");
                }
                else if(zugBestätigen.getText() == "Restart"){
                    zugBestätigen.setText("Confirm");
                    restart.setText("Restart");
                }
            }
        });

        for (int i = 0; i < size ; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(90));
            gridPane.getRowConstraints().add(new RowConstraints(90));
        }

        Label eins = new Label(" 1");
        Label zwei = new Label(" 2");
        Label drei = new Label(" 3");
        Label vier = new Label(" 4");
        Label fünf = new Label(" 5");
        Label sechs = new Label(" 6");
        Label sieben = new Label(" 7");
        Label acht = new Label(" 8");

        Label a = new Label("       a");
        Label b = new Label("       b");
        Label c = new Label("       c");
        Label d = new Label("       d");
        Label e = new Label("       e");
        Label f = new Label("       f");
        Label g = new Label("       g");
        Label h = new Label("       h");

        eins.setFont(Font.font("Arial",20));
        zwei.setFont(Font.font("Arial",20));
        drei.setFont(Font.font("Arial",20));
        vier.setFont(Font.font("Arial",20));
        fünf.setFont(Font.font("Arial",20));
        sechs.setFont(Font.font("Arial",20));
        sieben.setFont(Font.font("Arial",20));
        acht.setFont(Font.font("Arial",20));

        a.setFont(Font.font("Arial",20));
        b.setFont(Font.font("Arial",20));
        c.setFont(Font.font("Arial",20));
        d.setFont(Font.font("Arial",20));
        e.setFont(Font.font("Arial",20));
        f.setFont(Font.font("Arial",20));
        g.setFont(Font.font("Arial",20));
        h.setFont(Font.font("Arial",20));

        gridPane.add(eins, 9, 0);
        gridPane.add(zwei, 9, 1);
        gridPane.add(drei, 9, 2);
        gridPane.add(vier, 9, 3);
        gridPane.add(fünf, 9, 4);
        gridPane.add(sechs, 9, 5);
        gridPane.add(sieben, 9, 6);
        gridPane.add(acht, 9, 7);

        gridPane.add(a, 0, 9);
        gridPane.add(b, 1, 9);
        gridPane.add(c, 2, 9);
        gridPane.add(d, 3, 9);
        gridPane.add(e, 4, 9);
        gridPane.add(f, 5, 9);
        gridPane.add(g, 6, 9);
        gridPane.add(h, 7, 9);


        Label ip = new Label("Ip:");
        Label user = new Label("Username:");
        Label psw   = new Label("Passwort:");

        Button fullsreen = new Button("Vollbild");

        fullsreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fullsreen.getText() == "Vollbild")
                {
                    primaryStage.setFullScreen(true);
                    fullsreen.setText("Minimieren");
                }
                else if (fullsreen.getText() == "Minimieren")
                {
                    primaryStage.setFullScreen(false);
                    fullsreen.setText("Vollbild");
                }
            }
        });

        window.add(ip, 0,0);
        window.add(ipEingabe, 1,0, 3,1);
        window.add(user, 4,0);
        window.add(userEingabe, 5,0, 3,1);
        window.add(psw, 8,0);
        window.add(pswEingabe, 9,0,3,1);
        window.add(setCon, 0,1,12,1);
        window.add(fullsreen, 12, 0);

        ip.setFont(Font.font("Arial",20));
        user.setFont(Font.font("Arial",20));
        psw.setFont(Font.font("Arial",20));
        fullsreen.setFont(Font.font("Arial",20));
        Label figurfeld = new Label("Feld-Figur:");


        ausgewählt.setTextAlignment(TextAlignment.LEFT);
        möglich.setTextAlignment(TextAlignment.LEFT);
        möglich.setWrapText(true);


        figurfeld.setFont(Font.font("Arial",20));
        z1.setFont(Font.font("Arial",20));
        z2.setFont(Font.font("Arial",20));


        window.add(figurfeld, 8,3);
        window.add(z1, 8,4);
        window.add(z2, 8,5);
/*
        window.add(log3, 1,20);
        window.add(log2, 1,21);
        window.add(log1, 1,22);
*/

        Label anleitunglabel = new Label("Anleitung");
        anleitunglabel.setFont(Font.font("Arial",20));

        window.add(logfeld,1,20,1,3);

        log3.setFont(Font.font("Arial",20));
        log2.setFont(Font.font("Arial",20));
        log1.setFont(Font.font("Arial",20));
        //log1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));

        Label tw = new Label("Geschlagene Figuren w: ");
        Label ts = new Label("Geschlagene Figuren s: ");
        ausgewählt.setTextAlignment(TextAlignment.LEFT);
        möglich.setTextAlignment(TextAlignment.LEFT);

        ausgewählt.setTextAlignment(TextAlignment.LEFT);
        ausgewählt.setWrapText(true);
        möglich.setTextAlignment(TextAlignment.RIGHT);
        tw.setFont(Font.font("Arial",20));
        ts.setFont(Font.font("Arial",20));
        twLabel.setFont(Font.font("Arial",20));
        tsLabel.setFont(Font.font("Arial",20));
        twLabel.setFont(Font.font("Arial",20));
        tsLabel.setFont(Font.font("Arial",20));




        window.add(ausgewählt, 9,3);
        window.add(möglich,9,4);
        window.add(eingabe,9,5);
        window.add(zugBestätigen,9,6);
        window.add(amZug, 9,8);
        window.add(restart, 9,7);
        window.add(figuraus, 11,3);
        window.add(anleitunglabel, 11,4);
        anleitung.setFont(Font.font("Arial",15));
        window.add(anleitung, 11,5, 3,7);

        window.add(tw, 8,9);
        window.add(ts, 8,10);
        window.add(twLabel, 9,9);
        window.add(tsLabel, 9,10);

        restart.setFont(Font.font("Arial",20));
        // window.add(spielanleitung,11,4);

        //window.setGridLinesVisible(true);
        window.add(gridPane, 0, 2, 9,16);
        window.add(controllP, 11,2,3,8);

        for (int i = 0; i < windowsize; i++) {
            window.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            window.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }


        primaryStage.setTitle("ChessQL");
        primaryStage.setScene(new Scene(window, 1600, 900));
        primaryStage.show();
    }

    public String reihe(int col){
        String reihe;
        if(col == 0){
            reihe = "a";
            return reihe;
        }
        else if (col == 1){
            reihe = "b";
            return reihe;
        }
        else if (col == 2){
            reihe = "c";
            return reihe;
        }
        else if (col == 3){
            reihe = "d";
            return reihe;
        }
        else if (col == 4){
            reihe = "e";
            return reihe;
        }
        else if (col == 5){
            reihe = "f";
            return reihe;
        }
        else if (col == 6){
            reihe = "g";
            return reihe;
        }
        else if (col == 7){
            reihe = "h";
            return reihe;
        }
        else{
            return "ueber 7";
        }
    }

    public void Update(HashMap<String, String> hash, Button button){
        String Feld = button.getId();
        button.setId(Feld + trennzeichen + hash.get(Feld));

    }


    public String Split (int stelle, String Text){
        String[] parts = Text.split(trennzeichen);

        String stelle1 = parts[0];
        String stelle2 = parts[1];

        if(stelle == 1){
            return stelle1;
        }
        else if(stelle == 2){
            return stelle2;
        }
        else{
            return "error";
        }
    }

    public void SchachfigurenUpdate(Button button, String Figur){

        if(Figur.charAt(1) == 's') {
            if (Figur.charAt(0) == 'b') {
                button.setText("♟");
            } else if (Figur.charAt(0) == 't') {
                button.setText("♜");
            } else if (Figur.charAt(0) == 's') {
                button.setText("♞");
            } else if (Figur.charAt(0) == 'l') {
                button.setText("♝");
            } else if (Figur.charAt(0) == 'd') {
                button.setText("♛");
            } else if (Figur.charAt(0) == 'k') {
                button.setText("♚");
            } else {
                button.setText("");
            }
        }
        else if(Figur.charAt(1) == 'w') {
            if (Figur.charAt(0) == 'b') {
                button.setText("♙");
            } else if (Figur.charAt(0) == 't') {
                button.setText("♖");
            } else if (Figur.charAt(0) == 's') {
                button.setText("♘");
            } else if (Figur.charAt(0) == 'l') {
                button.setText("♗");
            } else if (Figur.charAt(0) == 'd') {
                button.setText("♕");
            } else if (Figur.charAt(0) == 'k') {
                button.setText("♔");
            } else {
                button.setText("");
            }
        }
        else{
            button.setText("");
        }
    }
    public String Ausschreiben(String figurbezeichnung){
        if(figurbezeichnung.charAt(0)== 'b')
        {
            return "Bauer ";
        }
        else if (figurbezeichnung.charAt(0)== 't'){
            return "Turm ";
        }
        else if(figurbezeichnung.charAt(0)== 'k'){
            return "König ";
        }
        else if(figurbezeichnung.charAt(0)== 'd'){
            return "Dame ";
        }
        else if(figurbezeichnung.charAt(0)== 'l'){
            return "Läufer ";
        }
        else if(figurbezeichnung.charAt(0)== 's'){
            return "Springer ";
        }
        return "";

    }

    public void createBtn(){

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col ++) {

                StackPane square = new StackPane();
                String spalte = reihe(col);
                String reiheNum = Integer.toString(row + 1);
                Button btn = new Button("Beispiel");
                btn.setId(spalte + reiheNum);
                btn.autosize();
                Update(field,btn);
                String buttonFigur = Split(2, btn.getId());
                String buttonFeld = Split(1, btn.getId());
                SchachfigurenUpdate(btn, buttonFigur);
                btn.setMinHeight(90);
                btn.setMinWidth(90);

                String color ;
                if ((row + col) % 2 == 0) {
                    color = "white";
                    btn.setStyle("-fx-background-color: #FFFFFF; -fx-font-size: 25px;");
                } else {
                    color = "grey";
                    btn.setStyle("-fx-background-color: #808080; -fx-font-size: 25px;");
                }

                square.setStyle("-fx-background-color: "+color+";");
                gridPane.add(square, col, row);
                gridPane.add(btn, col, row);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(btn.getId());
                        ausgabe = "";
                        try {
                            POS=buttonFeld;
                            /*
                            if(buttonFigur == "null"){
                                ausgewählt.setText("bitte wähle einer deiner Figuren aus");
                                figuraus.setText("");
                                anleitung.setText("");
                            }
                            else
                            */
                            if (buttonFigur.charAt(1) == farbe) {

                                if(buttonFigur.charAt(0) == 'k')
                                {
                                    figuraus.setText("König");
                                    anleitung.setText(kAnleitung);
                                    Koenigslogik Koenig = new Koenigslogik(buttonFeld,feld,Character.toString(farbe));
                                    try {
                                        ausgabe = Koenig.stringify();
                                    }
                                    catch(java.lang.IndexOutOfBoundsException e){
                                        System.out.println("Keine Felder erreichbar!");
                                        ausgabe = "";
                                    }
                                }
                                else if(buttonFigur.charAt(0) == 'd'){
                                    figuraus.setText("Dame");
                                    anleitung.setText(dAnleitung);
                                    Damenlogik Dame = new Damenlogik(buttonFeld,feld,Character.toString(farbe));
                                    try {
                                        ausgabe = Dame.stringify();
                                    }
                                    catch(java.lang.IndexOutOfBoundsException e){
                                        System.out.println("Keine Felder erreichbar!");
                                        ausgabe = "";
                                    }
                                }
                                else if(buttonFigur.charAt(0) == 'l'){
                                    figuraus.setText("Läufer");
                                    anleitung.setText(lAnleitung);
                                    Laeuferlogik Laeufer = new Laeuferlogik(buttonFeld,feld,Character.toString(farbe));
                                    try {
                                        ausgabe = Laeufer.stringify();
                                    }
                                    catch(java.lang.IndexOutOfBoundsException e){
                                        System.out.println("Keine Felder erreichbar!");
                                        ausgabe = "";
                                    }
                                }
                                else if(buttonFigur.charAt(0) == 's'){
                                    figuraus.setText("Springer");
                                    anleitung.setText(sAnleitung);
                                    Springerlogik Springer = new Springerlogik(buttonFeld,feld,Character.toString(farbe));
                                    try {
                                        ausgabe = Springer.stringify();
                                    }
                                    catch(java.lang.IndexOutOfBoundsException e){
                                        System.out.println("Keine Felder erreichbar!");
                                        ausgabe = "";
                                    }
                                }
                                else if(buttonFigur.charAt(0) == 't'){
                                    figuraus.setText("Turm");
                                    anleitung.setText(tAnleitung);
                                    Turmlogik Turm = new Turmlogik(buttonFeld,feld,Character.toString(farbe));
                                    try {
                                        ausgabe = Turm.stringify();
                                    }
                                    catch(java.lang.IndexOutOfBoundsException e){
                                        System.out.println("Keine Felder erreichbar!");
                                        ausgabe = "";
                                    }
                                }
                                else if(buttonFigur.charAt(0) == 'b'){
                                    figuraus.setText("Bauer");
                                    anleitung.setText(bAnleitung);
                                    if(buttonFigur.charAt(1)=='s'){
                                        SBauernlogik sBauer= new SBauernlogik(buttonFeld,feld);
                                        try {
                                            ausgabe = sBauer.stringify();
                                        }
                                        catch(java.lang.IndexOutOfBoundsException e){
                                            System.out.println("Keine Felder erreichbar!");
                                            ausgabe = "";
                                        }
                                    }
                                    else if(buttonFigur.charAt(1)=='w'){
                                        WBauernlogik wBauer = new WBauernlogik(buttonFeld,feld);
                                        try {
                                            ausgabe = wBauer.stringify();
                                        }
                                        catch(java.lang.IndexOutOfBoundsException e){
                                            System.out.println("Keine Felder erreichbar!");
                                            ausgabe = "";
                                        }
                                    }
                                }

                                System.out.println(btn.getId());
                                ausgewählt.setText(btn.getId());
                                möglich.setText(ausgabe);

                            }

                            else if (buttonFigur.charAt(1) != farbe) {
                                ausgewählt.setText("Bitte wähle eine deiner Figuren aus!");
                                möglich.setText("");
                                anleitung.setText("");
                            }

                        }catch (NullPointerException e){
                            ausgewählt.setText("bitte wähle eine deiner Figuren aus");
                            möglich.setText("");
                        }
                    }
                });
            }
        }
    }
    public void setAnleitungen (HashMap<String,String> anleitungen){
        bAnleitung = anleitungen.get("b");
        lAnleitung = anleitungen.get("l");
        sAnleitung = anleitungen.get("s");
        tAnleitung = anleitungen.get("t");
        dAnleitung = anleitungen.get("d");
        kAnleitung = anleitungen.get("k");

    }
}

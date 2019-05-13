package at.chessql.SceneBuilder;

import at.chessql.logik.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public  class  MainController extends Application {
    Spiellogik sl;
    boolean multiplayer = false;
    Integer oldturn;
    String auswahl = "hallo";
    Label label = new Label();
    Feld feld = new Feld();
    final int size = 8 ;
    TextField eingabe = new TextField();
    GridPane window = new GridPane();
    GridPane controllP = new GridPane();
    @FXML
    GridPane root=new GridPane();
    int zugCounter = 0;
    HashMap<String,String> oldfield = new HashMap<>();
    List<String> totS = new ArrayList<>();
    List<String> totW = new ArrayList<>();
    String zielfeld="";
    final int windowsize = 13;
    String trennzeichen = "-";
    String ausgabe = "";
    Label ausgewählt = new Label("");
    @FXML
    Label figuraus = new Label();
    @FXML
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
    //Label amZug = new Label("♔ WEIß ♔");
    Button zugBestätigen = new Button("Confirm");
    Button setCon = new Button("set Connection");

    @FXML
     Button createDb;

    @FXML
     Button fullscreen;

    @FXML
    Button restart;


    GridPane logfeld = new GridPane();

    @FXML
    Label log1,log2,log3;
    @FXML
    Label twLabel,tsLabel;

    Boolean spielstatus = true;
    String buttonfeldOLD = "";

    HashMap<String, String> field = feld.sit;
    char farbe = 'w';
    @FXML
    Text amZug ;

    @FXML
     TextField ip_textfield,username_textfield,psw_textfield ;

    @FXML
     Button login_button;

    @FXML
     ListView möglich_List;
    @FXML
     Label möglich;


    //Login Button überprüft ob passwort und username leer sind

    public void Restart(ActionEvent event) {
        System.out.println("Restarting");
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(zugBestätigen.getText() == "Confirm"){
            zugBestätigen.setText("Restart");
            restart.setText("Cancel Restart");
        }
        else if(zugBestätigen.getText() == "Restart"){
            zugBestätigen.setText("Confirm");
            restart.setText("Restart");
            System.out.println("Restarting");
        }
    }

// create shema Funktion
    public void createDatabase(ActionEvent event) {
        ip=ip_textfield.getText();
        user=username_textfield.getText();
        if(psw_textfield.getText().isEmpty()){
            psw="";
        }
        else{
            psw=psw_textfield.getText();
        }
        try {
            Spiellogik slc = new Spiellogik(ip, user, psw, 0);
            slc.reset();
        }
        catch (java.lang.Exception e){
            System.out.println("Fehler bei create database"+e);
        }
    }

    // Spiel auf Vollbild stellen
    public void fullscreen(MouseEvent event) {
        if(fullscreen.getText() == "Vollbild")
        {
            stage.setMaximized(true);
            fullscreen.setText("Minimieren");
        }
        else if (fullscreen.getText() == "Minimieren")
        {
            stage.setMaximized(false);
            fullscreen.setText("Vollbild");
        }
        System.out.println("fullscreen sollte schon gehen ");
    }
/*
    public void SpielFeldMouseClicked(ActionEvent event) {
        try {
            //alle möglichen Felder in eine Liste einfügen
            String möglichSt = möglich.getText();
            String[] möglichArr;
            möglichArr = möglichSt.split(",");
            List<String> möglichList = new ArrayList<>();
            oldfield.clear();
           // oldfield.putAll(field);
            for (String item : möglichArr) {
                System.out.println(item);
                möglichList.add(item);
            }
            Spiellogik sl = new Spiellogik(ip,user,psw,zugCounter);


        }
        catch (Exception e){ e.printStackTrace();}
    }

/*/
//int col = root.getColumnCount();
 //   int row = root.getRowCount();





    public void createBtn(){
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++) {

                StackPane square = new StackPane();
                String spalte = reihe(col);
                String reiheNum = Integer.toString(row + 1);
                Button btn = new Button("Beispiel");
                btn.setId(spalte + reiheNum);
                btn.autosize();
                Update(field, btn);
                String buttonFigur = Split(2, btn.getId());
                String buttonFeld = Split(1, btn.getId());
                SchachfigurenUpdate(btn, buttonFigur);
                btn.setMinHeight(90);
                btn.setMinWidth(90);

                String color;
                if ((row + col) % 2 == 0) {
                    color = "white";
                    btn.setStyle("-fx-background-color: #FFFFFF; -fx-font-size: 25px;");
                } else {
                    color = "grey";
                    btn.setStyle("-fx-background-color: #808080; -fx-font-size: 25px;");
                }

                square.setStyle("-fx-background-color: " + color + ";");

                root.add(square, col, row);

                root.add(btn, col, row);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println(btn.getId());
                        ausgabe = "";
                        try {
                            if (spielstatus == true) {
                                POS = buttonFeld;
                                buttonfeldOLD = buttonFeld;
                            } else {
                                POS = buttonfeldOLD;
                            }

                            String btnInAuswahl = "";

                            if (spielstatus == true) {


                                if (buttonFigur.charAt(1) == farbe) {

                                    if (buttonFigur.charAt(0) == 'k') {
                                        figuraus.setText("König");
                                        anleitung.setText(kAnleitung);
                                        Koenigslogik Koenig = new Koenigslogik(buttonFeld, feld, Character.toString(farbe));
                                        try {
                                            ausgabe = Koenig.stringify();
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Keine Felder erreichbar!");
                                            ausgabe = "";
                                        }
                                    } else if (buttonFigur.charAt(0) == 'd') {
                                        figuraus.setText("Dame");
                                        anleitung.setText(dAnleitung);
                                        Damenlogik Dame = new Damenlogik(buttonFeld, feld, Character.toString(farbe));
                                        try {
                                            ausgabe = Dame.stringify();
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Keine Felder erreichbar!");
                                            ausgabe = "";
                                        }
                                    } else if (buttonFigur.charAt(0) == 'l') {
                                        figuraus.setText("Läufer");
                                        anleitung.setText(lAnleitung);
                                        Laeuferlogik Laeufer = new Laeuferlogik(buttonFeld, feld, Character.toString(farbe));
                                        try {
                                            ausgabe = Laeufer.stringify();
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Keine Felder erreichbar!");
                                            ausgabe = "";
                                        }
                                    } else if (buttonFigur.charAt(0) == 's') {
                                        figuraus.setText("Springer");
                                        anleitung.setText(sAnleitung);
                                        Springerlogik Springer = new Springerlogik(buttonFeld, feld, Character.toString(farbe));
                                        try {
                                            ausgabe = Springer.stringify();
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Keine Felder erreichbar!");
                                            ausgabe = "";
                                        }
                                    } else if (buttonFigur.charAt(0) == 't') {
                                        figuraus.setText("Turm");
                                        anleitung.setText(tAnleitung);
                                        Turmlogik Turm = new Turmlogik(buttonFeld, feld, Character.toString(farbe));
                                        try {
                                            ausgabe = Turm.stringify();
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Keine Felder erreichbar!");
                                            ausgabe = "";
                                        }
                                    } else if (buttonFigur.charAt(0) == 'b') {
                                        figuraus.setText("Bauer");
                                        anleitung.setText(bAnleitung);
                                        if (buttonFigur.charAt(1) == 's') {
                                            SBauernlogik sBauer = new SBauernlogik(buttonFeld, feld);
                                            try {
                                                ausgabe = sBauer.stringify();
                                            } catch (IndexOutOfBoundsException e) {
                                                System.out.println("Keine Felder erreichbar!");
                                                ausgabe = "";
                                            }
                                        } else if (buttonFigur.charAt(1) == 'w') {
                                            WBauernlogik wBauer = new WBauernlogik(buttonFeld, feld);
                                            try {
                                                ausgabe = wBauer.stringify();
                                            } catch (IndexOutOfBoundsException e) {
                                                System.out.println("Keine Felder erreichbar!");
                                                ausgabe = "";
                                            }
                                        }
                                    }

                                    System.out.println(btn.getId());

                                    btnInAuswahl = btn.getId();

                                    ausgewählt.setText(btnInAuswahl);

                                    möglich.setText(ausgabe);

                                } else if (buttonFigur.charAt(1) != farbe) {
                                    ausgewählt.setText("Bitte wähle eine deiner Figuren aus!");
                                    möglich.setText("");
                                    anleitung.setText("");
                                }

                                spielstatus = false;
                                System.out.println("von tur auf false");
                            } else {
                                eingabe.setText(btn.getId().substring(0, 2));

                                System.out.println("false");

                                System.out.println(btn.getId());

                                spielstatus = true;

                                ausgewählt.setText(btnInAuswahl);

                                zugBestätigen.fire();

                            }

                        } catch (NullPointerException e) {
                            ausgewählt.setText("bitte wähle eine deiner Figuren aus");
                            möglich.setText("");
                        }
                    }
                });
            }
    }


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

                                    String umwandlungsfig ="dsu, tsu, lsu, ssu";
                                    möglich.setText(umwandlungsfig);
                                    zugBestätigen.setText("Umwandlung");
                                }

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
                                    String umwandlungsfig ="dwu, twu, lwu, swu";
                                    möglich.setText(umwandlungsfig);
                                    zugBestätigen.setText("Umwandlung");

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
        catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void LoginButton(ActionEvent event) {
        multiplayer = true;
        ip = ip_textfield.getText();
        user = username_textfield.getText();
        if (psw_textfield.getText().isEmpty()) {
            psw = "";
        } else {
            psw = psw_textfield.getText();
        }


    }

    //Saras Versuch
    public void play(ActionEvent event){
        try {
            Spiellogik sl1 = new Spiellogik(ip, user, psw, zugCounter);
            int cp = sl1.checkPlayers();
            if(cp==1){
                System.out.println("Sie sind weiss!");
                farbe = 'w';
                multiplayer = true;
            }
            else if(cp==2){
                System.out.println("Sie sind schwarz!");
                farbe = 's';
                multiplayer = true;
            }
            else{
                System.out.println("Bei der Farbwahl ist etwas schief gelaufen (returned 0)");
            }
            TimerTask tt1 = sl1.createTimertask(1);
            Timer t = new Timer();
            t.schedule(tt1,0,1000);
            while(!sl1.getCallback().equals(1)){
                System.out.println("Auf 2ten Spieler Warten");
            }
            tt1.cancel();
            if(farbe=='w'){
                sl1.setWhite();
            }
            TimerTask tt2 = sl1.createTimertask(2);
            t.schedule(tt2,3000,3000);
            TimerTask tt3 = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(sl1.getCallback());
                    if (sl1.getCallback().equals(2)){
                        if(!sl1.getCallback().equals(oldturn)){
                            try {
                                field = sl1.readField(sl1.readField(field));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        amZug.setText("♚ SCHWARZ ♚");
                                        createBtn();
                                    }
                                });
                            }
                            catch(java.lang.Exception e){
                                System.out.println("Fehler bei oldturn weiss"+e);
                            }
                        }
                        oldturn=2;
                    }
                    else if(sl1.getCallback().equals(3)){
                        if(!sl1.getCallback().equals(oldturn)){
                            try {
                                field = sl1.readField(sl1.readField(field));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        amZug.setText("♔ WEIß ♔");
                                        createBtn();
                                    }
                                });
                            }
                            catch(java.lang.Exception e){
                                System.out.println("Fehler bei oldturn schwarz"+e);
                            }
                        }
                        oldturn=3;
                    }
                }
            };
            t.schedule(tt3,3000,3000);
        }
        catch (java.lang.Exception e){
            System.out.println("Fehler beim Initialisieren der Spiellogik " +e);
        }
    }

//Schachbrettreihe setzen im Column
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

    //Feld Button einsetzen
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

// Schachfiguren aufsetzen
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
    public void setAnleitungen (HashMap<String,String> anleitungen){
        bAnleitung = anleitungen.get("b");
        lAnleitung = anleitungen.get("l");
        sAnleitung = anleitungen.get("s");
        tAnleitung = anleitungen.get("t");
        dAnleitung = anleitungen.get("d");
        kAnleitung = anleitungen.get("k");

    }



    static Stage stage;

    public void start(Stage stage) {
        try{

            //URL url = new File("fxml/sample2.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("sample2.fxml"));
            Scene scene = new Scene(parent,800,600);

            stage.setTitle("Chessql");
            stage.setScene(scene);
            stage.show();
            createBtn();
        }catch (Exception e){
            e.printStackTrace();
        }


        anleitung.setWrapText(true);


    }
    public static void main(String args[]){
        launch(args);
    }
}

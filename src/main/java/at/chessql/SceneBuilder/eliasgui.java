package at.chessql.SceneBuilder;

import at.chessql.logik.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;


public class eliasgui extends Application {
    boolean multiplayer = false;
    Integer oldturn;
    String auswahl = "hallo";
    Label label = new Label();
    Feld feld = new Feld();
    final int size = 8 ;
    TextField eingabe = new TextField();
    GridPane window = new GridPane();
    GridPane logingrid = new GridPane();
    GridPane controllP = new GridPane();
    GridPane root = new GridPane();
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
    Button dame_1 = new Button();
    Button laufer_1 = new Button();
    Button springer_1 = new Button();
    Button turm_1= new Button();
    Button zugBestätigen = new Button("Confirm");
    Button setCon = new Button("set Connection");
    Button createDb= new Button("create scheme");
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
    Boolean spielstatus = true;
    String buttonfeldOLD = "";

    HashMap<String, String> field = feld.sit;
    char farbe = 'w';

    @Override
    public void start(Stage primaryStage) {

        //GridPane root = new GridPane();
        createBtn();
        ausgewählt.setFont(new Font("Arial",20));
        möglich.setFont(Font.font("Arial",20));
        figuraus.setFont(Font.font("Arial",20));
        anleitung.setFont(Font.font("Arial",18));
        anleitung.setWrapText(true);
        amZug.setFont(Font.font("Arial",20));
        zugBestätigen.setFont(Font.font("Arial",20));
        setCon.setFont(Font.font("Arial",20));
        createDb.setFont(Font.font("Arial",20));

        logfeld.add(log3,0,1);
        logfeld.add(log2,0,2);
        logfeld.add(log1,0,3);

        //root.setGridLinesVisible(true);

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

                    if(zugBestätigen.getText()=="Umwandlung"){
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
                                window.getChildren().remove(dame_1);
                                window.getChildren().remove(turm_1);
                                window.getChildren().remove(springer_1);
                                window.getChildren().remove(laufer_1);

                                if(multiplayer){
                                    sl.setWhite();

                                }
                                else{
                                    farbe = 'w';
                                    amZug.setText("♔ WEIß ♔");

                                }
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
                                window.getChildren().remove(dame_1);
                                window.getChildren().remove(turm_1);
                                window.getChildren().remove(springer_1);
                                window.getChildren().remove(laufer_1);

                                if(multiplayer){
                                    sl.setBlack();

                                }
                                else{
                                    farbe = 's';
                                    amZug.setText("♚ SCHWARZ ♚");

                                }
                                z1.setText("mögliche Felder: ");
                            }
                            else{
                                System.out.println("Bitte eine gültige Figur zum Umwandeln eingeben");
                            }
                        }
                    }

                    else if(zugBestätigen.getText()=="Confirm") {
                        if(multiplayer){
                            if(sl.checkTurn(farbe)){
                                confirm(möglichList,sl);
                                //anleitung.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
                            }
                            else{
                                System.out.println("Bitte warten Sie bis Sie an der Reihe sind!");
                            }
                        }
                        else
                        {
                            confirm(möglichList,sl);
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
                multiplayer = true;
                ip=ipEingabe.getText();
                user=userEingabe.getText();
                if(pswEingabe.getText().isEmpty()){
                    psw="";
                }
                else{
                    psw=pswEingabe.getText();
                }
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
                    t.schedule(tt2,0,1000);
                    TimerTask tt3 = new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println(sl1.getCallback());
                            if (sl1.getCallback().equals(2)){
                                if(!sl1.getCallback().equals(oldturn)){
                                    try {
                                        field = sl1.readField(sl1.readField(field));
                                        System.out.println("in try schwarz");
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
                                        System.out.println("in try weiss");
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
                    t.schedule(tt3,0,1000);
                }
                catch (java.lang.Exception e){
                    System.out.println("Fehler beim Initialisieren der Spiellogik " +e);
                }

            }
        });

        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Spiellogik sl = new Spiellogik(ip,user,psw,zugCounter);
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
                    if(!multiplayer) {
                        if (farbe == 's') {
                            farbe = 'w';
                            amZug.setText("♔ WEIß ♔");
                        } else if (farbe == 'w') {
                            farbe = 's';
                            amZug.setText("♚ SCHWARZ ♚");
                        }
                    }
                    else{
                        sl.setWhite();
                    }
                }
                catch (java.lang.Exception e){
                    System.out.println("Fehler beim restart "+e);
                }
            }
        });

        createDb.setOnAction(new EventHandler<ActionEvent>(){
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
                try {
                    Spiellogik slc = new Spiellogik(ip, user, psw, 0);
                    slc.reset();
                }
                catch (java.lang.Exception e){
                    System.out.println("Fehler bei create database"+e);
                }
            }
        });

        for (int i = 0; i < size ; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(90));
            root.getRowConstraints().add(new RowConstraints(90));
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

        root.add(eins, 9, 0);
        root.add(zwei, 9, 1);
        root.add(drei, 9, 2);
        root.add(vier, 9, 3);
        root.add(fünf, 9, 4);
        root.add(sechs, 9, 5);
        root.add(sieben, 9, 6);
        root.add(acht, 9, 7);

        root.add(a, 0, 9);
        root.add(b, 1, 9);
        root.add(c, 2, 9);
        root.add(d, 3, 9);
        root.add(e, 4, 9);
        root.add(f, 5, 9);
        root.add(g, 6, 9);
        root.add(h, 7, 9);


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
        /*
        window.add(ip, 0,0);
        window.add(ipEingabe, 1,0, 3,1);
        window.add(user, 4,0);
        window.add(userEingabe, 5,0, 3,1);
        window.add(psw, 8,0);
        window.add(pswEingabe, 9,0,3,1);
        window.add(setCon, 0,1,12,1);
        window.add(createDb,12,1,12,1);
        window.add(fullsreen, 12, 0);

        ip.setFont(Font.font("Arial",20));
        user.setFont(Font.font("Arial",20));
        psw.setFont(Font.font("Arial",20));
        fullsreen.setFont(Font.font("Arial",20));

/*/

        logingrid.add(ip, 0,1);
        logingrid.add(ipEingabe, 1,1, 1,2);
        logingrid.add(user, 0,2);
        logingrid.add(userEingabe, 1,2, 1,2);
        logingrid.add(psw, 0,3);
        logingrid.add(pswEingabe, 1,3,1,2);
        logingrid.add(setCon, 1,0,2,1);
        logingrid.add(createDb,1,4,2,1);

        logingrid.setMargin(ip, new Insets(0, 10, 0, 500));
        logingrid.setMargin(ipEingabe, new Insets(0, 10, 175, 0));
        logingrid.setMargin(user, new Insets(0, 10, 0, 420));
        logingrid.setMargin(userEingabe, new Insets(0, 10, 175, 0));
        logingrid.setMargin(psw, new Insets(0, 10, 0, 420));
        logingrid.setMargin(pswEingabe, new Insets(0, 10, 275, 0));
        //logingrid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        //logingrid.setVgap(10); //vertical gap in pixels
        //logingrid.setPadding(new Insets(20, 10, 10, 50)); //margins around the whole grid

        //(top/right/bottom/left)

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(530);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(530);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(530);
        logingrid.getColumnConstraints().addAll(col1,col2,col3);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(80);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(180);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(180);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(180);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(280);
        logingrid.getRowConstraints().addAll(row,row1,row2,row3,row4);



        ip.setFont(Font.font("Arial",20));
        user.setFont(Font.font("Arial",20));
        psw.setFont(Font.font("Arial",20));

        Label figurfeld = new Label("Feld-Figur:");

        ausgewählt.setTextAlignment(TextAlignment.LEFT);
        möglich.setTextAlignment(TextAlignment.LEFT);
        möglich.setWrapText(true);


        figurfeld.setFont(Font.font("Arial",20));
        z1.setFont(Font.font("Arial",20));
        z2.setFont(Font.font("Arial",20));
        ipEingabe.setText("jdbc:mysql://192.189.51.135:3306/alfons_chessql");
        userEingabe.setText("db-admin2");
        pswEingabe.setText("TBs2017");

/*
        window.add(figurfeld, 8,3);
        window.add(z1, 8,4);
        window.add(z2, 8,5);

        window.add(log3, 1,20);
        window.add(log2, 1,21);
        window.add(log1, 1,22);
*/
        //window.setGridLinesVisible(true);

        ColumnConstraints coli = new ColumnConstraints();
        coli.setPercentWidth(160);
        ColumnConstraints coli2 = new ColumnConstraints();
        coli2.setPercentWidth(160);
        ColumnConstraints coli3 = new ColumnConstraints();
        coli3.setPercentWidth(160);
        ColumnConstraints coli4 = new ColumnConstraints();
        coli4.setPercentWidth(160);
        ColumnConstraints coli5 = new ColumnConstraints();
        coli5.setPercentWidth(160);
        ColumnConstraints coli6 = new ColumnConstraints();
        coli6.setPercentWidth(160);
        ColumnConstraints coli7 = new ColumnConstraints();
        coli7.setPercentWidth(160);
        ColumnConstraints coli8 = new ColumnConstraints();
        coli8.setPercentWidth(160);
        ColumnConstraints coli9 = new ColumnConstraints();
        coli9.setPercentWidth(160);
        ColumnConstraints coli10 = new ColumnConstraints();
        coli10.setPercentWidth(160);
        window.getColumnConstraints().addAll(col1,col2,col3,coli4,coli5,coli6,coli7,coli8,coli9,coli10);


        RowConstraints rowi = new RowConstraints();
        rowi.setMinHeight(110);
        RowConstraints rowi1 = new RowConstraints();
        rowi1.setMinHeight(110);
        RowConstraints rowi2 = new RowConstraints();
        rowi2.setMinHeight(110);
        RowConstraints rowi3 = new RowConstraints();
        rowi3.setMinHeight(110);
        RowConstraints rowi4 = new RowConstraints();
        rowi4.setMinHeight(110);
        RowConstraints rowi5 = new RowConstraints();
        rowi5.setMinHeight(110);
        RowConstraints rowi6 = new RowConstraints();
        rowi6.setMinHeight(110);
        RowConstraints rowi7 = new RowConstraints();
        rowi7.setMinHeight(110);

        window.getRowConstraints().addAll(rowi,rowi1,rowi2,rowi3,rowi4,rowi5,rowi6,rowi7);

        Label anleitunglabel = new Label("Anleitung");
        anleitunglabel.setStyle("-fx-font-weight: bold");

        anleitunglabel.setFont(Font.font("Arial",20));

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




        dame_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(farbe=='s'){
                    eingabe.setText("dsu");
                    zugBestätigen.fire();
                }
                else{
                    eingabe.setText("dwu");
                    zugBestätigen.fire();
                }

                }
            });
        turm_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(farbe=='s'){
                    eingabe.setText("tsu");
                    zugBestätigen.fire();
                }
                else{
                    eingabe.setText("twu");
                    zugBestätigen.fire();
                }

            }
        });
        springer_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(farbe=='s'){
                    eingabe.setText("ssu");
                    zugBestätigen.fire();
                }
                else{
                    eingabe.setText("swu");
                    zugBestätigen.fire();
                }

            }
        });
        laufer_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(farbe=='s'){
                    eingabe.setText("lsu");
                    zugBestätigen.fire();
                }
                else{
                    eingabe.setText("lwu");
                    zugBestätigen.fire();
                }

            }
        });

        window.add(amZug, 5,4,2,1);
        window.add(restart, 5,3);

        //window.add(zugBestätigen,6,3);
        //window.add(eingabe,4,3);

        window.add(anleitunglabel, 3,0);
        anleitung.setFont(Font.font("Arial",18));
        window.add(anleitung, 3,1, 6,2);

        window.add(logfeld,5,7,3,1);
        window.add(fullsreen, 8, 0,2,1);
        fullsreen.setFont(Font.font("Arial",20));

        window.add(tw, 3,5,3,1);
        window.add(ts, 3,6,3,1);
        window.add(twLabel, 6,5);
        window.add(tsLabel, 6,6);

        restart.setFont(Font.font("Arial",20));
        // window.add(spielanleitung,11,4);

        //window.setGridLinesVisible(true);
        window.add(root, 0, 1, 5,6);
        window.add(controllP, 11,2,3,8);

        for (int i = 0; i < windowsize; i++) {
            window.getColumnConstraints().add(new ColumnConstraints(90, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            window.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }



        // create a tabpane
        TabPane tabpane = new TabPane();
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab logintab = new Tab();
        logintab.setText("Login");
        logintab.setStyle("-fx-font-weight: bold");
        Tab gametab = new Tab();
        gametab.setText("Game");
        gametab.setStyle("-fx-font-weight: bold");

        tabpane.getTabs().add(gametab);
        tabpane.getTabs().add(logintab);



        gametab.setContent(window);
        logintab.setContent(logingrid);

        //tabpane.setTabMinHeight(900);
        //tabpane.setTabMinWidth(1600);
        window.setMinHeight(900);
        window.setMinWidth(1600);

        primaryStage.setTitle("ChessQL");
        Scene scene = new Scene(tabpane,1600,900);
        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("tab-pane-big-tabs.css").toExternalForm());
        primaryStage.setScene(scene);

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
                root.add(square, col, row);
                root.add(btn, col, row);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println(btn.getId());
                        ausgabe = "";
                        try {


                            if(spielstatus == true)
                            {
                                POS=buttonFeld;
                                buttonfeldOLD = buttonFeld;
                            }
                            else{
                                POS=buttonfeldOLD;
                            }
                            /*
                            if(buttonFigur == "null"){
                                ausgewählt.setText("bitte wähle einer deiner Figuren aus");
                                figuraus.setText("");
                                anleitung.setText("");
                            }
                            else
                            */


                            String btnInAuswahl = "";

                            if(spielstatus == true)
                            {


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

                                    btnInAuswahl = btn.getId();

                                    ausgewählt.setText(btnInAuswahl);

                                    möglich.setText(ausgabe);

                                }

                                else if (buttonFigur.charAt(1) != farbe) {
                                    ausgewählt.setText("Bitte wähle eine deiner Figuren aus!");
                                    möglich.setText("");
                                    anleitung.setText("");
                                }

                                spielstatus = false;
                                System.out.println("von tur auf false");
                            }

                            else
                            {
                                eingabe.setText(btn.getId().substring(0,2));

                                System.out.println("false");

                                System.out.println(btn.getId());

                                spielstatus = true;

                                ausgewählt.setText(btnInAuswahl);

                                zugBestätigen.fire();

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

    public void confirm(List<String>möglichList,Spiellogik sl){
        try {
            if (möglichList.contains(eingabe.getText())) {
                if(!multiplayer){
                    if (zugCounter == 0) {
                        sl.reset();
                        System.out.println("resetting Database");
                        anleitungen = sl.readAnleitung(anleitungen);
                        setAnleitungen(anleitungen);
                    }
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
                            window.add(dame_1,6,3);
                            window.add(turm_1,7,3);
                            window.add(laufer_1,8,3);
                            window.add(springer_1,9,3);
                            dame_1.setText("♛");
                            turm_1.setText("♜");
                            laufer_1.setText("♝");
                            springer_1.setText("♞");

                        }

                        else {
                            if(!multiplayer) {
                                farbe = 'w';
                                amZug.setText("♔ WEIß ♔");
                            }
                            else{
                                sl.setWhite();
                            }
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
                            window.add(dame_1,6,3);
                            window.add(turm_1,7,3);
                            window.add(laufer_1,8,3);
                            window.add(springer_1,9,3);
                            dame_1.setText("♕");
                            turm_1.setText("♖");
                            laufer_1.setText("♗");
                            springer_1.setText("♘");
                        }
                        else {
                            if(!multiplayer) {
                                farbe = 's';
                                amZug.setText("♚ SCHWARZ ♚");
                            }
                            else{
                                sl.setBlack();
                            }
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
        catch (java.lang.Exception e){
            e.printStackTrace();
        }

    }
}

package at.chessql.logik;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

public class Spiellogik {
    private Connection connection;
    int zugCounter;
    Integer callback1 = 0;
    public Spiellogik(String ip, String user, String psw, int z)throws Exception{
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(ip, user, psw);
            System.out.println("Verbindung erfolgreich hergestellt");

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("database driver not found", e);
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new Exception("couldn't connect to database",e);
        }
        zugCounter = z;

    }


    public HashMap<String, String> readField(HashMap<String, String> sit)throws Exception{

        String stmt = "Select * from f_field";
        PreparedStatement pstmt = connection.prepareStatement(stmt);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            //In diesem while soll später der text auf die buttons geschrieben werden bzw eine liste/hashmap zurückliefern
            //die von einer Funktion im GUI verarbeitet werden kann sodass die button neu beschriftet/neu erstellt werden.
            /*System.out.println(rs.getInt("f_id"));
            System.out.println(rs.getString("f_colA"));
            System.out.println(rs.getString("f_colB"));
            System.out.println(rs.getString("f_colC"));
            System.out.println(rs.getString("f_colD"));
            System.out.println(rs.getString("f_colE"));
            System.out.println(rs.getString("f_colF"));
            System.out.println(rs.getString("f_colG"));
            System.out.println(rs.getString("f_colH"));*/

            sit.put("a"+rs.getInt("f_id"),rs.getString("f_colA"));
            sit.put("b"+rs.getInt("f_id"),rs.getString("f_colB"));
            sit.put("c"+rs.getInt("f_id"),rs.getString("f_colC"));
            sit.put("d"+rs.getInt("f_id"),rs.getString("f_colD"));
            sit.put("e"+rs.getInt("f_id"),rs.getString("f_colE"));
            sit.put("f"+rs.getInt("f_id"),rs.getString("f_colF"));
            sit.put("g"+rs.getInt("f_id"),rs.getString("f_colG"));
            sit.put("h"+rs.getInt("f_id"),rs.getString("f_colH"));
        }
        return sit;
    }

    public void zugAusführen(String pos, String tar, HashMap<String,String>sit) throws Exception{
        String col = pos.substring(0,1);
        Integer row = Integer.parseInt(pos.substring(1));
        String tarcol = tar.substring(0,1);
        Integer tarrow = Integer.parseInt(tar.substring(1));
        String stmt = "update f_field set f_col"+col+"=null where f_id = "+row;
        System.out.println(stmt);
        PreparedStatement pstmt = connection.prepareStatement(stmt);
        pstmt.executeUpdate();
        stmt = "update f_field set f_col"+tarcol+"='"+sit.get(pos)+"' where f_id = "+tarrow;
        System.out.println(stmt);
        pstmt = connection.prepareStatement(stmt);
        pstmt.executeUpdate();
    }

    public void reset () throws Exception{
        /*String dropDb ="drop schema if exists chessql";
        PreparedStatement pstmt = connection.prepareStatement(dropDb);
        pstmt.executeUpdate();
        String createDb="create schema chessql";
        pstmt =connection.prepareStatement(createDb);
        pstmt.executeUpdate();
        String useDb="use chessql";
        PreparedStatement pstmt =connection.prepareStatement(useDb);
        pstmt.executeUpdate();*/
        String dropTablef = "drop table if exists f_field";
        PreparedStatement pstmt = connection.prepareStatement(dropTablef);
        pstmt.executeUpdate();
        String createTablef= "create table f_field (f_id int(8) primary key, f_colA varchar(128), f_colB varchar(128), f_colC varchar(128), f_colD varchar(128), f_colE varchar(128), f_colF varchar(128), f_colG varchar(128), f_colH varchar(128))";
        pstmt = connection.prepareStatement(createTablef);
        pstmt.executeUpdate();
        String insertf = "insert into f_field (f_id, f_colA, f_colB, f_colG, f_colH) values (1,'tw1','bw1','bs1','ts1'), (2,'sw1','bw2','bs2','ss1'), (3,'lw1','bw3','bs3','ls1'), (4,'dw','bw4','bs4','ds'), (5,'kw','bw5','bs5','ks'), (6,'lw2','bw6','bs6','ls2'), (7,'sw2','bw7','bs7','ss2'), (8,'tw2','bw8','bs8','ts2')";
        pstmt = connection.prepareStatement(insertf);
        pstmt.executeUpdate();
        String createTablea = "create table if not exists a_anleitung(a_id char(1) primary key,a_beschreibung varchar(10000))";
        pstmt = connection.prepareStatement(createTablea);
        pstmt.executeUpdate();
        String dropTablep = "drop table if exists p_players";
        pstmt = connection.prepareStatement(dropTablep);
        pstmt.executeUpdate();
        String createTablep= "create table p_players(p_id int(8) primary key,t_weiss int(1),t_schwarz int(1))";
        pstmt = connection.prepareStatement(createTablep);
        pstmt.executeUpdate();
        String insertp ="insert into p_players(p_id,t_weiss,t_schwarz) values(1,0,0), (2,0,0)";
        pstmt = connection.prepareStatement(insertp);
        pstmt.executeUpdate();
        if(zugCounter==0){
            List<String> existA = new ArrayList<>();
            String checka = "select * from a_anleitung";
            pstmt = connection.prepareStatement(checka);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getString("a_beschreibung")!=null){
                    existA.add(rs.getString("a_id"));
                }
            }
            System.out.println("inserting default manual");
            if(existA.contains("b")==false){
                String insertb = "insert into a_anleitung (a_id,a_beschreibung) values ('b','Die Bauern stellen in Zeiten des Krieges mit ihrer schieren Anzahl den größten Teil der königlichen Armee dar. Die meisten Bauern ziehen jedoch nur in den Krieg da sie von ihren Lehensherren gezwungen werden und sind weder kampferprobt noch verfügen sie über Waffen abgesehen von Prügeln und Werkzeugen. Diejenigen die in der Heimat noch von Ruhm und Heldentum träumen müssen bald erkennen, dass ein einfaches Wams im Krieg jedoch keine Chance auf überleben bietet. Bauern können sich in ihrem ersten Zug zwei Felder und später ein Feld in Richtung Feind bewegen. Auch Schlagen können Bauern nur auf Felder die schräg vor ihnen liegen doch wenn sie es schaffen lebendig die gegnerische Seite zu erreichen steigen sie sofort im Rang.')";
                pstmt = connection.prepareStatement(insertb);
                pstmt.executeUpdate();}
            if(existA.contains("t")==false){
                String insertt = "insert into a_anleitung (a_id,a_beschreibung) values('t','Der Turm ragt wie ein riesiger Monolith hinter den Bauern empor und wirkt wie eine ausschließlich der Verteidigung gewidmete Kriegsmaschine. Doch der Schein trügt, du kannst deinen Turm auf horizontalem und vertikalem Wege über das Feld bewegen und musst nur Halt machen, wenn du auf Gegner stößt oder dir Verbündete den Weg versperren. So kann der Turm sowohl offensive Züge durchführen als auch eine solide Verteidigung darstellen, sollte es von Nöten sein.')";
                pstmt = connection.prepareStatement(insertt);
                pstmt.executeUpdate();}
            if(existA.contains("l")==false){
                String insertl = "insert into a_anleitung (a_id,a_beschreibung) values('l','Der Läufer ist ein Nachkömmling des Königs und wäre ein möglicher Thronfolger. Er verabscheut jedoch die Politik seines Vaters die ausschließlich darauf ausgelegt ist Autorität durch Gewalt zu erlangen. Er selbst will dieser Philosophie nicht Folge leisten und bevorzugt es Konflikten aus dem Weg zu gehen, was ihm unter dem Volk den Spitznamen -der Läufer- eingebracht hat. Sollte er doch einmal gezwungen sein, Gewalt einzusetzen tut er dies mit großer Tücke und durch Finten. Der Königssohn bewegt sich daher nur diagonal über das Feld.')";
                pstmt = connection.prepareStatement(insertl);
                pstmt.executeUpdate();}
            if(existA.contains("s")==false){
                String inserts = "insert into a_anleitung (a_id,a_beschreibung) values('s','Der Springer ist ein ausgewählter Reiter aus der berittenen Armee des Königs, der im Kampf eins mit seinem Ross wird und seine Feinde mit unbeschreiblicher Wucht zerschmettert. Nur wenige kommen dem Springer in Wendigkeit und Agilität nahe. Der Springer kann sich entweder zwei Felder vertikal und ein Feld horizontal oder zwei Felder horizontal und ein Feld vertikal bewegen und scheut dabei nicht davor Figuren zu überspringen. ')";
                pstmt = connection.prepareStatement(inserts);
                pstmt.executeUpdate();}
            if(existA.contains("d")==false){
                String insertd ="insert into a_anleitung (a_id,a_beschreibung) values('d','Die Dame ist die stete Gefährtin des Königs und eine eiserne Strategin. Während sich der König immer mehr Spiel, Speis und Trank zugewandt hat, hat sich die Königin an der Politik beteiligt und sich in der Regierung involviert. Sie hat ihre Spitzel überall und führt ihre Feldzüge mit extremer Präzision und kalter Rücksichtslosigkeit durch. Die Dame kann sich sowohl vertikal, horizontal als auch diagonal bewegen.')";
                pstmt = connection.prepareStatement(insertd);
                pstmt.executeUpdate();}
            if(existA.contains("k")==false){
                String insertk = "insert into a_anleitung (a_id,a_beschreibung) values('k','Der König ist durch ein langes Leben im Wohlstand abgestumpft. Er ist zu eitel sich am Kriegsgeschehen zu beteiligen und der Wein hat seine Sinne getrübt. Sein Sohn verabscheut seine Lebensweise und vergleicht ihn immer wieder mit dem Dorftrottel, da er seiner Meinung genauso gut über das Reich regieren könnte. Der König kann sich ein Feld in jede Richtung bewegen.')";
                pstmt = connection.prepareStatement(insertk);
                pstmt.executeUpdate();}
        }

    }

    public void umwandeln(String pos, String Figur) throws Exception{
        String col = pos.substring(0,1);
        Integer row = Integer.parseInt(pos.substring(1));
        String stmt = "update f_field set f_col"+col+" = '"+Figur+"' where f_id="+row;
        PreparedStatement pstmt = connection.prepareStatement(stmt);
        pstmt.executeUpdate();
    }

    public HashMap<String, String> readAnleitung(HashMap<String, String> anleitungen)throws Exception{
        String stmt = "Select * from a_anleitung";
        PreparedStatement pstmt = connection.prepareStatement(stmt);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            anleitungen.put(rs.getString("a_id"),rs.getString("a_beschreibung"));
        }
        return anleitungen;
    }

    public int checkPlayers() throws Exception{ //return 0 sollte nie eintreten
        String stmt = "Select * from p_players where p_id=1";
        PreparedStatement pstmt = connection.prepareStatement(stmt);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            if(rs.getInt("t_weiss")==0){
                stmt ="update p_players set t_weiss = 1 where p_id = 1";
                pstmt = connection.prepareStatement(stmt);
                pstmt.executeUpdate();
                return 1;
            }
            else if(rs.getInt("t_weiss")==1){
                if(rs.getInt("t_schwarz")==0){
                    stmt ="update p_players set t_schwarz = 1 where p_id = 1";
                    pstmt = connection.prepareStatement(stmt);
                    pstmt.executeUpdate();
                    return 2;
                }
                else if(rs.getInt("t_schwarz")==1){
                    return 3;
                }
                else {
                    return 0;
                }
            }
            else{
                return 0;
            }
        }
        return 0;
    }
    public TimerTask createTimertask(int c){
        if(c==1){
            TimerTask tt1 = new TimerTask() {
                @Override
                public void run() {
                    try{
                        String stmt = "Select * from p_players where p_id=1";
                        PreparedStatement pstmt = connection.prepareStatement(stmt);
                        ResultSet rs = pstmt.executeQuery();
                        while(rs.next()) {
                            if(rs.getInt("t_weiss")==1&&rs.getInt("t_schwarz")==1){
                                callback1=1;
                            }
                        }
                    }
                    catch (java.sql.SQLException e){
                        System.out.println("Fehler bei tt1");
                    }
                }
            };
            return tt1;
        }
        else if(c==2){
            TimerTask tt2 = new TimerTask() {
                @Override
                public void run() {
                    try{
                        String stmt = "Select * from p_players where p_id=2";
                        PreparedStatement pstmt = connection.prepareStatement(stmt);
                        ResultSet rs = pstmt.executeQuery();
                        callback1=0;
                        while(rs.next()) {
                            if(rs.getInt("t_weiss")==0&&rs.getInt("t_schwarz")==1){
                                callback1=2;
                            }
                            else if(rs.getInt("t_weiss")==1&&rs.getInt("t_schwarz")==0){
                                callback1=3;
                            }
                        }
                    }
                    catch (java.sql.SQLException e){
                        System.out.println("Fehler bei tt2");
                    }
                }
            };
            return tt2;
        }
        return null;
    }
    public void setWhite(){
        try {
            String stmt = "update p_players set t_weiss = 1, t_schwarz=0 where p_id = 2";
            PreparedStatement pstmt = connection.prepareStatement(stmt);
            pstmt.executeUpdate();
        }
        catch(java.sql.SQLException e){
            System.out.println("Fehler bei setWhite "+e);
        }
    }
    public void setBlack(){
        try {
            String stmt = "update p_players set t_weiss = 0, t_schwarz=1 where p_id = 1";
            PreparedStatement pstmt = connection.prepareStatement(stmt);
            pstmt.executeUpdate();
        }
        catch(java.sql.SQLException e){
            System.out.println("Fehler bei setBlack "+e);
        }

    }
    public boolean checkTurn(char farbe){
        try {
            String stmt = "Select * from p_players where p_id=2";
            PreparedStatement pstmt = connection.prepareStatement(stmt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                if(farbe=='w'){
                    if(rs.getInt("t_weiss")==1&&rs.getInt("t_schwarz")==0)
                        return true;
                    else if(rs.getInt("t_weiss")==0&&rs.getInt("t_schwarz")==1)
                        return false;
                    else{
                        System.out.println("Ungültige Turn bei checkTurn weiss");
                        return false;
                    }
                }
                else if(farbe=='s'){
                    if(rs.getInt("t_weiss")==1&&rs.getInt("t_schwarz")==0)
                        return false;
                    else if(rs.getInt("t_weiss")==0&&rs.getInt("t_schwarz")==1)
                        return true;
                    else{
                        System.out.println("Ungültige Turn bei checkTurn schwarz");
                        return false;
                    }
                }
                else{
                    System.out.println("Ungültige Farbe bei checkTurn");
                    return false;
                }
            }
        }
        catch(java.lang.Exception e){
            System.out.println("Fehler bei checkTurn " +e);
        }
        System.out.println("Falsche case bei checkTurn");
        return false;
    }

    public Integer getCallback() {
        return callback1;
    }
}

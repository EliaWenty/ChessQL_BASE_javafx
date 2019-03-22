package at.chessql.logik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Feld {

    //Hashmap sit zum Abbilden der momentanen Situation am Feld
    public HashMap<String, String> sit = new HashMap<>();


    public Feld(){
        StringBuilder sb1 = new StringBuilder();

        List<String> cols = new ArrayList<>();
        cols.add("a");
        cols.add("b");
        cols.add("c");
        cols.add("d");
        cols.add("e");
        cols.add("f");
        cols.add("g");
        cols.add("h");

        //Erzeugen der Liste names, die Abkuerzungen fuer alle Figuren enthaelt
        List<String> names=new ArrayList<>();
        for(int i =1;i<9;i++){
            sb1.append("bw");
            sb1.append(i);
            names.add(sb1.toString());
            sb1.setLength(0);
            sb1.append("bs");
            sb1.append(i);
            names.add(sb1.toString());
            sb1.setLength(0);
        }
        names.add("tw1");
        names.add("tw2");
        names.add("ts1");
        names.add("ts2");
        names.add("sw1");
        names.add("sw2");
        names.add("ss1");
        names.add("ss2");
        names.add("lw1");
        names.add("lw2");
        names.add("ls1");
        names.add("ls2");
        names.add("dw");
        names.add("ds");
        names.add("kw");
        names.add("ks");

        //Erzeugen einer Liste mit allen Positionen am Feld
        List<String> pos = new ArrayList<>();

        for(int i = 0;i<cols.size();i++){
            for (int j=1;j<9;j++){
                sb1.append(cols.get(i));
                sb1.append(j);
                pos.add(sb1.toString());
                sb1.setLength(0);
            }

        }


        setPos(names,pos);
    }
    public void setPos(List<String> names, List<String> pos){
        //Alle Keys mit Positionen versehen
        for(int i = 0;i<pos.size();i++){
            sit.put(pos.get(i),null);
        }

        //Figuren setzen

        //weisse Bauern
        sit.replace("b1",names.get(0));
        sit.replace("b2",names.get(2));
        sit.replace("b3",names.get(4));
        sit.replace("b4",names.get(6));
        sit.replace("b5",names.get(8));
        sit.replace("b6",names.get(10));
        sit.replace("b7",names.get(12));
        sit.replace("b8",names.get(14));

        //schwarze Bauern
        sit.replace("g1",names.get(1));
        sit.replace("g2",names.get(3));
        sit.replace("g3",names.get(5));
        sit.replace("g4",names.get(7));
        sit.replace("g5",names.get(9));
        sit.replace("g6",names.get(11));
        sit.replace("g7",names.get(13));
        sit.replace("g8",names.get(15));

        //weisse Tuerme
        sit.replace("a1",names.get(16));
        sit.replace("a8",names.get(17));

        //schwarze Tuerme
        sit.replace("h1",names.get(18));
        sit.replace("h8",names.get(19));

        //weisse Springer
        sit.replace("a2",names.get(20));
        sit.replace("a7",names.get(21));

        //schwarze Springer
        sit.replace("h2",names.get(22));
        sit.replace("h7",names.get(23));

        //weisse Laeufer
        sit.replace("a3",names.get(24));
        sit.replace("a6",names.get(25));

        //schwarze Laeufer
        sit.replace("h3",names.get(26));
        sit.replace("h6",names.get(27));

        //weisse Dame
        sit.replace("a4",names.get(28));

        //schwarze Dame
        sit.replace("h4",names.get(29));

        //weisser Koenig
        sit.replace("a5",names.get(30));

        //schwarzer Koenig
        sit.replace("h5",names.get(31));

        /*
        for(int i=0;i<pos.size();i++){
            System.out.println(pos.get(i)+": "+sit.get(pos.get(i)));
        }
        */
    }



}

package at.chessql.logik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Koenigslogik { //SBauernlogik used
    List<String> fields = new ArrayList<>();
    public Koenigslogik(String pos, Feld f,String farbe) {
        fields = reachableFields(pos, f.sit, farbe);
        for (int i = 0; i < fields.size(); i++) {
            System.out.println("erreichbares Feld: " + fields.get(i));
        }
    }
    public String stringify(){
        String fieldsAlsString = "";
        for(String item:fields){
            fieldsAlsString = fieldsAlsString +"," + item;
        }
        fieldsAlsString = fieldsAlsString.substring(1);
        return fieldsAlsString;
    }

    public List<String> reachableFields(String pos, HashMap sit, String farbe) {

        String col = pos.substring(0, 1);
        Integer row = Integer.parseInt(pos.substring(1));

        List<String> cols = new ArrayList<>();
        cols.add("a");
        cols.add("b");
        cols.add("c");
        cols.add("d");
        cols.add("e");
        cols.add("f");
        cols.add("g");
        cols.add("h");

        List<Integer> rows = new ArrayList<>();
        rows.add(1);
        rows.add(2);
        rows.add(3);
        rows.add(4);
        rows.add(5);
        rows.add(6);
        rows.add(7);
        rows.add(8);


        List<String> reachableFields = new ArrayList<>();
        Integer colPosition = cols.indexOf(col);

        if (row == 1) {
            System.out.println("oberste Zeile");
            Integer below = row + 1;
            if(colPosition>0){
                reachableFields.add(cols.get(colPosition - 1) + below);
                reachableFields.add(cols.get(colPosition -1) + row);}
            if(colPosition<7) {
                reachableFields.add(cols.get(colPosition + 1) + below);
                reachableFields.add(cols.get(colPosition + 1) + row); }

            reachableFields.add(cols.get(colPosition) + below);
        } else if (row == 8) {
            System.out.println("unterste Zeile");
            Integer above = row - 1;
            if(colPosition>0){
                reachableFields.add(cols.get(colPosition - 1) + above);
                reachableFields.add(cols.get(colPosition - 1) + row);}
            if(colPosition<7){
                reachableFields.add(cols.get(colPosition + 1) + above);
                reachableFields.add(cols.get(colPosition +1) + row);}

            reachableFields.add(cols.get(colPosition) + above);
        } else {
            System.out.println("Nicht oberste oder unterste Zeile");
            Integer below = row + 1;
            Integer above = row - 1;
            if(colPosition>0){
                reachableFields.add(cols.get(colPosition - 1) + above);
                reachableFields.add(cols.get(colPosition - 1) + row);
                reachableFields.add(cols.get(colPosition - 1) + below);}
            if(colPosition<7){
                reachableFields.add(cols.get(colPosition + 1) + above);
                reachableFields.add(cols.get(colPosition + 1) + below);
                reachableFields.add(cols.get(colPosition + 1) + row);}

            reachableFields.add(cols.get(colPosition) + above);
            reachableFields.add(cols.get(colPosition) + below);

        }

        List<String> realreachablefields = new ArrayList<>();

        for(String s:reachableFields){
            try{
               if(sit.get(s).toString().substring(1, 2).equals(farbe)==false) {
                   System.out.println(s + "removed weil gleiche farbe: " + sit.get(s).toString().substring(1, 2) + " = " + farbe);
                   realreachablefields.add(s);
               }
            }
            catch (NullPointerException e){
                System.out.println("Leeres Feld");
                realreachablefields.add(s);
            }
        }

        return realreachablefields;
    }


}

package at.chessql.logik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Turmlogik {
    List<String> fields = new ArrayList<>();
    public Turmlogik(String pos, Feld f, String farbe){
        fields=reachableFields(pos,f.sit, farbe);
        try {
            for (int i = 0; i < fields.size(); i++) {
                System.out.println(fields.get(i));
            }
        }
        catch (NullPointerException e){
            System.out.println("Keine Felder erreichbar");
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
        List<String> felderRechts = new ArrayList<>();
        List<String> felderLinks = new ArrayList<>();
        List<String> felderOben = new ArrayList<>();
        List<String> felderUnten = new ArrayList<>();

        Integer colPosition = cols.indexOf(col);

        for(int i=colPosition+1;i<cols.size();i++){ //Felder rechts vom Turm
            felderRechts.add(cols.get(i)+row);
        }
        for(int i=colPosition-1;i>-1;i--){ //Felder links vom Turm
            felderLinks.add(cols.get(i)+row);
        }
        for(int i=row-1;i<rows.size()-1;i++){ //Felder unter dem Turm
            System.out.println(i);
            felderUnten.add(cols.get(colPosition)+rows.get(i+1).toString());
        }
        for(int i=row-1;i>0;i--){ //Felder über dem Turm
            felderOben.add(cols.get(colPosition)+rows.get(i-1).toString());
        }

        reachableFields.addAll(blockedFields(felderRechts,sit,farbe));
        reachableFields.addAll(blockedFields(felderLinks,sit,farbe));
        reachableFields.addAll(blockedFields(felderOben,sit,farbe));
        reachableFields.addAll(blockedFields(felderUnten,sit,farbe));

        return reachableFields;
        }

        public List<String> blockedFields(List<String> list, HashMap sit, String farbe){
            for(int i=0;i<list.size();i++){
                try {
                    if (sit.get(list.get(i)).toString().substring(1, 2).equals(farbe)) {
                        list.subList(i, list.size()).clear();
                    }
                    else if (sit.get(list.get(i)).toString().substring(1, 2).equals(farbe)==false)
                    {
                    list.subList(i+1,list.size()).clear();
                    }
                }
                catch (NullPointerException e){
                    System.out.println("Leeres Feld");
                }
            }
        return list;
    }


    }

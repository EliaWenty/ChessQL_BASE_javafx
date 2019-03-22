package at.chessql.logik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SBauernlogik {
    List<String> fields = new ArrayList<>();
    public SBauernlogik(String pos, Feld f) {
            fields = reachableFields(pos, f.sit);
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

        public List<String> reachableFields(String pos, HashMap sit) {

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
            List<String> schlagFields = new ArrayList<>();
            Integer colPosition = cols.indexOf(col);

            if (row == 1) {
                System.out.println("oberste Zeile");
                Integer below = row + 1;
                if(colPosition>0){
                    schlagFields.add(cols.get(colPosition - 1) + below);
                    reachableFields.add(cols.get(colPosition -1) + row);}
                    //schlagFields.add(cols.get(colPosition + 1) + below);
                if (col.equals("g")) {
                    reachableFields.add(cols.get(colPosition - 2) + row);
                }
            } else if (row == 8) {
                System.out.println("unterste Zeile");
                Integer above = row - 1;
                if(colPosition>0){
                    schlagFields.add(cols.get(colPosition - 1) + above);
                    reachableFields.add(cols.get(colPosition - 1) + row);}
                    //schlagFields.add(cols.get(colPosition + 1) + above);
                if (col.equals("g")) {
                    reachableFields.add(cols.get(colPosition - 2) + row);
                }
            } else {
                System.out.println("Nicht oberste oder unterste Zeile");
                Integer below = row + 1;
                Integer above = row - 1;
                if(colPosition>0){
                    schlagFields.add(cols.get(colPosition - 1) + above);
                    schlagFields.add(cols.get(colPosition - 1) + below);
                    reachableFields.add(cols.get(colPosition - 1) + row);}
                    //schlagFields.add(cols.get(colPosition + 1) + above);
                    //schlagFields.add(cols.get(colPosition + 1) + below);
                if (col.equals("g")) {
                    reachableFields.add(cols.get(colPosition - 2) + row);
                }

            }

            List<String> realschlagfields = new ArrayList<>();
            for(String s:schlagFields){
                try{
                    if(sit.get(s).toString().substring(1, 2).equals("w")) {
                        realschlagfields.add(s);
                    }
                }
                catch (NullPointerException e){
                    System.out.println("Leeres Feld schlagfield nicht hinzugefuegt");
                }
            }

            Collections.sort(reachableFields);
            Collections.reverse(reachableFields);

            for(int i=0;i<reachableFields.size();i++){
                try {
                    if (sit.get(reachableFields.get(i)).toString().substring(1, 2).equals("s")||sit.get(reachableFields.get(i)).toString().substring(1, 2).equals("w")) {
                        reachableFields.subList(i, reachableFields.size()).clear();
                    }
                    else if (sit.get(reachableFields.get(i)).toString().substring(1, 2).equals("s")==false||sit.get(reachableFields.get(i)).toString().substring(1, 2).equals("s"))
                    {
                        reachableFields.subList(i+1,reachableFields.size()).clear();
                    }
                }
                catch (NullPointerException e){
                    System.out.println("Leeres Feld");
                }
            }

            reachableFields.addAll(realschlagfields);

            return reachableFields;
        }

    }

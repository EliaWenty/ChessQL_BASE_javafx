package at.chessql.logik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Laeuferlogik {
    List<String> fields = new ArrayList<>();
    public Laeuferlogik(String pos, Feld f, String farbe){
        fields=reachableFields(pos,f.sit, farbe);
        for(int i =0;i< fields.size();i++){
            System.out.println(fields.get(i));
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

    public List<String> reachableFields(String pos, HashMap sit, String farbe){

        String col = pos.substring(0,1);
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




        List<String> reachableFields=new ArrayList<>();
        Integer colPosition = cols.indexOf(col);

        for(int i = 0;i<rows.size();i++){
            boolean b = rows.get(i)==row;
            System.out.println("I:"+i);
            System.out.println("Row laeufer is in: "+row);
            System.out.println("Row being checked: "+rows.get(i)+" "+b);

            if(row>rows.get(i)){
                if(row - rows.get(i)==1){
                    if(colPosition-1>=0)
                        reachableFields.add(cols.get(colPosition-1)+rows.get(i));
                    if (colPosition+1<=7)
                        reachableFields.add(cols.get(colPosition+1)+rows.get(i));
                }
                else if(row - rows.get(i)==2){
                    if(colPosition-2>=0)
                        reachableFields.add(cols.get(colPosition-2)+rows.get(i));
                    if (colPosition+2<=7)
                        reachableFields.add(cols.get(colPosition+2)+rows.get(i));
                }
                else if(row - rows.get(i)==3){
                    if(colPosition-3>=0)
                        reachableFields.add(cols.get(colPosition-3)+rows.get(i));
                    if (colPosition+3<=7)
                        reachableFields.add(cols.get(colPosition+3)+rows.get(i));
                }
                else if(row - rows.get(i)==4){
                    if(colPosition-4>=0)
                        reachableFields.add(cols.get(colPosition-4)+rows.get(i));
                    if (colPosition+4<=7)
                        reachableFields.add(cols.get(colPosition+4)+rows.get(i));
                }
                else if(row - rows.get(i)==5){
                    if(colPosition-5>=0)
                        reachableFields.add(cols.get(colPosition-5)+rows.get(i));
                    if (colPosition+5<=7)
                        reachableFields.add(cols.get(colPosition+5)+rows.get(i));
                }
                else if(row - rows.get(i)==6){
                    if(colPosition-6>=0)
                        reachableFields.add(cols.get(colPosition-6)+rows.get(i));
                    if (colPosition+6<=7)
                        reachableFields.add(cols.get(colPosition+6)+rows.get(i));
                }
                else if(row - rows.get(i)==7){
                    if(colPosition-7>=0)
                        reachableFields.add(cols.get(colPosition-7)+rows.get(i));
                    if (colPosition+7<=7)
                        reachableFields.add(cols.get(colPosition+7)+rows.get(i));
                }
            }
            else if(rows.get(i)>row){
                if(rows.get(i)-row == 1){
                    System.out.println(colPosition-1);
                    System.out.println(colPosition+1);
                    if(colPosition-1>=0)
                        reachableFields.add(cols.get(colPosition-1)+rows.get(i));
                    if (colPosition+1<=7)
                        reachableFields.add(cols.get(colPosition+1)+rows.get(i));
                }
                else if(rows.get(i)-row == 2){
                    if(colPosition-2>=0)
                        reachableFields.add(cols.get(colPosition-2)+rows.get(i));
                    if (colPosition+2<=7)
                        reachableFields.add(cols.get(colPosition+2)+rows.get(i));
                }
                else if(rows.get(i)-row == 3){
                    if(colPosition-3>=0)
                        reachableFields.add(cols.get(colPosition-3)+rows.get(i));
                    if (colPosition+3<=7)
                        reachableFields.add(cols.get(colPosition+3)+rows.get(i));
                }
                else if(rows.get(i)-row == 4){
                    if(colPosition-4>=0)
                        reachableFields.add(cols.get(colPosition-4)+rows.get(i));
                    if (colPosition+4<=7)
                        reachableFields.add(cols.get(colPosition+4)+rows.get(i));
                }
                else if(rows.get(i)-row == 5){
                    if(colPosition-5>=0)
                        reachableFields.add(cols.get(colPosition-5)+rows.get(i));
                    if (colPosition+5<=7)
                        reachableFields.add(cols.get(colPosition+5)+rows.get(i));
                }
                else if(rows.get(i)-row == 6){
                    if(colPosition-6>=0)
                        reachableFields.add(cols.get(colPosition-6)+rows.get(i));
                    if (colPosition+6<=7)
                        reachableFields.add(cols.get(colPosition+6)+rows.get(i));
                }
                else if(rows.get(i)-row == 7){
                    if(colPosition-7>=0)
                        reachableFields.add(cols.get(colPosition-7)+rows.get(i));
                    if (colPosition+7<=7)
                        reachableFields.add(cols.get(colPosition+7)+rows.get(i));
                }
            }
        }
        List<List<String>> quadrants = splitQuadrants(reachableFields, row, colPosition, cols);
        reachableFields.clear();
        reachableFields.addAll(blockedFields(quadrants.get(0),sit,farbe));
        reachableFields.addAll(blockedFields(quadrants.get(1),sit,farbe));
        reachableFields.addAll(blockedFields(quadrants.get(2),sit,farbe));
        reachableFields.addAll(blockedFields(quadrants.get(3),sit,farbe));



/*
        for(int i=0;i<reachableFields.size();i++){
            try{
                if(sit.get(reachableFields.get(i))!=null){
                reachableFields.remove(i);
                }
            }
            catch (NullPointerException e){
                System.out.println("lol");
            }

        }*/

        return reachableFields;
    }
    public List<List<String>> splitQuadrants(List<String> list, Integer row, Integer colPosition, List<String> cols){
        List<List<String>> quadrants = new ArrayList<>();
        List<String> quadrant1 = new ArrayList<>(); //links oberhalb des Läufers
        List<String> quadrant2 = new ArrayList<>(); //rechts oberhalb des Läufers
        List<String> quadrant3 = new ArrayList<>(); //rechts unterhalb des Läufers
        List<String> quadrant4 = new ArrayList<>(); //links unterhalb des Läufers

        for(int i=0;i<list.size();i++){
            Integer fieldrow = Integer.parseInt(list.get(i).substring(1));
            Integer fieldcolpos = cols.indexOf(list.get(i).substring(0,1));
            if(fieldrow < row && fieldcolpos < colPosition){
                quadrant1.add(list.get(i));
            }
            else if(fieldrow < row && fieldcolpos > colPosition){
                quadrant2.add(list.get(i));
            }
            else if(fieldrow > row && fieldcolpos > colPosition){
                quadrant3.add(list.get(i));
            }
            else if(fieldrow > row && fieldcolpos < colPosition){
                quadrant4.add(list.get(i));
            }
            else{
                System.out.println("Fehler ungültiges Feld in reachable Fields (Für Läufer nicht erreichbar - vertikal rechts, links, oben, unten");
            }
            Collections.sort(quadrant1);
            Collections.sort(quadrant2);
            Collections.sort(quadrant3);
            Collections.sort(quadrant4);
            Collections.reverse(quadrant1);
            Collections.reverse(quadrant4);
            quadrants.add(quadrant1);
            quadrants.add(quadrant2);
            quadrants.add(quadrant3);
            quadrants.add(quadrant4);
        }
        return quadrants;
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

package at.chessql.logik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Springerlogik {
    List<String> fields = new ArrayList<>();
    public Springerlogik(String pos, Feld f, String farbe) {
        fields = reachableFields(pos, f.sit, farbe);
        for (int i = 0; i < fields.size(); i++) {
            System.out.println(i +". erreichbares Feld: " + fields.get(i));
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

        int runter1 = row + 1;
        int runter2 = row + 2;
        int rauf1 = row - 1;
        int rauf2 = row - 2;


        if (row == 1) {
            System.out.println("erste Zeile");
            if (colPosition == 0) {
                System.out.println("colposition 0");
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);

            }
            else if (colPosition == 1) {
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);


            }
            else if (colPosition == 6) {
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);

            }
            else if (colPosition == 7) {
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);

            } else {
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);


            }
        } else if (row == 2) {
            System.out.println("zweite Zeile");
            if (colPosition == 0) {
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);

            }
            else if (colPosition == 1) {
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);

            }
            else if (colPosition == 6) {
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);

            }
            else if (colPosition == 7) {

                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);

            } else {
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);


            }
        } else if (row == 7) {
            System.out.println("siebte Zeile");
            if (colPosition == 0) {
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 1) {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 6) {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 7) {

                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);

            } else {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);


            }
        } else if (row == 8) {
            System.out.println("achte Zeile");
            if (colPosition == 0) {
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 1) {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 6) {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 7) {

                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);

            } else {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);


            }
        } else {
            System.out.println("mitte");
            if (colPosition == 0) {
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 1) {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 6) {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);

            }
            else if (colPosition == 7) {

                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);

            } else {
                //1
                reachableFields.add(cols.get(colPosition - 1) + rauf2);
                //2
                reachableFields.add(cols.get(colPosition - 2) + rauf1);
                //3
                reachableFields.add(cols.get(colPosition - 2) + runter1);
                //4
                reachableFields.add(cols.get(colPosition - 1) + runter2);
                //5
                reachableFields.add(cols.get(colPosition + 1) + runter2);
                //6
                reachableFields.add(cols.get(colPosition + 2) + runter1);
                //7
                reachableFields.add(cols.get(colPosition + 2) + rauf1);
                //8
                reachableFields.add(cols.get(colPosition + 1) + rauf2);
            }
        }
        List<String> realreachablefields = new ArrayList<>();

        for (String s : reachableFields) {
            try {
                System.out.println(s);
                if (sit.get(s).toString().substring(1, 2).equals(farbe)==false) {
                    System.out.println(s + " hinzugefügt weil gegenteilige farbe: " + sit.get(s).toString().substring(1, 2) + " = " + farbe);
                    realreachablefields.add(s);
                }

            } catch (NullPointerException e) {
                    System.out.println("Leeres Feld");
                    realreachablefields.add(s);
            }
        }
        return realreachablefields;
    }
}

package com.nocdib.generics;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        System.out.println("This is Generic Country");
        Point origin = new Point();
        Point dest = new Point(3,5);
        // Raw ArrayList. Allows anything to be added.
        ArrayList al = new ArrayList();
        al.add(origin);
        al.add(dest);
        al.add(45);
        al.add("anything else");
        System.out.println(al.get(0));
        System.out.println(al.get(1));
        System.out.println(al.get(2));
        System.out.println(al.get(3));
        // Type-enforced ArrayList. Allows only Point objects to be added.
        ArrayList<Point> point_al = new ArrayList<>();
        point_al.add(origin);
        point_al.add(dest);
        // point_al.add(45);    -- Error because cannot add int
        //point_al.add("anything else");  -- Error because cannot add String

        Iterator<Point> iter = point_al.iterator();
        while(iter.hasNext()) {
            System.out.println(iter.next());
        }

    }
}

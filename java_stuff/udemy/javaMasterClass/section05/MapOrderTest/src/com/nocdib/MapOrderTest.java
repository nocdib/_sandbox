package com.nocdib;

import java.util.*;

public class MapOrderTest {

    public static void main(String[] args) {
	    // This is to test a sorting mechanism for map objects
        Map<Integer, String> map = new TreeMap<>();

        // Adding new key-value pairs to a TreeMap
        map.put(1, "In Progress");
        map.put(2, "Needs Editing");
        map.put(3, "Needs Review");
        map.put(4, "Needs Print Review");
        map.put(5, "Done");
        map.put(6, "Tentative");

        // Create an empty map
        LinkedHashMap<Integer, String> tentativeFirstMap = new LinkedHashMap<>();

        // Iterate through the TreeMap to find "Tentative"
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            // Add "Tentative" to the new map and remove it from the old map
            if(entry.getValue().equals("Tentative")) {
                tentativeFirstMap.put(entry.getKey(), entry.getValue());
                map.remove(entry.getKey(), entry.getValue());
                break;
            }
        }

        // Add the other map items in order
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            tentativeFirstMap.put(entry.getKey(), entry.getValue());
        }

        System.out.println(map);
        System.out.println(tentativeFirstMap);
    }
}

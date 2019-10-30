/*
package com.nocdib;

import java.util.Hashtable;
import java.util.function.Function;

public class Train {
    private Hashtable<Integer, Integer> wagons;

    public Train(int wagonCount, Function<Integer, Integer> fillWagon) {
        this.wagons = new Hashtable<Integer, Integer>();

        for (int i = 0; i < wagonCount; i++) {
            this.wagons.put(i, fillWagon.apply(i));
        }
    }

    public int peekWagon(int wagonIndex) {
        return this.wagons.get(wagonIndex);
    }

    public static void main(String[] args) {
        Train train = new Train(100000000, wagonIndex -> wagonIndex);

        for (int i = 0; i < 100000000; i++) {
            System.out.println("Wagon: " + i + ", cargo: " + train.peekWagon(i));
        }
    }
}
*/

/* */
package com.nocdib;

import java.util.Hashtable;
import java.util.function.Function;

public class Train {
    private Hashtable<Integer, Integer> wagons;
    private Function<Integer, Integer> fillWagon;

    public Train(int wagonCount, Function<Integer, Integer> fillWagon) {
        this.wagons = new Hashtable<Integer, Integer>();
        this.fillWagon = fillWagon;
    }

    public int peekWagon(int wagonIndex) {
        return this.fillWagon.apply(wagonIndex).intValue();
    }

    public static void main(String[] args) {
        int num = 100000000;
        Train train = new Train(num, wagonIndex -> wagonIndex);

        for (int i = 0; i < num; i++) {
            System.out.println("Wagon: " + i + ", cargo: " + train.peekWagon(Integer.valueOf(i)));
        }
    }
}

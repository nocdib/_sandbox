package com.nocdib.chapter02;

/**
 * Created by 207515 on 5/9/17.
 */
class SwitchStatement {
    static void example(){
        System.out.println("Without Breaks\n---------------");
        for(int x=1; x<4; x++) {
            switch (x) {
                case 1:
                    System.out.println("One");
                case 2:
                    System.out.println("Two");
                case 3:
                    System.out.println("Three");
            }
            System.out.println();
        }
        System.out.println("With Breaks\n-----------");
        for(int x=1; x<4; x++) {
            switch (x) {
                case 1:
                    System.out.println("One");
                    break;
                case 2:
                    System.out.println("Two");
                    break;
                case 3:
                    System.out.println("Three");
                    break;
            }
            System.out.println();
        }
        System.out.println("With Strings\n-------------");
        String y = "Greg";
        switch (y) {
            case "James":
                System.out.println("James");
                break;
            case "Greg":
                System.out.println("Greg");
                break;
            case "Aidan":
                System.out.println("Aidan");
                break;
        }
    }
}

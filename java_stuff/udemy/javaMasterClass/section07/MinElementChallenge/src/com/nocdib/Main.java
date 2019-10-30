package com.nocdib;

import org.w3c.dom.ls.LSInput;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static int[] getNumbers(int count){
        int [] numArray = new int[count];
        for(int x=0; x<count; x++){
            System.out.printf("Enter integer #%d: ", x+1);
            numArray[x] = scanner.nextInt();
        }
        return numArray;
    }

    public static int getMin(int [] array){
        int min = Integer.MAX_VALUE;
        for(int x=0; x<array.length; x++){
            min = array[x] < min ? array[x] : min;
        }
        return min;
    }

    public static void main(String[] args) {
	int [] numbers = getNumbers(5);
	System.out.printf("The smallest value in the array is: %d", getMin(numbers));
    }
}

/*
-Write a method called readIntegers() with a parameter called count that represents how many integers the user needs to enter.

-The method needs to read from the console until all the numbers are entered, and then return an array containing the numbers entered.

-Write a method findMin() with the array as a parameter. The method needs to return the minimum value in the array.

-In the main() method read the count from the console and call the method readIntegers() with the count parameter.

-Then call the findMin() method passing the array returned from the call to the readIntegers() method.

-Finally, print the minimum element in the array.

Tips:
	-Assume that the user will only enter numbers, never letters
	-For simplicity, create a Scanner as a static field to help with data input
	-Create a new console project with the name ÅeMinElementChallengeÅf

 */
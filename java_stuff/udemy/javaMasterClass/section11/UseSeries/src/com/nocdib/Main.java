package com.nocdib;

import com.nocdib.sumfactfib.Series;

// Make use of the JAR created from the Series project.

public class Main {

    public static void main(String[] args) {
        int n = 10;
        System.out.println(Series.factorial(n));
        System.out.println(Series.fibonacci(n));
        System.out.println(Series.nSum(n));
    }
}

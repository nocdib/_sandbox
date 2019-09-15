import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    /*
    1) The formula for the sum of numbers 1..n is (n*(n+1))/2

    2) We want the sum of numbers 1...n-1 so (n*(n-1))/2

    3) We want the sum of the multiples of 3 and 5 but we don't want to double-count the sum of the multiples of both 3 and 5, which is 15.

    4) Since the upper bound is n-1 (see step 2) for the multiples of 3 the highest bound is (n-1)/3 and. Se only need the sum to that upper bound and then multiply it by 3.

    5) Apply step 4 to the multiples of 5 and 15 and the final answer = sumOfMultiplesOf3 + sumOfMultiplesOf5 - sumOfMultiplesOf15.

    */
    public static long sumOfMultiplesOf(int multiple, int max){
        max--;
        max /= multiple;
        /* 
        It's possible for the answer to exceed Java's memory bounds for int so return the answer as long.
        */
        return (long)multiple * (((long)max * (long)(max+1)) / 2L);
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            if (n > 0 && n <= 1000000000) {
                System.out.println(sumOfMultiplesOf(3,n) + sumOfMultiplesOf(5,n) - sumOfMultiplesOf(15,n));
            }
        }
    }
}
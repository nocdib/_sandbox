public class SharedDigit {

    public static boolean hasSharedDigit(int num1, int num2) {
        if((num1 >= 10 && num1 <= 99) && (num2 >= 10 && num2 <= 99)) {
            int digit1, digit2, temp;
            while(num1 != 0) {
                temp = num2;
                digit1 = num1 % 10;
                while(temp != 0) {
                    digit2 = temp % 10;
                    if(digit1 == digit2) {
                        return true;
                    }
                    temp = temp/10;
                }
                num1 = num1/10;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        System.out.println(hasSharedDigit(11, 44));
        System.out.println(hasSharedDigit(11, 31));
        System.out.println(hasSharedDigit(11, 15));

    }
}

/*

Write a method named hasSharedDigit with two parameters of type int.

Each number should be within the range of 10 (inclusive) - 99 (inclusive). If one of the numbers is not within the range, the method should return false.

The method should return true if there is a digit that appears in both numbers, such as 2 in 12 and 23; otherwise, the method should return false.


EXAMPLE INPUT/OUTPUT:

* hasSharedDigit(12, 23); → should return true since the digit 2 appears in both numbers

* hasSharedDigit(9, 99); → should return false since 9 is not within the range of 10-99

* hasSharedDigit(15, 55); → should return true since the digit 5 appears in both numbers


NOTE: The method hasSharedDigit should be defined as public static like we have been doing so far in the course.

NOTE: Do not add a main method to the solution code.

*/
public class ShiftingBits {
    // function to reverse bits of a number
    public static int reverseBits(int n)
    {
        int rev = 0;

        // traversing bits of 'n'
        // from the right
        while (n > 0)
        {
            System.out.printf("n = %d (%s)\n", n, Integer.toString(n,2));
            // bitwise left shift
            // 'rev' by 1
            rev <<= 1;
            System.out.printf("rev = %d (%s)\n", rev, Integer.toString(rev,2));

            // if current bit is '1'
            if ((int)(n & 1) == 1) {
                System.out.printf("(int)(n & 1) = %d\n", (int)(n & 1));
                rev ^= 1;
                System.out.printf("rev = %d (%s)\n", rev, Integer.toString(rev,2));
            } else {
                System.out.printf("(int)(n & 1) = %d\n", (int)(n & 1));
            }

            // bitwise right shift
            //'n' by 1
            n >>= 1;

        }
        // required number
        return rev;
    }

    public static String decimalToBinary(int n) {
        return n + ")\t " + Integer.toString(n,2);
    }

    // Driver code
    public static void main(String[] args)
    {
        int n = 11;
        int reverse = reverseBits(n);
        System.out.printf("The number is:\t %d (%s)\n", n, Integer.toString(n,2));
        System.out.printf("The reverse is:\t %d (%s)\n", reverse, Integer.toString(reverse,2));

        int num = 13;
        System.out.println(num ^ 1);
        System.out.println(decimalToBinary(num));
        num >>= 1;
        System.out.println(decimalToBinary(num));
        num >>= 1;
        System.out.println(decimalToBinary(num));
    }
}
/*

100000

*/

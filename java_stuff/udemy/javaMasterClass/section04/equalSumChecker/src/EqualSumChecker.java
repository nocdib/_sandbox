public class EqualSumChecker {

    public static boolean hasEqualSum(int val1, int val2, int sum) {
        return val1 + val2 == sum;
    }

    public static void main(String[] args) {
        System.out.println(hasEqualSum(1,1,1));
        System.out.println(hasEqualSum(1,1,2));
        System.out.println(hasEqualSum(1,-1,0));
    }
}

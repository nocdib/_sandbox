public class TeenNumberChecker {

    public static boolean isTeen(int num) {
        if(num >= 13 && num <= 19) {
            return true;
        }
        return false;
    }

    public static boolean hasTeen(int num1, int num2, int num3) {
        if(isTeen(num1) || isTeen(num2) || isTeen(num3)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(hasTeen(9, 99, 19));
        System.out.println(hasTeen(23, 15, 42));
        System.out.println(hasTeen(22, 23, 34));
    }
}

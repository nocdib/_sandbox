public class VarArgs {

    public static int sum (String something, int... nums){
        int sum = 0;
        System.out.printf("%s = ", something);
        for(int num: nums) {
           sum += num;
        }
        return sum;
    }
    public static void main(String[] args) {
        System.out.println(sum("1",1));
        System.out.println(sum("1+2",1,2));
        System.out.println(sum("1+2+3",1,2,3));
    }
}

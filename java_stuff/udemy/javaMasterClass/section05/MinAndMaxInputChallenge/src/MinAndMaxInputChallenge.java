import java.util.Scanner;

// Read number from user input, track min/max, stop on non-numeric input
public class MinAndMaxInputChallenge {

    public static void main(String[] args) {
        int count = 0;
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        int current;
        boolean validInput = true;
        Scanner scanner = new Scanner(System.in);

        while(validInput) {
            System.out.printf("Enter a number: ");
            if(scanner.hasNextInt()) {
                if(count == 0){
                    min = scanner.nextInt();
                    max = min;
                    scanner.nextLine();
                } else {
                    current = scanner.nextInt();
                    min = current < min ? current : min;
                    max = current > max ? current : max;
                    scanner.nextLine();
                }
                count++;
            } else {
                if(count == 0){
                    System.out.println("Min and Max have no value");
                } else {
                    System.out.printf("Min is %d and Max is %d \n",min, max);
                }
                validInput = false;
            }
        }
        scanner.close();
    }
}

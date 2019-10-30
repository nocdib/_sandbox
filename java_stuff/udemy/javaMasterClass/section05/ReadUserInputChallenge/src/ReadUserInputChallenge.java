import java.util.Scanner;

/* Read 10 number entered by the user then calculate the sum */

public class ReadUserInputChallenge {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        int sum = 0;

        while(counter < 11) {
            System.out.printf("Enter number #%d: ", counter);
            if(scanner.hasNextInt()){
                sum += scanner.nextInt();
                scanner.nextLine();
                counter++;
            } else {
                System.out.println("Invalid input. Try again.\n");
                scanner.nextLine();
            }
        }
        scanner.close();
        System.out.printf("\nThe sum of all the numbers is %d", sum);
    }
}

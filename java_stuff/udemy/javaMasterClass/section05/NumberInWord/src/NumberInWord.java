public class NumberInWord {

    public static void printNumberInWord(int number) {
        String numberWord = "";
        switch(Math.abs(number)) {
            case 0:
                numberWord += "ZERO";
                break;
            case 1:
                numberWord += "ONE";
                break;
            case 2:
                numberWord += "TWO";
                break;
            case 3:
                numberWord += "THREE";
                break;
            case 4:
                numberWord += "FOUR";
                break;
            case 5:
                numberWord += "FIVE";
                break;
            case 6:
                numberWord += "SIX";
                break;
            case 7:
                numberWord += "SEVEN";
                break;
            case 8:
                numberWord += "EIGHT";
                break;
            case 9:
                numberWord += "NINE";
                break;
            default:
                numberWord += "OTHER";
                break;
        }
        numberWord = number < 0 ? "NEGATIVE " + numberWord : numberWord;
        System.out.println(numberWord);
    }

    public static void main(String[] args) {
        printNumberInWord(-1);
        printNumberInWord(0);
        printNumberInWord(1);
        printNumberInWord(2);
        printNumberInWord(3);
        printNumberInWord(4);
        printNumberInWord(5);
        printNumberInWord(6);
        printNumberInWord(7);
        printNumberInWord(8);
        printNumberInWord(9);
        printNumberInWord(10);
    }

}

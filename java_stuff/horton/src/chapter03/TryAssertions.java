package chapter03;

public class TryAssertions{

/*	An example of using assertions
	Remember to run using "java -ea [classname]"
*/

	public static void main(String[] args){
		int daysInMonth = 32; 
		switch(daysInMonth){
			case(30):
				System.out.println("30-day month");
				break;			
			case(29):
				System.out.println("29-day month");
				break;			
			case(28):
				System.out.println("28-day month");
				break;
			case(31):
				System.out.println("31-day month");
				break;
			default:
				assert false : daysInMonth + " is an invalid number of days";
		}
	
	}

}

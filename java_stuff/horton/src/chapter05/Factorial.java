public class Factorial {
	public static void main(String[] args){
		int x = 5;
		System.out.println(x + "! = " +  factorial(x) );
	}

	private static int factorial(int n){
	    return (n==0) ? 1 : n * factorial(n-1);
	}
}

package chapter03;

public class MultiExpressionForLoop {
	public static void main(String[] args){
		int x, y;

		for(x = 1, y = 100; x < y; x++, y--)
			System.out.printf("x = %d, y = %d\n",x,y);
	}

}

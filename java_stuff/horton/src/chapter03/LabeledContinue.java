package chapter03;

public class LabeledContinue{

	/* Only print x when it is odd */

	public static void main(String[] args){
		OuterLoop:
		for(int x=0; x<10; x++){
			if(x%2 ==0)
				continue OuterLoop;
			System.out.printf("x = %d\n",x);

		}
	
	}


}

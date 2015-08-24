package chapter02;

public class HexLiteral {

	public static void main(String[] args){
		long hexNum = 35;
		System.out.println("hexNum = " + hexNum);
		char c = '\udce5';
		System.out.println("c = " + c);
StringBuffer sb = new StringBuffer();
    sb.append(Character.toChars(127468));
    sb.append(Character.toChars(127479));
    System.out.println(sb);
	System.out.println(2 ^ 2);
	}


}

package com.nocdib;

public class ForLoop {

	public static void main(String[] args) {
		int x = 0;
		for(; x<5; ++x){
			System.out.println(x);
		}
		x = 0;
		System.out.println("\n" + x++);
		System.out.println(x);
		System.out.println(++x);

	}

}
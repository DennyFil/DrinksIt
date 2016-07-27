package ru.drinksit.tester;

import java.util.Scanner;

import sun.misc.BASE64Encoder;

public class Base64Encoder {

	public static void main(String[] args) {

		Scanner keyb = new Scanner(System.in);
		
		System.out.println("Input user name: ");
		String userName = keyb.nextLine();
		
		System.out.println("Input password: ");
		String password = keyb.nextLine();
		
		String concat = userName + ":" + password;
		
		System.out.println(new BASE64Encoder().encode(concat.getBytes()));
		
		keyb.close();

	}

}

package ru.drinksit.auxillary;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main (String [] args)
	{
		System.out.println(encodePassword("DrinksItWSAdminPassword"));
	}
	
	public static String encodePassword(String password)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordHash = passwordEncoder.encode(password);
		
		return passwordHash;
	}
}

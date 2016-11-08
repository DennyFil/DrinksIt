package ru.drinksit.auxillary;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;

public class PasswordEncoder {

	public static void main (String [] args)
	{
		System.out.println(encodePassword("denis"));
		
		String signature = "POST\n{\"userName\":\"denis\"}\napplication/json\nnull\nhttp://drinksit:8080/DrinksItSrv/orders";
			
		String passwordHash = "bPNDr/NSWqI2bB0g4yclU9yt/2CK8KhdnDdfw2CF7rNGLdTq5DAhd5hJjPXJyBmcD7/WZ8YcS/zgB+8EypHS4Q==";
			
		System.out.println(calculateHMAC(passwordHash, signature));
	}
	
	public static String calculateHMAC(String secret, String data) {
    	
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            String result = new String(Base64Utils.encodeToString(rawHmac));
            return result;
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException();
        }
    }
	
	public static String encodePassword(String password)
	{		
		MessageDigest md = null;
		try {
		    md = MessageDigest.getInstance("SHA-512");
		    md.update(password.getBytes());
		    byte byteData[] = md.digest();
		    return Base64Utils.encodeToString(byteData);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Could not load MessageDigest: SHA-512");
		}	

	    return null;
	}
}

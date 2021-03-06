package webservice.auxillary;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import webservice.auxillary.DTO.User;

@Configuration
@ComponentScan({ "webservice.auxillary" })
@PropertySource(value = { "classpath:server.properties" })
public class TokenService {
    
	//@Autowired
    //private Environment environment;
	
    public boolean CheckToken(String token, User user) {
    	
    	try {
    	    Algorithm algorithm = Algorithm.HMAC512("secret");
    	    JWTVerifier verifier = JWT.require(algorithm)
    	    	.withClaim("userName", user.getUserName())
    	    	.withClaim("barId", user.getBarId())
    	    	.withClaim("passwordHash", user.getPasswordHash())
    	    	//.acceptLeeway(1) // // 1 sec for nbf ("nbf" > TODAY), iat ("iat" < TODAY) and exp ("exp" > TODAY)
    	        .build(); //Reusable verifier instance
    	    
    	    verifier.verify(token);
    	    
    	} catch (JWTVerificationException exception){
    	    //Invalid signature/claims
    		return false;
    	}
    	
    	return true;
    }
    
    public String GenerateToken(User user) {
    	
    	String token = "";
    	
    	try {
    	    Algorithm algorithm = Algorithm.HMAC512("secret");
    	    // Expiration (in hours)
    	    //int expiresIn = Integer.parseInt(environment.getRequiredProperty("token.expration"));
    	    //Date expiryDate = DateUtils.addHours(new Date(), expiresIn);
    	    
    	    token = JWT.create()
        	    	.withClaim("userName", user.getUserName())
        	    	.withClaim("barId", user.getBarId())
        	    	.withClaim("passwordHash", user.getPasswordHash())
        	        //.withExpiresAt(expiryDate)
        	        .sign(algorithm);
    	} catch (JWTCreationException exception){
    	    //Invalid Signing configuration / Couldn't convert Claims.
    	}
    	
    	return token;
    }
}

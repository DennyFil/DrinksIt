package ru.drinksit.auxillary;

/**
 * Created by dennyfil on 25.06.16.
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

import ru.drinksit.auxillary.DTO.User;
import ru.drinksit.auxillary.ServiceDTO.UserService;

public class HmacAuthenticationFilter {

	private final Logger logger = LoggerFactory.getLogger("wsAuthenticationLogger");

	@Autowired
	UserService userService;

    private String calculateHMAC(String secret, String data) {
    	
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

    public boolean filterRequest(HttpServletRequest request, String content) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        System.out.println(authHeader);

        if (authHeader == null || authHeader == "") {
            logger.debug("HmacAuthenticationFilter: Authorization header is missing");
            return false;
        }

        try {

        	int idxSeparator = authHeader.indexOf(":");
            String username = authHeader.substring(0, idxSeparator);
            String receivedDigest = authHeader.substring(idxSeparator + 1, authHeader.length());
            
            System.out.println(username);
            System.out.println(receivedDigest);
            
            User user = userService.getUserByUsername(username);
            assert (user != null);

            String dateStr = request.getHeader(HttpHeaders.DATE);
            
            // create signature: method + content + content-type + date + URL
            StringBuilder signature = new StringBuilder();
            signature.append(request.getMethod()).append("\n")
                    .append(content).append("\n")
                    .append(request.getContentType()).append("\n")
                    .append(dateStr).append("\n")
                    .append(request.getRequestURL());
            
            System.out.println(signature.toString());
            
            System.out.println(user.getPasswordHash());

            String hmac = calculateHMAC(user.getPasswordHash(), signature.toString());
            
            System.out.println(hmac);

            if (!hmac.equals(receivedDigest)) {
            	logger.error("HmacAuthenticationFilter: Bad signature");
            	logger.error("HmacAuthenticationFilter: " + username + " is not authorized");
            	return false;
            }

            return true;
        }
        catch (Exception e)
        {        	
        	logger.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }
}

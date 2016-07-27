package ru.drinksit.auxillary;

import ru.drinksit.auxillary.database.Admin;
/**
 * Created by dennyfil on 25.06.16.
 */
import ru.drinksit.auxillary.database.DatabaseException;
import ru.drinksit.auxillary.database.DrinksItDBManager;
import ru.drinksit.auxillary.database.User;

import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.util.Base64Utils;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HmacAuthenticationFilter { //extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger("wsAuthenticationLogger");

    @Autowired
    private DrinksItDBManager drinksItDBManager;

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

    //@Override
    public boolean filterRequest(HttpServletRequest request, String content) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        System.out.println(authHeader);

        if (authHeader == null || authHeader == "") {
            // invalid authorization token
            logger.debug("Authorization header is missing");
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {

        	int idxSeparator = authHeader.indexOf(":");
            String username = authHeader.substring(0, idxSeparator);
            String receivedDigest = authHeader.substring(idxSeparator + 1, authHeader.length());
            
            System.out.println(username);
            System.out.println(receivedDigest);
            
            Admin admin = drinksItDBManager.getAdminByUsername(username);
            assert (admin != null);

            String passwordHash = drinksItDBManager.getAdminPasswordHash(username);
            
            // "Sun, 03 Jul 2016 17:51:50 GMT"
            DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
            
            String dateStr = request.getHeader(HttpHeaders.DATE);
            Date date = df.parse(dateStr);
            
            // create signature: method + content md5 + content-type + date + uri
            StringBuilder signature = new StringBuilder();
            signature.append(request.getMethod()).append("\n")
                    .append(content).append("\n")
                    .append(request.getContentType()).append("\n")
                    .append(df.format(date)).append("\n")
                    .append(request.getRequestURL());
            
            System.out.println(signature.toString());

            String hmac = calculateHMAC(passwordHash, signature.toString());
            
            System.out.println(hmac);

            if (!hmac.equals(receivedDigest)) {
            	logger.debug("HmacAuthenticationFilter.badSignature");
                throw new BadCredentialsException("HmacAuthenticationFilter.badSignature");
            }

            return true;
        }
        catch (Exception e)
        {
        	//logger.error("Failed to get user by username: " + username);
        	logger.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }
}

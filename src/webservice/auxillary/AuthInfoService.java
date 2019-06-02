/**
 * Created by dennyfil on 25.06.16.
 */

package webservice.auxillary;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import webservice.exceptions.CustomAuthenticationException;

public class AuthInfoService {
		
	// Return user information based on received HTTP request
    public AuthInfo getAuthInfo(HttpServletRequest request) throws CustomAuthenticationException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || authHeader == "") {
			throw new CustomAuthenticationException("Empty authentication header");
		}

		try {
			int idxSeparator = authHeader.indexOf(":");

			String userName = authHeader.substring(0, idxSeparator);
			String token = authHeader.substring(idxSeparator + 1, authHeader.length());

			return new AuthInfo(userName, token);
		}
		catch (Exception e)
		{
			throw new CustomAuthenticationException("Failed to parse authentication header");
		}
	}
}

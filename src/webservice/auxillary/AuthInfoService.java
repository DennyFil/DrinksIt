/**
 * Created by dennyfil on 25.06.16.
 */

package webservice.auxillary;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import webservice.auxillary.DTO.User;
import webservice.auxillary.ServiceDAO.UserService;
import webservice.exceptions.CustomAuthenticationException;

public class AuthInfoService {
	
	@Autowired
	private UserService userService;
	
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

			// Get user with userName
			User user = userService.GetUser(userName);

			if (user == null)
				throw new Exception("Unable to get auth info of non-existent user");

			return new AuthInfo(userName, token, user.getId());
		}
		catch (Exception e)
		{
			throw new CustomAuthenticationException("Failed to parse authentication header");
		}
	}
}

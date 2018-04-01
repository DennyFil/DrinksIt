package webservice.auxillary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import webservice.auxillary.DTO.User;
import webservice.auxillary.ServiceDTO.IUserService;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IUserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public Authentication authenticate(Authentication authentication) 
			throws AuthenticationException {

		String userName = String.valueOf(authentication.getPrincipal());
	    String token = String.valueOf(authentication.getCredentials());
		
		// Check user exists and token is valid based authentication info
		try {
			User user = userService.GetUser(userName);
	        
	        // Check token validity
	        if (tokenService.CheckToken(token, user)) {
	        	logger.info("Succesful authentication!");
				return new PreAuthenticatedAuthenticationToken(userName, token, authentication.getAuthorities());
	        }
			else {
				logger.info("Invalid token!");
			}
		} catch (Exception e) {
			logger.info(e.getStackTrace().toString());
		}

		throw new BadCredentialsException("Invalid userName or token");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(PreAuthenticatedAuthenticationToken.class);
	}
}

package webservice.auxillary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import webservice.auxillary.DTO.User;
import webservice.auxillary.ServiceDAO.IUserService;

public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IUserService userService;

	@Autowired
	TokenService tokenService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String userName = String.valueOf(authentication.getPrincipal());
	    String password = String.valueOf(authentication.getCredentials());
	    
		// Check user exists and password matches
		try {
			User user = userService.GetUser(userName);
			String receivedPasswordHash = HashComputor.ComputeSHA256(password);

			if (receivedPasswordHash.equals(user.getPasswordHash()))
			{
				// Generate token
				String token = tokenService.GenerateToken(user);
				
				return new PreAuthenticatedAuthenticationToken(user, token, authentication.getAuthorities());
			}
			else {
				logger.info("Invalid password!");
			}
		}
		catch (Exception e)
		{
			logger.info(e.getStackTrace().toString());
		}
		
		throw new BadCredentialsException("Invalid userName or password");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

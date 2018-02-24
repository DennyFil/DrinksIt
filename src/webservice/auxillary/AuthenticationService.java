package webservice.auxillary;

/**
 * Created by dennyfil on 25.06.16.
 */

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import webservice.auxillary.DTO.User;
import webservice.auxillary.ServiceDTO.UserService;

public class AuthenticationService {

	private final Logger logger = LoggerFactory.getLogger("wsAuthenticationLogger");

	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;

    public boolean IsAuthorized(AuthInfo authInfo) {

        try {
        	User user = userService.GetUser(authInfo.userName);
            assert (user != null);
            
            // Check token validity
            if (tokenService.CheckToken(authInfo.getToken(), user) == false) {
            	logger.error("AuthenticationService: token no valid");
            	logger.error("AuthenticationService: " + authInfo.userName + " is not authorized");
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

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

    public boolean IsAuthorized(UserInfo userInfo) {

        try {
        	User user = userService.GetUser(userInfo.userName);
            assert (user != null);
            
            if (!userInfo.passwordHash.equals(user.getPasswordHash())) {
            	logger.error("AuthenticationService: wrong password");
            	logger.error("AuthenticationService: " + userInfo.userName + " is not authorized");
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

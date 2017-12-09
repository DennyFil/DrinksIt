package webservice.auxillary;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import webservice.auxillary.DTO.User;
import webservice.auxillary.ServiceDTO.UserService;

public class AccessRightsService {

	private final Logger logger = LoggerFactory.getLogger("wsAccessRightsLogger");
	
	@Autowired
	UserService userService;
	
	public boolean checkRight(UserInfo userInfo, String right) {

        try {
            User user = userService.getUserByUsername(userInfo.userName);
            assert (user != null);            
                      
            if (!user.getIsAdmin()) {
            	logger.error("AccessRightService: not authorized");
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

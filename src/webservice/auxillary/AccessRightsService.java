package webservice.auxillary;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import webservice.auxillary.DTO.User;
import webservice.auxillary.ServiceDAO.IUserService;

public class AccessRightsService {

	private final Logger logger = LoggerFactory.getLogger("DrinksItLoggerConsole");
	
	@Autowired
	IUserService userService;
	
	public boolean checkRight(AuthInfo userInfo, String right) {

        try {
            User user = userService.GetUser(userInfo.getUserName());
            assert (user != null);
                      
            if (!user.isAdmin()) {
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
	
	public boolean isBarAdmin(AuthInfo userInfo, int barId){
		try {
            User user = userService.GetUser(userInfo.getUserName());
            assert (user != null);
                      
            if (!user.isBarAdmin(barId)) {
            	logger.error("AccessRightService: not bar admin");
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

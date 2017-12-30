package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthenticationService;
import webservice.auxillary.ServiceDTO.UserService;
import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.User;
import webservice.auxillary.DTO.UserInfo;

@RestController
public class LoginController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("loginControllerLogger");

	@Autowired
	UserService userService;

	@RequestMapping("/login")
	public ResponseEntity<UserInfo> login(HttpServletRequest request) throws Exception
	{
		logger.debug("POST /login");

		AuthInfo authInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(authInfo))
		{
			logger.debug("POST /login: login failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try {
			User user = userService.GetUser(authInfo.getUserName());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserInfo(user));
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}

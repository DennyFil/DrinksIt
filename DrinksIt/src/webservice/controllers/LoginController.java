package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthenticationService;
import webservice.auxillary.ServiceDTO.UserService;

@RestController
public class LoginController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("loginControllerLogger");

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationService authService;
	
	@RequestMapping("/login")
	public ResponseEntity<Boolean> login(HttpServletRequest request) throws Exception
	{
		logger.debug("POST /login");
		
		if (! authService.IsAuthorized(getUserInfo(request)))
		{
			logger.debug("POST /login: login failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
	}
}

package webservice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthenticationService;
import webservice.auxillary.UserInfo;
import webservice.auxillary.ServiceDTO.UserService;
import webservice.auxillary.DTO.User;

@RestController
public class UserController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("userControllerLogger");

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationService authService;

	@RequestMapping("/users")
	public ResponseEntity<List<User>> GetUsers(HttpServletRequest request, @RequestParam Integer barId) throws Exception {

		logger.debug("GET /users for bar " + barId);

		if (! authService.IsAuthorized(getUserInfo(request)))
		{
			logger.debug("GET /users: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<User> users = userService.getListOfUsers(barId);
			
		return ResponseEntity.ok(users);
	}
	
	@RequestMapping("/postUser")
	public ResponseEntity<User> PostUser(HttpServletRequest request, @RequestParam String userName, @RequestParam String passwordHash, @RequestParam Integer barId) throws Exception {

		logger.debug("POST /postUser: " + userName + " for bar " + barId);
		
		UserInfo userInfo = getUserInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("POST /postUser: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if ( ! arService.checkRight(userInfo, "create"))
		{
			logger.debug("POST /postUser: no create right");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		User user = userService.createUser(userName, passwordHash, barId);
			
		return ResponseEntity.ok(user);
	}
}

package webservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.ServiceDTO.UserService;
import webservice.auxillary.DTO.User;
import webservice.auxillary.DTO.UserInfo;

@RestController
public class UserController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("userControllerLogger");

	@Autowired
	UserService userService;

	@RequestMapping("/users")
	public ResponseEntity<List<UserInfo>> GetUsers(HttpServletRequest request) throws Exception {

		logger.debug("GET /users");

		if (! authService.IsAuthorized(getAuthInfo(request)))
		{
			logger.debug("GET /users: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<User> users = userService.GetUsers();

		List<UserInfo> userToList = users.stream()
				.filter(elt -> elt != null)
				.map(elt -> new UserInfo(elt))
				.collect(Collectors.toList());

		return ResponseEntity.ok(userToList);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/postUser")
	public ResponseEntity<User> PostUser(HttpServletRequest request, @RequestBody User newUser) throws Exception {

		logger.debug("POST /postUser: " + newUser.getUserName() + " for bar " + newUser.getBarId());

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("POST /postUser: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		if ( ! arService.checkRight(userInfo, "create"))
		{
			logger.debug("POST /postUser: no create right");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		User user = userService.CreateUser(newUser);

		return user != null? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}

package ru.drinksit.controllers;

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

import ru.drinksit.auxillary.HmacAuthenticationFilter;
import ru.drinksit.auxillary.ServiceDTO.UserService;
import ru.drinksit.auxillary.DTO.User;

@RestController
public class UserController {

	private static final Logger logger = 
			LoggerFactory.getLogger("userControllerLogger");

	@Autowired
	UserService userService;

	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;

	@RequestMapping("/users")
	public ResponseEntity<List<User>> getUsers(HttpServletRequest request, @RequestParam Integer barId) {

		logger.debug("GET /users for bar " + barId);
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("barId", new Integer(barId).toString());

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /users: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<User> users = userService.getListOfUsers(barId);
			
		return ResponseEntity.ok(users);
	}
	
	@RequestMapping("/postUser")
	public ResponseEntity<User> createUser(HttpServletRequest request, @RequestParam String userName, @RequestParam String passwordHash, @RequestParam Integer barId) {

		logger.debug("POST /postUser: " + userName + " for bar " + barId);
		JSONObject contentJson = new JSONObject();
		contentJson.put("userName", userName);
		contentJson.put("passwordHash", passwordHash);
		contentJson.put("barId", new Integer(barId).toString());

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("POST /postUser: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		User user = userService.createUser(userName, passwordHash, barId);
			
		return ResponseEntity.ok(user);
	}
}

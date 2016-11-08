package ru.drinksit.controllers;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.drinksit.auxillary.HmacAuthenticationFilter;
import ru.drinksit.auxillary.ServiceDTO.UserService;

@RestController
public class LoginController {

	private static final Logger logger = 
			LoggerFactory.getLogger("loginControllerLogger");

	@Autowired
	UserService userService;

	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;
	
	@RequestMapping("/checkCreds")
	public ResponseEntity<Boolean> checkCreds(HttpServletRequest request)
	{
		logger.debug("POST /checkCreds");
		
		JSONObject contentJson = new JSONObject();

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("POST /checkCreds: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
	}
}

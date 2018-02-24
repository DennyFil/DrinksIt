package webservice.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.ServiceDTO.UserService;
import webservice.auxillary.HashComputor;
import webservice.auxillary.TokenService;
import webservice.auxillary.DTO.User;
import webservice.auxillary.DTO.UserInfo;

@RestController
public class LoginController extends BaseController {

	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;

	// Checks login credentials, generates and returns token
	@RequestMapping("/login")
	public ResponseEntity login(HttpServletRequest request, @RequestBody User userPosted) throws Exception
	{
		String errorMsg = "";
		logger.debug("POST /login");
		// Check user exists and password matches
		try {
			User user = userService.GetUser(userPosted.getUserName());
			String receivedPasswordHash = HashComputor.ComputeSHA256(userPosted.getPassword());

			if (user != null && receivedPasswordHash.equals(user.getPasswordHash()))
			{
				// Generate token
				String token = tokenService.GenerateToken(user);
				UserInfo userInfo = new UserInfo(user, token);
				return ResponseEntity.ok(userInfo);
			}
			else {
				errorMsg = "Wrong username or password";
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}

		logger.debug("POST /login: login failed");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMsg);
	}
}

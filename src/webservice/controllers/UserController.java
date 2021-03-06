package webservice.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.User;
import webservice.auxillary.DTO.UserInfo;
import webservice.auxillary.ServiceDAO.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController extends GenController<User> {

	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	public ResponseEntity<?> GetUsers(HttpServletRequest request) throws Exception {

		try {
			// Remap to userInfo		
			List<UserInfo> usersInfo = new ArrayList<UserInfo>();
			
			for (User user : userService.FindAll()){
				usersInfo.add(new UserInfo(user));
			} 
			
			return ResponseEntity.ok(usersInfo);
		}
		catch (Exception e) {
			loggerConsole.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get users");
	}
	
	@Override
	protected boolean itemExists(User newUser) throws Exception {
		return userService.Exists(newUser);
	}

	@Override
	// Check for user if the userName is not already in use
	protected User updateItem(User newUser) throws Exception {
		
		User userSameName = userService.GetUser(newUser.getUserName());
		if (userSameName == null) {
			userService.Update(newUser);
			return newUser;
		}
		else {
			throw new Exception("Other user with same userName exists");
		}
	}
	
	@Override
	protected void deleteItem(int id) throws Exception {
		userService.DeleteById(id);
	}

	@Override
	// Check for new user if the userName is not already in use
	protected User createItem(User newUser) throws Exception {
		
		User userSameName = userService.GetUser(newUser.getUserName());
		if (userSameName == null) {
			return userService.Create(newUser);
		}
		else {
			throw new Exception("Other user with same userName exists");
		}
	}

	@Override
	protected String getTypeStr() {
		return "user";
	}

	@Override
	protected boolean hasPostRight(AuthInfo userInfo, User newItem) {
		return arService.checkRight(userInfo, "create");
	}
}

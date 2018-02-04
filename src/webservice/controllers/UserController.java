package webservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.ServiceDTO.UserService;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.User;

@RestController
@RequestMapping("/users")
public class UserController extends GenController<User> {

	@Autowired
	UserService userService;

	@RequestMapping("/list")
	public ResponseEntity<List<User>> GetUsers(HttpServletRequest request) throws Exception {

		return list(request);		
	}
	
	@Override
	protected boolean itemExists(User newUser) throws Exception {
		return userService.Exists(newUser);
	}

	@Override
	protected User updateItem(User newUser) throws Exception {
		userService.Update(newUser);
		return newUser;
	}

	@Override
	protected User createItem(User newUser) throws Exception {
		return userService.Create(newUser);
	}

	@Override
	protected String getPostLog(User user) {
		return "CREATION: user " + user.getUserName() + " for bar " + user.getBarId();
	}

	@Override
	protected boolean hasPostRight(AuthInfo userInfo, User newItem) {
		return arService.checkRight(userInfo, "create");
	}

	@Override
	protected ResponseEntity<List<User>> getListItems(AuthInfo userInfo) throws Exception {
		
		List<User> users = userService.GetUsers();

		return ResponseEntity.ok(users);
	}
}

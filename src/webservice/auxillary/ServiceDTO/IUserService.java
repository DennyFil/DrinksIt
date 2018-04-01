package webservice.auxillary.ServiceDTO;

import java.util.List;

import webservice.auxillary.DTO.User;

public interface IUserService extends IGenDao<User> {
	
	List<User> GetUsers() throws Exception;

	String GetUserPasswordHash(String userName) throws Exception;
	
	User GetUser(String userName) throws Exception;
	
	User Create(User newUser) throws Exception;
	
	void Update(User user) throws Exception;
	
	boolean Exists(User newUser) throws Exception;
}

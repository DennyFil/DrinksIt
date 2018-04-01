package webservice.auxillary.ServiceDAO;

import webservice.auxillary.DTO.User;

public interface IUserService extends IGenDao<User> {
	
	String GetUserPasswordHash(String userName) throws Exception;
	
	User GetUser(String userName) throws Exception;
}

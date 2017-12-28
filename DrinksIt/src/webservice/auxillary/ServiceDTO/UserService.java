package webservice.auxillary.ServiceDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.HashComputor;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.DTO.User;

@Service("UserService")
@Transactional
public class UserService {

	private static final Logger logger = 
			LoggerFactory.getLogger("userServiceLogger");
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public UserService() {
	}
	
	@SuppressWarnings({"rawtypes", "finally"})
	public List<User> GetUsers()
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			List<User> results = (List<User>)session.createQuery("FROM User u JOIN FETCH u.bar").list();

			logger.debug("RETURNED: List of users");
			return results;
		}catch (Exception e) {
			logger.error("Failed to get of users");
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return new ArrayList<User>();
	}

	public String GetUserPasswordHash(String userName)
	{
		try{			
			Session session = sessionFactory.getCurrentSession();

			User user = (User) session.get(User.class, userName);
			if (user != null) {

				logger.debug("RETURNED password hash of " + userName);
				return user.getPasswordHash();
			}
			else {
				logger.debug("User: " + userName + " does not exist");
			}
		}catch (Exception e) {
			logger.error("Failed to get password hash of " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
	
	public User GetUser(String userName)
	{
		try{			
			Session session = sessionFactory.getCurrentSession();

			User user = (User) session.get(User.class, userName);

			logger.debug("RETURNED user " + userName);
			return user;
		}catch (Exception e) {
			logger.error("Failed to retrieve user " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
	
	public User CreateUser(User newUser)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
			
			Bar bar = (Bar)session.get(Bar.class, newUser.getBarId());
			
			if (bar != null) {
				
				newUser.setPasswordHash(HashComputor.ComputeSHA256(newUser.getPassword()));
				newUser.setBar(bar);
				
				session.save(newUser);
	
				logger.debug("CREATION: user " + newUser.getUserName() + " for bar " + newUser.getBarId());
				return newUser;
			}
			else {
				logger.debug("Bar: " + newUser.getBarId() + " does not exist");
			}
		}
		catch (Exception e) {
			logger.error("Failed to create user " + newUser.getUserName() + " for bar " + newUser.getBarId());
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
	
	public User CreateUser(String userName, String password, int barId, boolean isAdmin)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
			
			Bar bar = (Bar)session.get(Bar.class, barId);
			
			if (bar != null) {
				
				User user = new User(userName, HashComputor.ComputeSHA256(password), barId, isAdmin);
	
				session.save(user);
	
				logger.debug("CREATION: user " + userName + " for bar " + barId);
				return user;
			}
			else {
				logger.debug("Bar: " + barId + " does not exist");
			}
		}
		catch (Exception e) {
			logger.error("Failed to create user " + userName + " for bar " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
}

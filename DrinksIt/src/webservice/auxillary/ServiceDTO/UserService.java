package webservice.auxillary.ServiceDTO;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.DTO.Bar;
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
	public List<User> getListOfUsers(int barId)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			String query = "FROM User";
			if (barId > 0)
			{
				query += " WHERE bar.bar_id = '" + barId + "'";
			}
			List<User> results = (List<User>)session.createQuery(query).list();

			logger.debug("RETURNED: List of users for bar: " + barId + "");
			return results;
		}catch (Exception e) {
			logger.error("Failed to get of users for bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}

	public String getUserPasswordHash(String userName)
	{
		try{			
			Session session = sessionFactory.getCurrentSession();

			User user = (User) session.get(User.class, userName);

			logger.debug("RETURNED password hash of " + userName);
			return user.getPasswordHash();
		}catch (Exception e) {
			logger.error("Failed to get password hash of " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
	
	public User getUserByUsername(String userName)
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
	
	public User createUser(String userName, String passwordHash, int barId)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
			
			Bar bar = (Bar)session.get(Bar.class, barId);
			
			User user = new User(userName, passwordHash, bar);

			session.save(user);

			logger.debug("CREATION: user " + userName + " for bar " + barId);
			return user;
		}
		catch (Exception e) {
			logger.error("Failed to create user " + userName + " for bar " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
}

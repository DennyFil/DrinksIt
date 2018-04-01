package webservice.auxillary.ServiceDTO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.HashComputor;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.DTO.User;

@Service("UserService")
@Transactional
public class UserService extends GenDao<User> implements IUserService {

	public UserService() {
		this.setGenericType(User.class);
	}

	@SuppressWarnings("unchecked")
	public List<User> GetUsers() throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		return (List<User>)session.createQuery("FROM User u JOIN FETCH u.bar").list();
	}

	public String GetUserPasswordHash(String userName) throws Exception
	{			
		Session session = sessionFactory.getCurrentSession();

		User user = (User) session.get(User.class, userName);
		if (user != null) {

			return user.getPasswordHash();
		}
		else {
			throw new Exception("Get password hash failed: user " + userName + " does not exist");
		}
	}

	public User GetUser(String userName) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		return (User) session.get(User.class, userName);
	}

	public User Create(User newUser) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		Bar bar = (Bar)session.get(Bar.class, newUser.getBarId());

		if (bar != null) {

			newUser.setPasswordHash(HashComputor.ComputeSHA256(newUser.getPassword()));
			newUser.setBar(bar);

			session.save(newUser);

			return newUser;
		}
		else {
			throw new Exception("CREATE user failed: bar " + newUser.getBarId() + " does not exist");
		}
	}
	
	public void Update(User user) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();
		
		Bar bar = (Bar)session.get(Bar.class, user.getBarId());		
		
		if (bar != null) {

			user.setPasswordHash(HashComputor.ComputeSHA256(user.getPassword()));
			user.setBar(bar);

			session.update(user);
		}
		else {
			throw new Exception("UPDATE user failed: bar " + user.getBarId() + " does not exist");
		}
	}
	
	public boolean Exists(User newUser) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		User user = (User) session.get(User.class, newUser.getIdStr());
		
		return user != null;
	}
}

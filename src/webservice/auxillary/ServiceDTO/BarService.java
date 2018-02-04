package webservice.auxillary.ServiceDTO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.User;

@Service("BarService")
@Transactional
public class BarService {
	
	public BarService() {
	}

	@Autowired
	public SessionFactory sessionFactory;

	public Bar GetBar(String userName) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		User user = (User) session.get(User.class, userName);
		if (user != null)
		{
			return user.getBar();
		}
		else {
			throw new Exception("User: " + userName + " does not exist");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Bar> GetBars() throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		return (List<Bar>)session.createQuery("FROM Bar").list();
	}

	public Bar Create(Bar newBar) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		session.save(newBar);

		return newBar;
	}
	
	public void Update(Bar bar) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		session.update(bar);
	}
	
	public boolean Exists(Bar newBar) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		Bar bar = (Bar) session.get(Bar.class, newBar.getId());
		
		return bar != null;
	}
}

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

import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.DTO.User;

@Service("BarService")
@Transactional
public class BarService {

	private static final Logger logger = 
			LoggerFactory.getLogger("barServiceLogger");
	
	public BarService() {
	}
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public Bar GetBar(String userName)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			User user = (User) session.get(User.class, userName);
			if (user != null)
			{
				logger.debug("RETURNED: bar of user: " + userName);
				return user.getBar();
			}
			else {
				logger.debug("User: " + userName + " does not exist");
			}				
		}
		catch (Exception e) {
			logger.error("Failed to get bar for user " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}

	public List<Bar> GetBars()
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			List<Bar> bars = (List<Bar>)session.createQuery("FROM Bar").list();
			
			logger.debug("RETURNED: List of bars");
			return bars;
		}catch (Exception e) {
			logger.error("Failed to get list of bars");
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return new ArrayList<Bar>();
	}

	public Bar CreateBar(Bar newBar)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			session.save(newBar);

			logger.debug("CREATION: bar: " + newBar.getName() + ", " + newBar.getAddress() + ", " + newBar.getCity() + ", " + newBar.getCountry());
			return newBar;
		}
		catch (Exception e) {
			logger.error("Failed to create bar: " + newBar.getName() + ", " + newBar.getAddress() + ", " + newBar.getCity() + ", " + newBar.getCountry());
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
	
	public Bar CreateBar(String name, String address, String city, String country)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

            Bar bar = new Bar(address, city, country, name);

			session.save(bar);

			logger.debug("CREATION: bar: " + name + ", " + address + ", " + city + ", " + country);
			return bar;
		}
		catch (Exception e) {
			logger.error("Failed to create bar: " + name + ", " + address + ", " + city + ", " + country);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
}

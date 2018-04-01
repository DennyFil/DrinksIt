package webservice.auxillary.ServiceDAO;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.User;

@Service("BarService")
@Transactional
public class BarService extends GenDao<Bar> implements IBarService {

	public BarService() {
		this.setGenericType(Bar.class);
	}

	public Bar GetBar(String userName) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		User user = (User) session.byNaturalId(User.class)
			.using("userName", userName)
        	.load();
		
		if (user != null)
		{
			return user.getBar();
		}
		else {
			throw new Exception("User: " + userName + " does not exist");
		}
	}
}

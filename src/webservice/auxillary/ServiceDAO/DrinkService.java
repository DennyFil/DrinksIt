package webservice.auxillary.ServiceDAO;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.Drink;

@Service("DrinkService")
@Transactional
public class DrinkService extends GenDao<Drink> {

	public DrinkService() {
		super(Drink.class);
	}

	public boolean CheckDrink(int drinkId, int barId, String drinkName, double drinkSize, double drinkPrice) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();
		Drink drink = (Drink) session.get(Drink.class, drinkId);

		return drink != null
			&& drink.getBarId() == barId
			&& drink.getName().equals(drinkName)
			&& drink.getSize() == drinkSize
			&& drink.getPrice() == drinkPrice;
	}

	@SuppressWarnings({"unchecked"})
	public List<Drink> GetDrinks(int barId) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		String query = "FROM Drink";
		if (barId > 1) // 1 is Master Bar for which all drinks listed
		{
			query += " WHERE bar_id = '" + barId + "'";
		}

		return (List<Drink>)session.createQuery(query).list();
	}

	public Drink Create(Drink newDrink) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		Bar bar = session.get(Bar.class, newDrink.getBarId());

		if (bar != null) {

			newDrink.setBar(bar);
			session.save(newDrink);

			return newDrink;
		}
		else {
			throw new Exception("CREATE drink failed: bar " + newDrink.getBarId() + " not available");
		}
	}
	
	public void Update(Drink drink) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();
		
		Bar bar = session.get(Bar.class, drink.getBarId());

		if (bar != null) {

			drink.setBar(bar);
			session.update(drink);
		}
		else {
			throw new Exception("UPDATE drink failed: bar " + drink.getBarId() + " not available");
		}
	}
}

package ru.drinksit.auxillary.ServiceDTO;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.drinksit.auxillary.DTO.Bar;
import ru.drinksit.auxillary.DTO.Drink;
import ru.drinksit.auxillary.DTO.User;

@Service("DrinkService")
@Transactional
public class DrinkService {
	
	private static final Logger logger = 
			LoggerFactory.getLogger("drinkServiceLogger");
	
	public DrinkService() {
	}
	
	@Autowired
	public SessionFactory sessionFactory;
	
	@SuppressWarnings("finally")
	public Drink checkDrink(int drinkId, int barId, String drinkName, double drinkSize, double drinkPrice)
	{
		try
		{
			Session session = sessionFactory.getCurrentSession();
			Drink drink = (Drink) session.get(Drink.class, drinkId);

			if (drink.getBar().getBar_id() == barId
					&& drink.getName().equals(drinkName)
					&& drink.getSize() == drinkSize
					&& drink.getPrice() == drinkPrice)
			{
				logger.debug("CHECK: drink: " + drinkId + " (name: " + drinkName + ", size: " + drinkSize + ", price: " + drinkPrice + ") in bar: " + barId);
				
				return drink;
			}
			else
			{
				logger.debug("NOT AVAILABLE: drink: " + drinkId + " (name: " + drinkName + ", size: " + drinkSize + ", price: " + drinkPrice + ") in bar: " + barId);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed to check drink: " + drinkId + " (name: " + drinkName + ", size: " + drinkSize + ", price: " + drinkPrice + ") in bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		finally
		{
			
		}
		
		return null;
	}

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Drink> getListOfDrinks(int barId)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			String query = "FROM Drink";
			if (barId > 0)
			{
				query += " WHERE bar.bar_id = '" + barId + "'";
			}
			List<Drink> drinks = (List<Drink>)session.createQuery(query).list();

			logger.debug("RETURNED: List of drinks for bar: " + barId + "");
			return drinks;
		}catch (Exception e) {
			logger.error("Failed to get drinks at bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}	

	public Drink getDrinkByUser(int drinkId, String userName)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			User user = (User) session.get(User.class, userName);

			int barId = user.getBar().getBar_id();
			Drink drink = (Drink) session.get(Drink.class, drinkId);

			if (drink.getBar().getBar_id() == barId)
			{
				logger.debug("RETURNED drink " + drinkId + " for " + userName);
				return drink;
			}
			else
			{
				logger.error("No drink " + drinkId + " available for " + userName);
			}
		}catch (Exception e) {
			logger.error("Failed to get drink " + drinkId + " for " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}

    public Drink createDrink(String name, double price, double size, int barId)
    {
        try{
            Session session = sessionFactory.getCurrentSession();

            Bar bar = session.get(Bar.class, barId);

            Drink drink = new Drink(bar, name, price, size);

            session.save(drink);

            logger.debug("CREATION: drink: " + name + ", size: " + size + ", price: " + price + ", bar: " + barId);
            return drink;
        }
        catch (Exception e) {
            logger.error("Failed to create drink: " + name + ", size: " + size + ", price: " + price + ", bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
        }finally {

        }
        
        return null;
    }
}

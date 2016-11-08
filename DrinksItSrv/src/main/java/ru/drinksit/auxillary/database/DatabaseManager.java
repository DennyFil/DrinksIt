package ru.drinksit.auxillary.database;

//import java.util.Date;
//import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import ru.drinksit.auxillary.DTO.*;

@Service("DatabaseManager")
@Transactional
public class DatabaseManager {

	private static final Logger logger = 
			LoggerFactory.getLogger("databaseManagerLogger");
	
	@Autowired
	public SessionFactory sessionFactory;

	public DatabaseManager() {
	}

	/*public Payment createPayment(int orderId)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			List<Payment> payments = (List<Payment>)session.createQuery("FROM Payment p WHERE p.order.order_id = " + orderId).list();

			Payment payment = null;
			if (payments.size() > 0)
			{
				payment = payments.get(0);
				
				if (payment != null)
				{
					return payment;
				}
			}

			Order order = (Order)session.get(Order.class, orderId);
			double amount = order.getDrink().getPrice() * order.getQuantity();

			payment = new Payment(amount, order);

			session.save(payment);

			return payment;
		}
		catch (Exception e) {
			throw new DatabaseException("Failed to create payment for order " + orderId);
		}finally {

		}
	}*/
	
	public boolean truncateTable(String tableName)
	{
		String query = "delete from " + tableName;
		try{
			Session session = sessionFactory.getCurrentSession();

			session.createQuery(query).executeUpdate();

			logger.debug(query);
			return true;
		}
		catch (Exception e) {
			logger.error("FAILED: " + query);
		}finally {

		}
		
		return false;
	}
	
	public boolean resetAutoIncr(String tableName)
	{
		String query = "ALTER TABLE " + tableName + " AUTO_INCREMENT=1";
		try{
			Session session = sessionFactory.getCurrentSession();

			
			session.createSQLQuery(query).executeUpdate();

			logger.debug(query);
			return true;
		}
		catch (Exception e) {
			logger.error("FAILED: " + query);
		}finally {

		}
		
		return false;
	}
}

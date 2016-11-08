package ru.drinksit.auxillary.ServiceDTO;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.drinksit.auxillary.DTO.Drink;
import ru.drinksit.auxillary.DTO.Order;
import ru.drinksit.auxillary.DTO.User;

@Service("OrderService")
@Transactional
public class OrderService {

	private static final Logger logger = 
			LoggerFactory.getLogger("orderServiceLogger");
	
	public OrderService() {
	}
	
	@Autowired
	public SessionFactory sessionFactory;	

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Order> getListOfOrders(String userName)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			User user = (User) session.get(User.class, userName);
			int barId = user.getBar().getBar_id();

			List<Order> results = (List<Order>)session.createQuery("FROM Order o JOIN FETCH o.drink d WHERE d.bar.bar_id = '" + barId + "'").list(); //JOIN User u JOIN FETCH u.bar ub WHERE ub.bar_id = db.bar_id AND u.userName = " + userName).list();

			logger.debug("RETURNED: list of orders for user " + userName);
			return results;
		}catch (Exception e) {
			logger.error("Failed to get list of order for " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Order> getListOfOrdersAll()
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			List<Order> results = (List<Order>)session.createQuery("FROM Order").list(); //JOIN User u JOIN FETCH u.bar ub WHERE ub.bar_id = db.bar_id AND u.userName = " + userName).list();

			logger.debug("RETURNED: list of all orders");
			return results;
		}catch (Exception e) {
			logger.error("Failed to get list of orders");
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}

	public boolean updateOrderStatus(int orderId, String status)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			Order order = (Order)session.get(Order.class, orderId); 
			order.setStatus(status);
			order.setUpdateTS(new Date());
			session.update(order);
			
			logger.debug("STATUS UPDATED: order " + orderId + " to " + status);
			return true;
		}catch (Exception e) {
			logger.error("Failed to update status of order " + orderId + " to " + status);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return false;
	}

	public Order createOrder(int drinkId, int quantity, String status)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			Drink drink = session.get(Drink.class, drinkId);

			Order order = new Order(drink, quantity, status, new Date().toString());

			session.save(order);

			logger.debug("CREATION: order for drink: " + drinkId + " with quantity: " + quantity);
			return order;
		}
		catch (Exception e) {
			logger.error("Failed to create order for drink: " + drinkId + " with quantity: " + quantity);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}
		
		return null;
	}
}

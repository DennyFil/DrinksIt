package webservice.auxillary.ServiceDTO;

import java.util.ArrayList;
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

import webservice.auxillary.DTO.Drink;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.DTO.User;

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
	public Order GetOrder(int orderId)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			Order order = (Order) session.get(Order.class, orderId);

			logger.debug("RETURNED: order " + orderId);
			return order;
		}catch (Exception e) {
			logger.error("Failed to get orders " + orderId);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}

		return null;
	}

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Order> GetOrders()
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

		return new ArrayList<Order>();
	}

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Order> GetOrders(String userName)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			User user = (User) session.get(User.class, userName);

			if (user != null) {
				int barId = user.getBarId();

				List<Order> results = (List<Order>)session.createQuery("FROM Order o JOIN FETCH o.drink d WHERE d.bar_id = '" + barId + "'").list(); //JOIN User u JOIN FETCH u.bar ub WHERE ub.id = db.id AND u.userName = " + userName).list();

				logger.debug("RETURNED: list of orders for user " + userName);
				return results;
			}
			else {
				logger.debug("User: " + userName + " does not exist");
			}
		}catch (Exception e) {
			logger.error("Failed to get list of order for " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}

		return new ArrayList<Order>();
	}

	public boolean UpdateOrder(Order order)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
			
			session.update(order);				
			logger.debug("UPDATED: order " + order.getId());
			return true;
		}catch (Exception e) {
			logger.error("Failed to update order " + order.getId());
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}

		return false;
	}

	public Order CreateOrder(int drinkId, int quantity, String status)
	{
		try{
			Session session = sessionFactory.getCurrentSession();

			Drink drink = session.get(Drink.class, drinkId);

			if (drink != null) {

				Order order = new Order(drinkId, quantity, status, new Date());

				session.save(order);

				logger.debug("CREATION: order for drink: " + drinkId + " with quantity: " + quantity);
				return order;
			}
			else {
				logger.debug("Drink: " + drinkId + " does not exist");
			}
		}
		catch (Exception e) {
			logger.error("Failed to create order for drink: " + drinkId + " with quantity: " + quantity);
			logger.error(ExceptionUtils.getStackTrace(e));
		}finally {

		}

		return null;
	}
}

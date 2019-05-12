package webservice.auxillary.ServiceDAO;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.DTO.Drink;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.DTO.User;

@Service("OrderService")
@Transactional
public class OrderService extends GenDao<Order> {

	public OrderService() {
		super(Order.class);
	}

	public Order GetOrder(int orderId) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		return (Order) session.get(Order.class, orderId);
	}

	@SuppressWarnings({"unchecked"})
	public List<Order> GetOrders(String userName) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		User user = (User) session.byNaturalId(User.class).using("userName", userName).load();

		if (user != null) {
			int barId = user.getBarId();

			return (List<Order>)session.createQuery("FROM Order o JOIN FETCH o.drink d WHERE d.barId = '" + barId + "'").list();
		}
		else {
			throw new Exception("GET ORDERS failed: user " + userName + " does not exist");
		}
	}

	public Order CreateOrder(int drinkId, int quantity, String status) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		Drink drink = session.get(Drink.class, drinkId);

		if (drink != null) {

			Order order = new Order(drinkId, quantity, status, new Date());
			order.setDrink(drink);
			
			session.save(order);

			return order;
		}
		else {
			throw new Exception("CREATE ORDER failed: drink " + drinkId + " does not exist");
		}
	}
}

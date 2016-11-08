package ru.drinksit.auxillary.database;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("drinksItDBManager")
@Transactional
public class DrinksItDBManager {

	@Autowired
	public SessionFactory sessionFactory;

	public DrinksItDBManager() {
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<Bar> getListOfBars()
			throws DatabaseException
	{
		try{
			Session session = getSession();

			List<Bar> bars = (List<Bar>)session.createQuery("FROM Bar").list();

			return bars;
		}catch (Exception e) {
			throw new DatabaseException("Failed to get list of bars");
		}finally {

		}
	}

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Order> getListOfOrders(String userName)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			User user = (User) session.get(User.class, userName);
			int barId = user.getBar().getBar_id();

			List<Order> results = (List<Order>)session.createQuery("FROM Order o JOIN FETCH o.drink d WHERE d.bar.bar_id = '" + barId + "'").list(); //JOIN User u JOIN FETCH u.bar ub WHERE ub.bar_id = db.bar_id AND u.userName = " + userName).list();

			return results;
		}catch (Exception e) {
			throw new DatabaseException("Failed to get list of order for " + userName);
		}finally {

		}
	}

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Order> getListOfOrdersAll()
			throws DatabaseException
	{
		try{
			Session session = getSession();

			List<Order> results = (List<Order>)session.createQuery("FROM Order").list(); //JOIN User u JOIN FETCH u.bar ub WHERE ub.bar_id = db.bar_id AND u.userName = " + userName).list();

			return results;
		}catch (Exception e) {
			throw new DatabaseException("Failed to get list of orders");
		}finally {

		}
	}
	
	@SuppressWarnings({"rawtypes", "finally"})
	public List<User> getListOfUsers(int barId)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			String query = "FROM User";
			if (barId > 0)
			{
				query += " WHERE bar.bar_id = '" + barId + "'";
			}
			List<User> results = (List<User>)session.createQuery(query).list();

			return results;
		}catch (Exception e) {
			throw new DatabaseException("Failed to get users at bar: " + barId);
		}finally {

		}
	}

	@SuppressWarnings({"rawtypes", "finally"})
	public List<Drink> getListOfDrinks(int barId)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			String query = "FROM Drink";
			if (barId > 0)
			{
				query += " WHERE bar.bar_id = '" + barId + "'";
			}
			List<Drink> results = (List<Drink>)session.createQuery(query).list();

			return results;
		}catch (Exception e) {
			throw new DatabaseException("Failed to get drinks at bar: " + barId);
		}finally {

		}
	}

	public Admin getAdminByUsername(String userName)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			Admin admin = (Admin) session.get(Admin.class, userName);

			return admin;
		}catch (Exception e) {
			throw new DatabaseException("Admin " + userName + " does not exist");
		}finally {

		}
	}
	
	public String getAdminPasswordHash(String userName)
			throws DatabaseException
	{
		try{			
			Session session = getSession();

			Admin admin = (Admin) session.get(Admin.class, userName);

			return admin.getPasswordHash();
		}catch (Exception e) {
			throw new DatabaseException("Failed to get password hash of " + userName);
		}finally {

		}
	}

	public String getUserPasswordHash(String userName)
			throws DatabaseException
	{
		try{			
			Session session = getSession();

			User user = (User) session.get(User.class, userName);

			return user.getPasswordHash();
		}catch (Exception e) {
			throw new DatabaseException("Failed to get password hash of " + userName);
		}finally {

		}
	}
	
	public User getUserByUsername(String userName)
			throws DatabaseException
	{
		try{			
			Session session = getSession();

			User user = (User) session.get(User.class, userName);

			return user;
		}catch (Exception e) {
			throw new DatabaseException("User " + userName + " does not exist");
		}finally {

		}
	}

	public Drink getDrinkByUser(int drinkId, String userName)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			User user = (User) session.get(User.class, userName);

			int barId = user.getBar().getBar_id();
			Drink drink = (Drink) session.get(Drink.class, drinkId);

			if (drink.getBar().getBar_id() == barId)
			{
				return drink;
			}
			else
			{
				throw new DatabaseException("No drink " + drinkId + " available for " + userName);
			}
		}catch (Exception e) {
			throw new DatabaseException("Failed to get drink " + drinkId + " for " + userName);
		}finally {

		}
	}

	public Drink ifDrinkExists(int drinkId, int barId, String drinkName, double drinkSize, double drinkPrice)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			Drink drink = (Drink) session.get(Drink.class, drinkId);

			if (drink.getBar().getBar_id() == barId
					&& drink.getName().equals(drinkName)
					&& drink.getSize() == drinkSize
					&& drink.getPrice() == drinkPrice)
			{
				return drink;
			}			
			else
			{
				throw new DatabaseException("Drink " + drinkId + " not available at bar " + barId);
			}
		}
		catch (Exception e) {
			throw new DatabaseException("Failed to get drink " + drinkId);
		}finally {

		}
	}

	public void updateOrderStatus(int orderId, String status)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			Order order = (Order)session.get(Order.class, orderId); 
			order.setStatus(status);
			order.setUpdateTS(new Date());
			session.update(order);
		}catch (Exception e) {
			throw new DatabaseException("Failed to update status of order " + orderId);
		}finally {

		}
	}

	public Payment createPayment(int orderId)
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
	}

	public Bar createBar(String name, String address, String city, String country)
			throws DatabaseException
	{
		try{
			Session session = getSession();

            Bar bar = new Bar(address, city, country, name);

			session.save(bar);

			return bar;
		}
		catch (Exception e) {
			throw new DatabaseException("Failed to create bar: " + name + ", " + address + ", " + city + ", " + country);
		}finally {

		}
	}

    public Drink createDrink(String name, double price, double size, int barId)
            throws DatabaseException
    {
        try{
            Session session = getSession();

            Bar bar = session.get(Bar.class, barId);

            Drink drink = new Drink(bar, name, price, size);

            session.save(drink);

            return drink;
        }
        catch (Exception e) {
            throw new DatabaseException("Failed to create drink: " + name + ", size: " + size + ", price: " + price + ", bar: " + barId);
        }finally {

        }
    }

	public Order createOrder(int drinkId, int quantity, String status)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			Drink drink = session.get(Drink.class, drinkId);

			Order order = new Order(drink, quantity, status, new Date().toString());

			session.save(order);

			return order;
		}
		catch (Exception e) {
			throw new DatabaseException("Failed to create order for drink " + drinkId);
		}finally {

		}
	}

	public Bar getBarByUser(String userName)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			User user = (User) session.get(User.class, userName);

			return user.getBar();
		}
		catch (Exception e) {
			throw new DatabaseException("Failed to get bar for user " + userName);
		}finally {

		}
	}

	public User createUser(String userName, String passwordHash, int barId)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			Bar bar = (Bar)session.get(Bar.class, barId);

			User user = new User(userName, passwordHash, bar);

			session.save(user);

			return user;
		}
		catch (Exception e) {
			throw new DatabaseException("Failed to create user " + userName + " for bar " + barId);
		}finally {

		}
	}
	
	public boolean truncateTable(String tableName)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			session.createQuery("delete from " + tableName).executeUpdate();

			return true;
		}
		catch (Exception e) {
			throw new DatabaseException(e.getMessage());//"Failed to truncate " + tableName + " table");
		}finally {

		}
	}
	
	public boolean resetAutoIncr(String tableName)
			throws DatabaseException
	{
		try{
			Session session = getSession();

			session.createSQLQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT=1").executeUpdate();

			return true;
		}
		catch (Exception e) {
			throw new DatabaseException(e.getMessage());//"Failed to truncate " + tableName + " table");
		}finally {

		}
	}
}

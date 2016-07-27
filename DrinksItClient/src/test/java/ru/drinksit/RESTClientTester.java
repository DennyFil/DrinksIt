package ru.drinksit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;

import junit.framework.*;
import ru.drinksit.auxillary.Bar;
import ru.drinksit.auxillary.Drink;
import ru.drinksit.auxillary.Order;
import ru.drinksit.auxillary.User;
import ru.drinksit.clients.RESTClientTools;

public class RESTClientTester extends TestCase {

	protected String url;

	// assigning the values
	protected void setUp(){
		url = "http://localhost:8080/DrinksItSrv/";
	}

	// test method to reset DB
	public void testResetDB(){
		
		boolean dbResetted = RESTClientTools.resetDB(url);
		
		assertTrue(dbResetted);
	}

	// test method to create bar
	public void testCreateBar(){

		String barName = "Denis' Pub";
		String address = "Onex";
		String city = "Geneva";
		String country = "Switzerland";

		Bar bar = RESTClientTools.createBar(url, barName, address, city, country);

		assert(bar!=null);
		assert(bar.getName().equals(barName));
		assert(bar.getAddress().equals(address));
		assert(bar.getCity().equals(city));
		assert(bar.getCountry().equals(country));
	}

	// test method to create user
	public void testCreateUser(){

		int barId = 1;
		String username = "denis";
		String password = "denis";
		User user = RESTClientTools.createUser(url, barId, username, password);

		assert(user != null);
		assert(user.getUserName().equals(username));
		assert(user.getBar().getBar_id() == barId);
	}

	// test method to create drink
	public void testCreateDrink(){

		int barId = 1;
		String drinkName = "Denis' beer";
		double price = 5.0;
		double size = 0.5;	   

		Drink drink = RESTClientTools.createDrink(url, barId, drinkName, price, size);

		assert(drink != null);
		assert(drink.getBar().getBar_id() == barId);
		assert(drink.getName().equals(drinkName));
		assert(drink.getPrice() == price);
		assert(drink.getSize() == size);
	}

	// test method to check drink
	public void testCheckDrink(){

		int barId = 1;
		int drinkId = 1;
		String drinkName = "Denis' beer";
		double price = 5.0;
		double size = 0.5;
        
        boolean drinkStatus = RESTClientTools.checkDrink(url, barId, drinkId, drinkName, price, size);
        
        assertTrue(drinkStatus);
	}

	// test method to create order
	public void testCreateOrder(){

		int drinkId = 1;
		int quantity = 2;

        Order order = RESTClientTools.createOrder(url, drinkId, quantity);        
        
        int barId = 1;
		String drinkName = "Denis' beer";
		double price = 5.0;
		double size = 0.5;
        
        assert(order != null);
        assert(order.getQuantity() == 2);
        
        Drink drink = order.getDrink();
        
		assert(drink != null);
		assert(drink.getName().equals(drinkName));
		assert(drink.getPrice() == price);
		assert(drink.getSize() == size);
		
		Bar bar = drink.getBar();
		assert(bar != null);
		assert(bar.getBar_id() == barId);		
	}

	// test method to check list of bars
	public void testListBars(){
		
		List<Bar> bars = RESTClientTools.listBars(url);

        for (int i = 0; i < bars.size(); i++)
        {
            Bar bar = bars.get(i);
            System.out.println("Bar " + bar.getBar_id());
            System.out.println("	" + bar.getName());
            System.out.println("	" + bar.getAddress());
            System.out.println("	" + bar.getCity());
            System.out.println("	" + bar.getCountry());
        }
	}
}
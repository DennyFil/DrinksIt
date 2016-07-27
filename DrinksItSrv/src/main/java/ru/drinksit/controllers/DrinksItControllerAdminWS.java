package ru.drinksit.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.drinksit.auxillary.database.Bar;
import ru.drinksit.auxillary.database.DatabaseException;
import ru.drinksit.auxillary.database.Drink;
import ru.drinksit.auxillary.database.DrinksItDBManager;
import ru.drinksit.auxillary.database.User;
import ru.drinksit.auxillary.HmacAuthenticationFilter;

@RestController
public class DrinksItControllerAdminWS {

	private static final Logger logger = LoggerFactory.getLogger("wsControllerLogger");

	@Autowired
	DrinksItDBManager drinksItDBManager;
	
	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;
	
	@SuppressWarnings("finally")
	@RequestMapping("/admin/drinks")
	public List<Drink> getAvailableDrinks(HttpServletRequest request, @RequestParam Integer barId) {

		JSONObject contentJson = new JSONObject();
		contentJson.put("barId", new Integer(barId).toString());
		
		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("getAvailableDrinks: hmac check failed");
			return null;
		}
		
		logger.debug("getAvailableDrinks: hmac check successed");

		try {
			List<Drink> drinks = drinksItDBManager.getListOfDrinks(barId);
			logger.debug("INFO RETURNED: List of drinks for bar: " + barId + "");
			return drinks;
		} catch (DatabaseException e) {
			logger.error("Failed to get list of drinks for bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {

		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("/admin/users")
	public List<User> getAvailableUsers(HttpServletRequest request, @RequestParam Integer barId) {

		JSONObject contentJson = new JSONObject();
		contentJson.put("barId", new Integer(barId).toString());
		
		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("getAvailableUsers: hmac check failed");
			return null;
		}
		
		logger.debug("getAvailableUsers: hmac check successed");

		try {
			List<User> users = drinksItDBManager.getListOfUsers(barId);
			logger.debug("INFO RETURNED: List of users for bar: " + barId + "");
			return users;
		} catch (DatabaseException e) {
			logger.error("Failed to get list of users for bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {

		}
	}

	@SuppressWarnings("finally")
	@RequestMapping("/admin/bars")
	public List<Bar> getListOfBars(HttpServletRequest request) {
		
		if (! hmacAuthenticationFilter.filterRequest(request, "{}"))
		{
			logger.debug("getListOfBars: hmac check failed");
			return null;
		}
		
		logger.debug("getListOfBars: hmac check successed");
		
		try {
			List<Bar> bars = drinksItDBManager.getListOfBars();
			logger.debug("INFO RETURNED: List of bars");
			return bars;
		} catch (DatabaseException e) {
			logger.error("Failed to get list of bars");
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {

		}
	}

    @SuppressWarnings("finally")
    @RequestMapping("/admin/createDrink")
    public Drink createDrink(HttpServletRequest request, @RequestParam String drinkName, @RequestParam double price, @RequestParam double size, @RequestParam int barId) {
        
    	JSONObject contentJson = new JSONObject();
		contentJson.put("drinkName", drinkName);
		contentJson.put("price", new Double(price).toString());
		contentJson.put("size", new Double(size).toString());
		contentJson.put("barId", new Integer(barId).toString());

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("createDrink: hmac check failed");
			return null;
		}
		
		logger.debug("createDrink: hmac check successed");
    	
    	try {
            Drink drink = drinksItDBManager.createDrink(drinkName, price, size, barId);
            logger.debug("CREATION: drink: " + drinkName + ", size: " + size + ", price: " + price + ", bar: " + barId);
            return drink;
        } catch (DatabaseException e) {
            logger.error("Failed to create drink: " + drinkName + ", size: " + size + ", price: " + price + ", bar: " + barId);
            logger.error(ExceptionUtils.getStackTrace(e));
            return null;
        } finally {

        }
    }

	@SuppressWarnings("finally")
	@RequestMapping("/admin/createBar")
	public Bar createBar(HttpServletRequest request, @RequestParam String barName, @RequestParam String address, @RequestParam String city, @RequestParam String country)
	{
		JSONObject contentJson = new JSONObject();
		contentJson.put("barName", barName);
		contentJson.put("address", address);
		contentJson.put("city", city);
		contentJson.put("country", country);

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("createBar: hmac check failed");
			return null;
		}
		
		logger.debug("createBar: hmac check successed");
    	
		try {
			Bar bar = drinksItDBManager.createBar(barName, address, city, country);
			logger.debug("CREATION: bar: " + barName + ", " + address + ", " + city + ", " + country);
			return bar;
		}
		catch(DatabaseException e)
		{
			logger.error("Failed to create bar: " + barName + ", " + address + ", " + city + ", " + country);
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		finally
		{

		}
	}
	
	@RequestMapping("/admin/createUser")
	public User createUser(HttpServletRequest request, @RequestParam String userName, @RequestParam String passwordHash, @RequestParam Integer barId)
	{
		JSONObject contentJson = new JSONObject();
		contentJson.put("userName", userName);
		contentJson.put("passwordHash", passwordHash);
		contentJson.put("barId", new Integer(barId).toString());

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("createUser: hmac check failed");
			return null;
		}
		
		logger.debug("createUser: hmac check successed");
    	
		try
		{			
			User user = drinksItDBManager.createUser(userName, passwordHash, barId);
			logger.debug("CREATION: admin user: " + userName + " in bar: " + barId);
			return user;
		}
		catch (Exception e)
		{
			logger.error("Failed to create admin user: " + userName + " in bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		finally
		{
			
		}
	}
	
	@RequestMapping("/admin/resetDB")
	public boolean resetDB(HttpServletRequest request)
	{
		if (! hmacAuthenticationFilter.filterRequest(request, "{}"))
		{
			logger.debug("createUser: hmac check failed");
			return false;
		}
		
		try
		{			
			boolean dbResetted = drinksItDBManager.truncateTable("Payment") &&
					drinksItDBManager.truncateTable("Order") &&
					drinksItDBManager.truncateTable("Drink") &&
					drinksItDBManager.truncateTable("User") &&
					drinksItDBManager.truncateTable("Bar") &&
					drinksItDBManager.resetAutoIncr("bars") &&
					drinksItDBManager.resetAutoIncr("orders") &&
					drinksItDBManager.resetAutoIncr("drinks") &&
					drinksItDBManager.resetAutoIncr("payments");
			
			logger.debug("DB resetted");
			return dbResetted;
		}
		catch (Exception e)
		{
			logger.error("Failed to reset DB");
			logger.error(ExceptionUtils.getStackTrace(e));
			return false;
		}
		finally
		{
			
		}
	}
}

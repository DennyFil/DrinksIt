package ru.drinksit.controllers;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.drinksit.auxillary.database.DatabaseException;
import ru.drinksit.auxillary.database.Drink;
import ru.drinksit.auxillary.database.DrinksItDBManager;
import ru.drinksit.auxillary.database.Order;
import ru.drinksit.auxillary.database.OrderStatus;
import ru.drinksit.auxillary.database.Payment;

@RestController
public class DrinksItControllerWS {

	private static final Logger logger = LoggerFactory.getLogger("wsControllerLogger");

	@Autowired
	DrinksItDBManager drinksItDBManager;

	@SuppressWarnings("finally")
	@RequestMapping("/user/postOrder")
	public Order postOrder(@RequestParam Integer drinkId, 
							@RequestParam Integer barId, 
							@RequestParam String drinkName, 
							@RequestParam Double drinkSize, 
							@RequestParam Double drinkPrice)
	{
		Drink drink = checkDrink(drinkId, barId, drinkName, drinkSize, drinkPrice);
		
		if (drink != null)
		{
			int quantity = 1;
			Order order = createOrder(drink.getDrink_id(), quantity);
			
			return order != null? order : null; // Replace by OK http response
		}
		
		return null;
	}
	
	
	@SuppressWarnings("finally")
	@RequestMapping("/user/createOrder")
	public Order createOrder(@RequestParam Integer drinkId, @RequestParam Integer quantity)
	{
		try
		{
			Order order = drinksItDBManager.createOrder(drinkId, quantity, OrderStatus.NOT_ACCEPTED.getStatus());
			logger.debug("CREATION: order for drink: " + drinkId + " with quantity: " + quantity);
			return order;
		}

		catch (DatabaseException e)
		{
			logger.error("Failed to create order for drink: " + drinkId + " with quantity: " + quantity);
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		finally
		{
			
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("/user/createPayment")
	public Payment createPayment(@RequestParam Integer orderId)
	{
		try
		{
			Payment payment = drinksItDBManager.createPayment(orderId);
			logger.debug("CREATION: payment for order: " + orderId);
			return payment;
		}
		catch (DatabaseException e)
		{
			logger.error("Failed to create payment for order: " + orderId);
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		finally
		{

		}
	}

	@SuppressWarnings("finally")
	@RequestMapping("/user/checkDrink")
	public Drink checkDrink(@RequestParam Integer drinkId, @RequestParam Integer barId, @RequestParam String drinkName, @RequestParam Double drinkSize, @RequestParam Double drinkPrice)
	{
		try
		{
			Drink drink = drinksItDBManager.ifDrinkExists(drinkId, barId, drinkName, drinkSize, drinkPrice);
			logger.debug("CHECK: drink: " + drinkId + " (name: " + drinkName + ", size: " + drinkSize + ", price: " + drinkPrice + ") in bar: " + barId);
			return drink;
		}
		catch (DatabaseException e)
		{
			logger.error("Failed to check drink: " + drinkId + " (name: " + drinkName + ", size: " + drinkSize + ", price: " + drinkPrice + ") in bar: " + barId);
			logger.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		finally
		{
			
		}
	}
}

package ru.drinksit.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.drinksit.auxillary.HmacAuthenticationFilter;
import ru.drinksit.auxillary.DTO.Drink;
import ru.drinksit.auxillary.DTO.Order;
import ru.drinksit.auxillary.database.OrderComparator;
import ru.drinksit.auxillary.database.OrderStatus;
import ru.drinksit.auxillary.ServiceDTO.DrinkService;
import ru.drinksit.auxillary.ServiceDTO.OrderService;

@RestController
public class OrderController {

	private static final Logger logger = 
			LoggerFactory.getLogger("orderControllerLogger");
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	DrinkService drinkService;
	
	@Autowired
    private Environment environment;
	
	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;

	@RequestMapping("/recentOrders")
	public ResponseEntity<List<Order>> getRecentOrders(HttpServletRequest request, @RequestParam String userName) {

		logger.debug("GET /recentOrders for: " + userName);
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("userName", userName);
		
		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /recentOrders: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		List<Order> orderListAll = orderService.getListOfOrders(userName);

		String deliveredOrderTimeDisplay = environment.getRequiredProperty("order.deliveredOrderTimeDisplay");
		long DISPLAY_ORDER_TIME = Integer.valueOf(deliveredOrderTimeDisplay);

		Date currDate = new Date();
		List<Order> filteredOrders = orderListAll.stream()
				.filter(o -> ( (currDate.getTime() - o.getUpdateTS().getTime()) / (60 * 1000) ) <= DISPLAY_ORDER_TIME ).collect(Collectors.toList());

		// Remove orders that have been DELIVERED more that deliveryOrderTimeDisplay minutes
		/*Iterator<Order> it = orderListAll.iterator();
		while (it.hasNext())
		{
			Order order = it.next();

			String status = order.getStatus();
			Date timeStamp = order.getUpdateTS();			

			long diff = currDate.getTime() - timeStamp.getTime();
			long diffMinutes = diff / (60 * 1000);			

			if (status.equals(OrderStatus.DELIVERED.toString()) && diffMinutes >= DISPLAY_ORDER_TIME)
			{
				it.remove();
			}			
		}*/

		// Sorting order by status and creation time
		//orderListAll.sort(new OrderComparator());
		Collections.sort(filteredOrders, new OrderComparator());

		logger.debug("GET /recentOrders: returned list of recent orders");
		return ResponseEntity.ok(filteredOrders);
	}

	@RequestMapping("/orders")
	public ResponseEntity<List<Order>> getOrders(HttpServletRequest request, @RequestParam String userName) {
		
		logger.debug("GET /orders for " + userName);
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("userName", userName);
		
		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /orders: hmac check failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		logger.debug("GET orders for: " + userName);

		List<Order> orderListAll = orderService.getListOfOrders(userName);
		
		return ResponseEntity.ok(orderListAll);
	}

	/*@SuppressWarnings("finally")
	@RequestMapping("/getOrdersReport")
	public ModelAndView getOrdersReport(HttpSession session, @RequestParam String dateStart, @RequestParam String dateEnd) {

		User user = (User) session.getAttribute("loggedInUser");
		if (session == null || user == null) {

			return new ModelAndView("login");
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		ModelAndView modelAndView = new ModelAndView("OrdersReportGenerator");

		try
		{
			Date startDateD = df.parse(dateStart);			
			Date endDateD = df.parse(dateEnd);

			List<Order> orderList = getListOfOrdersFromDB(user.getUserName(), startDateD, endDateD);

			Bar bar = getBarByUserFromDB(user.getUserName());

			Map<String, Object> report = new HashMap<String, Object>();
			report.put("startDate", startDateD);
			report.put("endDate", endDateD);
			report.put("orders", orderList);
			report.put("dateFormat", df);
			report.put("bar", bar);

			logger.info("ORDER'S REPORT GENERATED for period from " + dateStart + " till " + dateEnd);
			return new ModelAndView(new PdfOrderReportView(), report);
		}
		catch(Exception e)
		{
			modelAndView.addObject("message", "Failed to generate orders report");
			logger.error("Failed to generate orders report");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return modelAndView;
	}

	@RequestMapping("/OrdersReportGenerator")
	public String getOrdersReportGenerator(HttpSession session) {

		if (session == null || session.getAttribute("loggedInUser") == null) {

			return "login";
		}

		return "OrdersReportGenerator";
	}*/

	@RequestMapping("/updateOrderStatus")
	public ResponseEntity<String> updateOrderStatus(HttpServletRequest request, HttpSession session, @RequestParam Integer orderId, @RequestParam String status) {  

		logger.debug("GET /updateOrderStatus for order " + orderId + " to " + status);
		JSONObject contentJson = new JSONObject();
		contentJson.put("orderId", orderId);
		contentJson.put("status", status);
		
		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /updateOrderStatus: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		String newStatus = status;
		boolean updateStatusNeeded = false;

		switch (OrderStatus.valueOf(status))
		{
		case NOT_ACCEPTED:
			newStatus = OrderStatus.ACCEPTED.getStatus();
			updateStatusNeeded = true;
			break;
		case ACCEPTED:
			newStatus = OrderStatus.DELIVERED.getStatus();
			updateStatusNeeded = true;
			break;
		case DELIVERED:
			// do nothing
			break;
		default:
			// do nothing
			break;
		}

		if (updateStatusNeeded)
		{
			orderService.updateOrderStatus(orderId, newStatus);
		}

		return ResponseEntity.ok(newStatus);
	}
	
	@RequestMapping("/postOrder")
	public ResponseEntity<Order> postOrder(@RequestParam Integer drinkId, 
							@RequestParam Integer barId, 
							@RequestParam String drinkName, 
							@RequestParam Double drinkSize, 
							@RequestParam Double drinkPrice)
	{
		logger.debug("POST /postOrder for drink " + drinkId + " in bar " + barId);
		
		Drink drink = drinkService.checkDrink(drinkId, barId, drinkName, drinkSize, drinkPrice);
		
		if (drink != null)
		{
			int quantity = 1;
			Order order = orderService.createOrder(drinkId, quantity, OrderStatus.NOT_ACCEPTED.getStatus());
			
			return order != null? ResponseEntity.ok(order) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
	}
	
	/*@SuppressWarnings("finally")
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
	}*/
}

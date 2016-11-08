package ru.drinksit.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Collections;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;  
import org.springframework.web.servlet.ModelAndView;

import ru.drinksit.auxillary.database.Bar;
import ru.drinksit.auxillary.database.DatabaseException;
import ru.drinksit.auxillary.database.DrinksItDBManager;
import ru.drinksit.auxillary.database.Order;
import ru.drinksit.auxillary.database.OrderComparator;
import ru.drinksit.auxillary.database.OrderStatus;
import ru.drinksit.auxillary.database.User;
import ru.drinksit.views.PdfOrderReportView;

@Controller
public class OrderControllerSrv {

	private static final Logger logger = 
			LoggerFactory.getLogger("orderControllerLogger");
	
	@Autowired
	DrinksItDBManager drinksItDBManager;
	
	@Autowired
    private Environment environment;
	
	@SuppressWarnings("finally")
	private List<Order> getListOfOrders(String userName) {
	
		List<Order> orderListAll = new ArrayList<Order>();
		
		try
		{
			orderListAll = drinksItDBManager.getListOfOrders(userName);
			logger.info("INFO RETURNED: list of orders for bar of user: " + userName);
		}
		catch(DatabaseException e)
		{
			logger.error("Failed to get list of orders for bar of user: " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		finally
		{			
			return orderListAll;
		}
	}
	
	@SuppressWarnings("finally")
	private Bar getBarByUser(String userName)
	{
		Bar bar = null;
		try
		{
			bar = drinksItDBManager.getBarByUser(userName);
			logger.error("INFO RETURNED: bar of user: " + userName);
		}
		catch(DatabaseException e)
		{
			logger.error("Failed to get bar of user: " + userName);
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		finally
		{
			return bar;
		}
	}
	
	@SuppressWarnings("finally")
	private List<Order> getListOfOrdersForPeriod(String userName, Date startDate, Date endDate)
	{		
		List<Order> orderListAll = getListOfOrders(userName);
		
		// Tue Mar 22 22:12:34 CET 2016
		DateFormat dfCreationTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		
		Iterator<Order> it = orderListAll.iterator();
		while (it.hasNext())
		{
			Order order = it.next();
			
			try
			{
				Date creationTime = dfCreationTime.parse(order.getCreationTS());
	
				if (creationTime.before(startDate) || creationTime.after(endDate))
				{
					it.remove();
				}
			}
			catch(Exception e)
			{
				// Failed to get order's creation time
				logger.error("Failed to get creation time of order: " + order.getOrder_id());
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
		
		logger.info("INFO RETURNED: list of orders for period from " + startDate.toString() + " till " + endDate.toString());
		return orderListAll;
	}
	
	@SuppressWarnings("finally")
	private List<Order> getListOfRecentOrders(String userName) {
		
		List<Order> orderListAll = getListOfOrders(userName);

		String deliveredOrderTimeDisplay = environment.getRequiredProperty("order.deliveredOrderTimeDisplay");
		
		// Remove orders that have been DELIVERED more that deliveryOrderTimeDisplay minutes
		Iterator<Order> it = orderListAll.iterator();
		while (it.hasNext())
		{
			Order order = it.next();
			
			String status = order.getStatus();
			Date timeStamp = order.getUpdateTS();

			Date currDate = new Date();

			long diff = currDate.getTime() - timeStamp.getTime();
			long diffMinutes = diff / (60 * 1000);
			
			long DISPLAY_ORDER_TIME = Integer.valueOf(deliveredOrderTimeDisplay);
			if (status.equals(OrderStatus.DELIVERED.toString()) && diffMinutes >= DISPLAY_ORDER_TIME)
			{
				it.remove();
			}			
		}
		
		// Sorting order by status and creation time
		//orderListAll.sort(new OrderComparator());
		Collections.sort(orderListAll, new OrderComparator());
		
		logger.info("INFO RETURNED: list of recent orders");
		return orderListAll;
	}
	
	@SuppressWarnings("finally")
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
			
			List<Order> orderList = getListOfOrdersForPeriod(user.getUserName(), startDateD, endDateD);
						
			Bar bar = getBarByUser(user.getUserName());
			
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
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("/OrdersDashboard")
	public ModelAndView getOrdersDashboard(HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (session == null || user == null) {
			
			return new ModelAndView("login");
        }
		
		ModelAndView modelAndView = new ModelAndView("OrdersDashboard");
		String ordersPageRefreshTime = environment.getRequiredProperty("order.ordersPageRefresh");
		modelAndView.addObject("ordersPageRefreshTime", ordersPageRefreshTime);

		List<Order> orderList = getListOfRecentOrders(user.getUserName());
			
		modelAndView.addObject("orderList", orderList);

		return modelAndView;
	}

	@RequestMapping("/updateOrderStatus")
	public String updateOrderStatus(HttpSession session, @RequestParam Integer orderId, @RequestParam String status) {  

		if (session == null || session.getAttribute("loggedInUser") == null) {
			
			return "login";
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
        	try
        	{
        		drinksItDBManager.updateOrderStatus(orderId, newStatus);
        		logger.info("STATUS UPDATED: order " + orderId + " to " + newStatus);
        	}
        	catch(DatabaseException e)
        	{
        		logger.error("Failed to update order status");
    			logger.error(ExceptionUtils.getStackTrace(e));
        	}
		}

		return "redirect:/OrdersDashboard";  
	}
}

package webservice.controllers;

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

import webservice.auxillary.AuthenticationService;
import webservice.auxillary.UserInfo;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.database.OrderComparator;
import webservice.auxillary.database.OrderStatus;
import webservice.auxillary.ServiceDTO.DrinkService;
import webservice.auxillary.ServiceDTO.OrderService;

@RestController
public class OrderController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("orderControllerLogger");
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	DrinkService drinkService;
	
	@Autowired
    private Environment environment;
	
	@Autowired
	AuthenticationService authService;

	@RequestMapping("/recentOrders")
	public ResponseEntity<List<Order>> GetRecentOrders(HttpServletRequest request) throws Exception {

		UserInfo userInfo = getUserInfo(request);
		logger.debug("GET /recentOrders for: " + userInfo.getUserName());
		
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /recentOrders: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		List<Order> orderListAll = orderService.GetOrders(userInfo.getUserName());

		String deliveredOrderTimeDisplay = environment.getRequiredProperty("order.deliveredOrderTimeDisplay");
		long DISPLAY_ORDER_TIME = Integer.valueOf(deliveredOrderTimeDisplay);

		Date currDate = new Date();
		List<Order> filteredOrders = orderListAll.stream()
				.filter(o -> ( (currDate.getTime() - o.getUpdateTS().getTime()) / (60 * 1000) ) <= DISPLAY_ORDER_TIME ).collect(Collectors.toList());

		// Sorting order by status and creation time
		Collections.sort(filteredOrders, new OrderComparator());

		logger.debug("GET /recentOrders: returned list of recent orders");
		return ResponseEntity.ok(filteredOrders);
	}

	@RequestMapping("/orders")
	public ResponseEntity<List<Order>> GetOrders(HttpServletRequest request) throws Exception {
		
		UserInfo userInfo = getUserInfo(request);
		logger.debug("GET /orders for " + userInfo.getUserName());

		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /orders: not logged in");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		List<Order> orderListAll = orderService.GetOrders(userInfo.getUserName());
		
		return ResponseEntity.ok(orderListAll);
	}

	@RequestMapping("/updateOrderStatus")
	public ResponseEntity<String> UpdateOrderStatus(HttpServletRequest request, HttpSession session, @RequestParam Integer orderId) throws Exception {  

		logger.debug("GET /updateOrderStatus for order " + orderId);
		
		if (! authService.IsAuthorized(getUserInfo(request)))
		{
			logger.debug("GET /updateOrderStatus: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Order order = orderService.GetOrder(orderId);
		
		if (order != null) {
	
			String status = order.getStatus();
			switch (OrderStatus.valueOf(status))
			{
			case NOT_ACCEPTED:
				order.setStatus(OrderStatus.ACCEPTED.getStatus());
				break;
			case ACCEPTED:
				order.setStatus(OrderStatus.DELIVERED.getStatus());
				break;
			case DELIVERED:
				// do nothing
				break;
			default:
				// do nothing
				break;
			}
	
			order.setUpdateTS(new Date());
			orderService.UpdateOrder(order);

			return ResponseEntity.ok(status);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@RequestMapping("/postOrder")
	public ResponseEntity<Order> PostOrder(@RequestParam Integer drinkId, 
							@RequestParam Integer barId, 
							@RequestParam String drinkName, 
							@RequestParam Double drinkSize, 
							@RequestParam Double drinkPrice)
	{
		logger.debug("POST /postOrder for drink " + drinkId + " in bar " + barId);
		
		boolean drinkExists = drinkService.CheckDrink(drinkId, barId, drinkName, drinkSize, drinkPrice);
		
		if (drinkExists)
		{
			int quantity = 1;
			Order order = orderService.CreateOrder(drinkId, quantity, OrderStatus.NOT_ACCEPTED.getStatus());
			
			return order != null? ResponseEntity.ok(order) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
	}
}

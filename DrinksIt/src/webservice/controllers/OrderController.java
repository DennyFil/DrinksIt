package webservice.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
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

	@RequestMapping("/recentOrders")
	public ResponseEntity<List<Order>> GetRecentOrders(HttpServletRequest request) throws Exception {

		AuthInfo userInfo = getAuthInfo(request);
		logger.debug("GET /recentOrders for: " + userInfo.getUserName());

		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /recentOrders: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try {
			List<Order> orderListAll = orderService.GetOrders(userInfo.getUserName());

			String deliveredOrderTimeDisplay = environment.getRequiredProperty("order.deliveredOrderTimeDisplay");
			long DISPLAY_ORDER_TIME = Integer.valueOf(deliveredOrderTimeDisplay);

			Date currDate = new Date();
			List<Order> filteredOrders = orderListAll.stream()
					.filter(o -> ( (currDate.getTime() - o.getUpdateTS().getTime()) / (60 * 1000) ) <= DISPLAY_ORDER_TIME ).collect(Collectors.toList());

			// Sorting order by status and creation time
			Collections.sort(filteredOrders, new OrderComparator());

			return ResponseEntity.ok(filteredOrders);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@RequestMapping("/orders")
	public ResponseEntity<List<Order>> GetOrders(HttpServletRequest request) throws Exception {

		AuthInfo userInfo = getAuthInfo(request);
		logger.debug("GET /orders for " + userInfo.getUserName());

		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /orders: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try {
			List<Order> orderListAll = orderService.GetOrders(userInfo.getUserName());

			return ResponseEntity.ok(orderListAll);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@RequestMapping("/updateOrderStatus")
	public ResponseEntity<Order> UpdateOrderStatus(HttpServletRequest request, HttpSession session, @RequestParam Integer orderId) throws Exception {  

		logger.debug("GET /updateOrderStatus for order " + orderId);

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /updateOrderStatus: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try {
			Order order = orderService.GetOrder(orderId);

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

			return ResponseEntity.ok(order);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);	}

	@RequestMapping("/postOrder")
	public ResponseEntity<Order> PostOrder(@RequestParam Integer drinkId, 
			@RequestParam Integer barId, 
			@RequestParam String drinkName, 
			@RequestParam Double drinkSize, 
			@RequestParam Double drinkPrice)
	{
		logger.debug("POST /postOrder for drink " + drinkId + " in bar " + barId);

		try {
			boolean drinkExists = drinkService.CheckDrink(drinkId, barId, drinkName, drinkSize, drinkPrice);

			if (drinkExists)
			{
				int quantity = 1;
				Order newOrder = orderService.CreateOrder(drinkId, quantity, OrderStatus.NOT_ACCEPTED.getStatus());
				return ResponseEntity.ok(newOrder);
			}
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}		

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}

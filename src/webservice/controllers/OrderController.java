package webservice.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.AuthInfoService;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.ServiceDAO.IDrinkService;
import webservice.auxillary.ServiceDAO.IOrderService;
import webservice.auxillary.database.OrderComparator;
import webservice.auxillary.database.OrderStatus;

@RestController
public class OrderController extends BaseController {

	@Autowired
	IOrderService orderService;

	@Autowired
	IDrinkService drinkService;

	@Autowired
	private Environment environment;

	@RequestMapping("/recentOrders")
	public ResponseEntity GetRecentOrders(HttpServletRequest request) throws Exception {

		logger.debug("GET /recentOrders");

		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
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

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get recent orders");
	}

	@RequestMapping("/orders")
	public ResponseEntity GetOrders(HttpServletRequest request) throws Exception {

		logger.debug("GET /orders");

		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
			List<Order> orderListAll = orderService.GetOrders(userInfo.getUserName());

			return ResponseEntity.ok(orderListAll);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get orders");
	}

	@RequestMapping("/updateOrderStatus")
	public ResponseEntity UpdateOrderStatus(HttpServletRequest request, HttpSession session, @RequestBody Integer orderId) throws Exception {  

		logger.debug("GET /updateOrderStatus for order " + orderId);

		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
			Order order = orderService.GetOrder(orderId);
			
			if (! arService.isBarAdmin(userInfo, order.getDrink().getBarId()))
			{
				logger.debug("GET /updateOrderStatus: no right for " + userInfo.getUserName());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not allowed to update order");
			}			

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
			orderService.Update(order);

			return ResponseEntity.ok(order);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order status");
	}

	@RequestMapping("/postOrder")
	public ResponseEntity PostOrder(@RequestParam Integer drinkId, 
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

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to post order");
	}
}

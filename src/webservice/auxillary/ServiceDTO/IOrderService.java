package webservice.auxillary.ServiceDTO;

import java.util.List;

import webservice.auxillary.DTO.Order;

public interface IOrderService extends IGenDao<Order> {

	Order GetOrder(int orderId) throws Exception;
	
	List<Order> GetOrders(String userName) throws Exception;
	
	void UpdateOrder(Order order) throws Exception;
	
	Order CreateOrder(int drinkId, int quantity, String status) throws Exception;
}

package webservice.auxillary.database;

import java.util.Comparator;
import webservice.auxillary.DTO.Order;

public class OrderComparator implements Comparator<Order> {

	public OrderComparator() {
	}
	
	public int compare(Order o1, Order o2)
	{
		if (o1.getStatus().equals(OrderStatus.DELIVERED.toString()) && o2.getStatus().equals(OrderStatus.NOT_ACCEPTED.toString()))
		{
			return 1;
		}
		
		if (o1.getStatus().equals(OrderStatus.NOT_ACCEPTED.toString()) && o2.getStatus().equals(OrderStatus.DELIVERED.toString()))
		{
			return -1;
		}
		
		if (o1.getStatus().equals(OrderStatus.DELIVERED.toString()) && o2.getStatus().equals(OrderStatus.ACCEPTED.toString()))
		{
			return 1;
		}
		
		if (o1.getStatus().equals(OrderStatus.ACCEPTED.toString()) && o2.getStatus().equals(OrderStatus.DELIVERED.toString()))
		{
			return -1;
		}
		
		if (o1.getStatus().equals(OrderStatus.ACCEPTED.toString()) && o2.getStatus().equals(OrderStatus.NOT_ACCEPTED.toString()))
		{
			return 1;
		}
		
		if (o1.getStatus().equals(OrderStatus.NOT_ACCEPTED.toString()) && o2.getStatus().equals(OrderStatus.ACCEPTED.toString()))
		{
			return -1;
		}
		
		if ( (o1.getStatus().equals(OrderStatus.DELIVERED.toString()) && o2.getStatus().equals(OrderStatus.DELIVERED.toString()))
				|| (o1.getStatus().equals(OrderStatus.ACCEPTED.toString()) && o2.getStatus().equals(OrderStatus.ACCEPTED.toString()))
				|| (o1.getStatus().equals(OrderStatus.NOT_ACCEPTED.toString()) && o2.getStatus().equals(OrderStatus.NOT_ACCEPTED.toString()))
			)
		{
			return o2.getUpdateTS().compareTo(o1.getUpdateTS());
		}
		
		return 0;
	}
}

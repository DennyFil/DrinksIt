package ru.drinksit.auxillary.DTO;

import java.util.Date;
import javax.persistence.*;

import ru.drinksit.auxillary.database.OrderComparator;

@Entity
@Table(name = "orders")
public class Order implements java.io.Serializable, Comparable<Order> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int order_id;
	
	@Column(name = "ts_create")
	private String ts_create;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_update")
	private Date ts_update;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drink_id")
	private Drink drink;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "status")
	private String status;

	public Order() {
	}
	
	public Order(Drink drink, int quantity, String status, String ts_create) {
		this.drink = drink;
		this.quantity = quantity;
		this.status = status;
		this.ts_create = ts_create;
	}

	public Order(String ts_create, Date updateTime, Drink drink, int quantity, String status) {
		this.ts_update = updateTime;
		this.drink = drink;
		this.quantity = quantity;
		this.status = status;
		this.ts_create = ts_create;
	}

	public int getOrder_id() {
		return this.order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public String getCreationTS()
	{
		return this.ts_create;
	}
	
	public void setCreationTS(String ts_create)
	{
		this.ts_create = ts_create;
	}

	public Date getUpdateTS() {
		return this.ts_update;
	}

	public void setUpdateTS(Date ts) {
		this.ts_update = ts;
	}

	public Drink getDrink() {
		return this.drink;
	}

	public void setDrink(Drink drink) {
		this.drink = drink;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int compareTo(Order that)
	{
		return new OrderComparator().compare(this, that);
	}
}

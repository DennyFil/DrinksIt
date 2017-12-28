package webservice.auxillary.DTO;

import java.util.Date;
import javax.persistence.*;

import webservice.auxillary.DTO.Drink;
import webservice.auxillary.database.OrderComparator;

@Entity
@Table(name = "orders")
public class Order extends GenItem implements java.io.Serializable, Comparable<Order> {

	@Column(name = "ts_create")
	private Date ts_create;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_update")
	private Date ts_update;
	
	@Column(name = "drink_id", insertable = false, updatable = false)
	private int drinkId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drink_id")
	private Drink drink;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "status")
	private String status;

	public Order() {
	}
	
	public Order(int drinkId, int quantity, String status, Date ts_create) {
		this.drinkId = drinkId;
		this.quantity = quantity;
		this.status = status;
		this.ts_create = ts_create;
	}

	public Order(int drinkId, int quantity, String status, Date ts_create, Date updateTime) {
		this.drinkId = drinkId;
		this.quantity = quantity;
		this.status = status;
		this.ts_create = ts_create;
		this.ts_update = updateTime;
	}

	public Date getCreationTS()
	{
		return this.ts_create;
	}
	
	public void setCreationTS(Date ts_create)
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
	
	public int getDrinkId(){
    	return this.drinkId;
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

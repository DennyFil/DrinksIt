package webservice.auxillary.DTO;

import javax.persistence.*;

import webservice.auxillary.DTO.Order;

@Entity
@Table(name = "payments")
public class Payment extends GenItem implements java.io.Serializable {

	@Column(name = "amount")
	private double amount;
    
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	public Payment() {
	}

	public Payment(double amount, Order order) {
		this.amount = amount;
		this.order = order;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}

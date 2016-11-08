package ru.drinksit.auxillary.DTO;

import javax.persistence.*;

@Entity
@Table(name = "payments")
public class Payment implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int payment_id;
	
	@Column(name = "amount")
	private double amount;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;

	public Payment() {
	}

	public Payment(double amount, Order order) {
		this.amount = amount;
		this.order = order;
	}

	public int getPayment_id() {
		return this.payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
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

package ru.drinksit.auxillary;

public class Payment {

	private int payment_id;
	
	private double amount;
	
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

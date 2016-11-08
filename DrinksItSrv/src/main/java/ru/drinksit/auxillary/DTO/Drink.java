package ru.drinksit.auxillary.DTO;

import javax.persistence.*;

@Entity
@Table(name = "drinks")
public class Drink implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drink_id")
	private int drink_id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bar_id")
	private Bar bar;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "size")
	private double size;

	public Drink() {
	}

	public Drink(Bar bar, String name, double price, double size) {
		this.bar = bar;
		this.name = name;
		this.price = price;
		this.size = size;
	}

	public int getDrink_id() {
		return this.drink_id;
	}

	public void setDrink_id(int drink_id) {
		this.drink_id = drink_id;
	}

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSize() {
		return this.size;
	}

	public void setSize(double size) {
		this.size = size;
	}

}

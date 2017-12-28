package webservice.auxillary.DTO;

import javax.persistence.*;

import webservice.auxillary.DTO.Bar;

@Entity
@Table(name = "drinks")
public class Drink extends GenItem implements java.io.Serializable {
	
	@Column(name = "bar_id", insertable = false, updatable = false)
	private int barId;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bar_id")
	private Bar bar;*/
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "size")
	private double size;

	public Drink() {
	}
	
	public Drink(int barId, String name, double price, double size) {
		this.barId = barId;
		this.name = name;
		this.price = price;
		this.size = size;
	}
    
    /*public Bar getBar() {
        return this.bar;
    }*/
    
    public int getBarId(){
    	return this.barId;
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

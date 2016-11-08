package ru.drinksit.auxillary.DTO;

import javax.persistence.*;

@Entity
@Table(name = "bars")
public class Bar implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bar_id")
	private int bar_id;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "name")
	private String name;

	public Bar() {
	}

	public Bar(String address, String city, String country, String name) {
		this.address = address;
		this.city = city;
		this.country = country;
		this.name = name;
	}

	public int getBar_id() {
		return this.bar_id;
	}

	public void setBar_id(int bar_id) {
		this.bar_id = bar_id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

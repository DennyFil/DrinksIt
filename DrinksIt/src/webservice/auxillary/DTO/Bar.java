package webservice.auxillary.DTO;

import javax.persistence.*;

@Entity
@Table(name = "bars")
public class Bar extends GenItem implements java.io.Serializable {

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

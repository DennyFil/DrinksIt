
package ru.drinksit.auxillary;

public class User {

	private String userName;
	
	private Bar bar;
	
	private String passwordHash;

	public User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public User(String userName, Bar bar, String passwordHash) {
		this.userName = userName;
		this.bar = bar;
		this.passwordHash = passwordHash;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}

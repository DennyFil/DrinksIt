package webservice.auxillary;

public class UserInfo {
	String userName;
	String passwordHash;
	
	public UserInfo(String userName, String passwordHash) {
		this.userName = userName;
		this.passwordHash = passwordHash;
	}
	
	public String getUserName(){
		return this.userName;
	}
}
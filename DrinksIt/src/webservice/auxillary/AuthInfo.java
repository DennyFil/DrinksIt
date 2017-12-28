package webservice.auxillary;

public class AuthInfo {
	String userName;
	String passwordHash;
	
	public AuthInfo(String userName, String passwordHash) {
		this.userName = userName;
		this.passwordHash = passwordHash;
	}
	
	public String getUserName(){
		return this.userName;
	}
}
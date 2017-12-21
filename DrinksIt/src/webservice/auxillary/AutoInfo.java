package webservice.auxillary;

public class AutoInfo {
	String userName;
	String passwordHash;
	
	public AutoInfo(String userName, String passwordHash) {
		this.userName = userName;
		this.passwordHash = passwordHash;
	}
	
	public String getUserName(){
		return this.userName;
	}
}
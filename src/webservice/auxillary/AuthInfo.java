package webservice.auxillary;

public class AuthInfo {
	String userName;
	String token;
	
	public AuthInfo(String userName, String token) {
		this.userName = userName;
		this.token = token;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public String getToken(){
		return this.token;
	}
}
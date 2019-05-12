package webservice.auxillary;

public class AuthInfo {
	String userName;
	String token;
	int userId;
	
	public AuthInfo(String userName, String token, int userId) {
		this.userName = userName;
		this.token = token;
		this.userId = userId;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public String getToken(){
		return this.token;
	}

	public int getUserId(){
		return this.userId;
	}
}
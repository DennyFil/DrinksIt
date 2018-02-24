package webservice.auxillary.DTO;

// Not Mapped
public class UserInfo extends BaseItem {

	String userName;
	int barId;
    String barName;
    String token;
    
    public UserInfo(User user) {
    	this(user, "");
	}
    
	public UserInfo(User user, String token) {
		this.userName = user.getUserName();
		if (user.getBar() != null){
			this.barName = user.getBar().getName();
		}
		this.barId = user.getBarId();
		this.token = token;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getBarName() {
		return barName;
	}
	
	public int getBarId() {
		return barId;
	}
	
	public String getToken() {
		return token;
	}

	@Override
	public String getIdStr() {
		return userName;
	}
}

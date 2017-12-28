package webservice.auxillary.DTO;

// Not Mapped
public class UserInfo {

	private String userName;
	private boolean isAdmin;
    private String barName;
    
	public UserInfo(User user) {
		this.userName = user.getUserName();
		this.isAdmin = user.getIsAdmin();
		if (user.getBar() != null){
			//this.barName = user.getBar().getName();
		}
	}
	
	public String getUserName() {
		return userName;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public String getBarName() {
		return barName;
	}
}

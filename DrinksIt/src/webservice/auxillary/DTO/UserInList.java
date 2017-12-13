package webservice.auxillary.DTO;

// Not Mapped
public class UserInList {

	private String userName;
	private boolean isAdmin;
    private String barName;
    
	public UserInList(User user) {
		this.userName = user.getUserName();
		this.isAdmin = user.getIsAdmin();
		this.barName = user.getBar().getName();
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

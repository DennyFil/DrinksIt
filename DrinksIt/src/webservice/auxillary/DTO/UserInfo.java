package webservice.auxillary.DTO;

// Not Mapped
public class UserInfo {

	private String userName;
	private boolean isAdmin;
    
	public UserInfo(User user) {
		this.userName = user.getUserName();
		this.isAdmin = user.getIsAdmin();
	}
	
	public String getUserName() {
		return userName;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
}

package webservice.auxillary.DTO;

// Not Mapped
public class UserInfo {

	private String userName;
	private int barId;
    private String barName;
    
	public UserInfo(User user) {
		this.userName = user.getUserName();
		if (user.getBar() != null){
			this.barName = user.getBar().getName();
		}
		this.barId = user.getBarId();
	}
	
	public String getUserName() {
		return userName;
	}
	
	public int getBarId() {
		return barId;
	}
	
	public String getBarName() {
		return barName;
	}
}

package webservice.auxillary.DTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import webservice.auxillary.DTO.Bar;

@Entity
@Table(name = "users")
public class User implements java.io.Serializable {

	@Id
    @Column(name = "userName")
    private String userName;
	
    @Column(name = "passwordHash")
    private String passwordHash;
    
    @Column(name = "bar_id", insertable = false, updatable = false)
	private int barId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bar_id")
    private Bar bar;

	// Not mapped
    @Transient
	private String password;
    
    public User() {
    }
    
    public User(String userName, String passwordHash, int barId) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.barId = barId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }
    
    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public Bar getBar() {
        return this.bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }
    
    public int getBarId(){
    	return this.barId;
    }
    
    public boolean isAdmin() {
        return this.barId == 0;
    }
    
    public boolean isBarAdmin(int barId) {
        return this.barId == barId || this.barId == 0; // or master bar admin
    }
}

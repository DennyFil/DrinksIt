package webservice.auxillary.DTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import webservice.auxillary.DTO.Bar;

@Entity
@Table(name = "users")
public class User implements java.io.Serializable {

	@Id
    @Column(name = "userName")
    private String userName;

    @Column(name = "passwordHash")
    private String passwordHash;
    
    @Column(name = "isAdmin")
    private boolean isAdmin;
    
    @ManyToOne
    @JoinColumn(name = "bar_id")
    private Bar bar;
    
    public User() {
    }
    
    public User(String userName, String passwordHash, Bar bar) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.isAdmin = false;
        this.bar = bar;
    }
    
    public User(String userName, String passwordHash, Bar bar, boolean isAdmin) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
        this.bar = bar;
    }
    
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public Bar getBar() {
        return this.bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }
}

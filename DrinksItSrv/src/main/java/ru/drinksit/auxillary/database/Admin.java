package ru.drinksit.auxillary.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dennyfil on 25.06.16.
 */
@Entity
@Table(name = "admins")
public class Admin implements java.io.Serializable {

	@Id
    @Column(name = "userName")
    private String userName;

    @Column(name = "passwordHash")
    private String passwordHash;
    
    public Admin() {
    }

    public Admin(String userName) {
        this.userName = userName;
    }

    public Admin(String userName, String passwordHash) {
    	this.userName = userName;
        this.passwordHash = passwordHash;
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
}

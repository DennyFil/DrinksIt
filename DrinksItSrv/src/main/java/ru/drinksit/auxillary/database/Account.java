package ru.drinksit.auxillary.database;

import javax.persistence.*;

/**
 * Created by dennyfil on 25.06.16.
 */
public class Account implements java.io.Serializable {

    @Id
    @Column(name = "userName")
    private String userName;

    @Column(name = "passwordHash")
    private String passwordHash;

    public Account() {
    }

    public Account(String userName) {
        this.userName = userName;
    }

    public Account(String userName, String passwordHash) {
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


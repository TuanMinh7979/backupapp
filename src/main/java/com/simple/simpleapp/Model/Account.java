package com.simple.simpleapp.Model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "Account")

public class Account {

    @Id
    private String username;
    private String phone;
    private String password;
    private String role;
    @Transient
    private String fullname;

    @Transient
    private String address;


    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


}

package com.ivs.map.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="Users")
@Inheritance(strategy=InheritanceType.JOINED) 
public class User {
	public User() {
		
	}
	public User(String phone, String fullname) {
		
		this.phone = phone;
		this.fullname = fullname;
	}
	@Id
	private String phone;
	@Column
	private String fullname;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
	
}

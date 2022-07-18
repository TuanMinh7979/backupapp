package com.simple.simpleapp.Model;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED) 
@PrimaryKeyJoinColumn(name="phone")
@Table(name="Customer")
public class Customer extends User{
	public Customer () {
		super();
	}
	public Customer(String phone, String fullname) {
		super();
	}
}
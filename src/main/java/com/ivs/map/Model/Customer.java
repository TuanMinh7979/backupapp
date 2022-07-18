package com.ivs.map.Model;

import javax.persistence.*;
import javax.swing.LayoutFocusTraversalPolicy;

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
package com.ivs.map.Model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED) 
@PrimaryKeyJoinColumn(name="phone")
@Table(name="Driver")
public class Driver extends User{
	public Driver(String phone, String fullname, String address) {
		super();
		this.address = address;
	}

	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Driver() {
		super();
	}
	
	@OneToMany(mappedBy="driver") 
    private Set<Trip> trip;
	
	 public Set<Trip> getTrip () {
	      return trip ;
	   }
	
}

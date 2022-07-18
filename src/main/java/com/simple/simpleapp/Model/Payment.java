package com.simple.simpleapp.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Payment")

public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentId ; 


	@Column 
	private String content ;
	
	
	public Payment() {
		super();
	}
	
	public int getPaymentId() {
		return paymentId;
	}

	public void setPayment_id(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
// ket noi ngoai
	@OneToMany(mappedBy = "payment")
	private Set<Trip> trip ;
	
	
	
}

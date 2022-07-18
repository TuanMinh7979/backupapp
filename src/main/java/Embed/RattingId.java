package Embed;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.simple.simpleapp.Model.Trip;
import com.simple.simpleapp.Model.Customer;

@SuppressWarnings("serial")
@Embeddable
public class RattingId implements Serializable{
	@OneToOne
	@JoinColumn(name="phone", nullable = false)
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name="trip_id", nullable = false)
	private Trip trip;

	

	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public Trip getTrip() {
		return trip;
	}



	public void setTrip(Trip trip) {
		this.trip = trip;
	}



	public RattingId(Customer customer, Trip trip) {
		super();
		this.customer = customer;
		this.trip = trip;
	}



	public RattingId() {
		super();
	}

	

	
	
}


package com.simpleapp.map.Model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private long tripId;
    @Column
    private String source;
    @Column
    private String destination;
    @Column(name = "time")
    private LocalDateTime day_time;
    
    public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(int unit_price) {
		this.unit_price = unit_price;
	}

    @Column(name = "distance")
    private float distance;

    @Column(name = "unit_price")
    private int unit_price;

    public Trip() {
        super();
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public LocalDateTime getDay_time() {
        return day_time;
    }

    public void setDay_time(LocalDateTime day_time) {
        this.day_time = day_time;
    }

    //	  KHóa ngoại
    @ManyToOne
    @JoinColumn(name = "phone", nullable = true)
    private Driver driver;


    @ManyToOne
    @JoinColumn(name = "paymentId", nullable = true)
    private Payment payment;


    @ManyToOne
    @JoinColumn(name = "stateId", nullable = true)
    private State state;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @ManyToOne
    @JoinColumn(name = "cus_phone", nullable = false)
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}

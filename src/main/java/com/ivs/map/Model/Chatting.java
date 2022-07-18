package com.ivs.map.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;



@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="Chatting")
public class Chatting {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long chat_id; 
	  
	  @Column
	  private String content ; 
	  
	  
	  @Column(name = "send_to")
	  private boolean sendTo ; 
	  
	 
	  
	  @ManyToOne
	  @JoinColumn(name = "trip_id", nullable = false)
	  private Trip trip;
	  
	  
	  
	  public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public boolean isSendTo() {
		return sendTo;
	}



	public void setSendTo(boolean sendTo) {
		this.sendTo = sendTo;
	}



	public Trip getTrip() {
		return trip;
	}



	public void setTrip(Trip trip) {
		this.trip = trip;
	}



	public Long getChat_id() {
		return chat_id;
	}



	public Chatting() {
		super();
	}



	
	  
	  
}

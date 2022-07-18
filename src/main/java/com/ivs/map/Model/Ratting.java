package com.ivs.map.Model;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import Embed.RattingId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Ratting")
@Embeddable
public class Ratting{
	
	@Id
	@EmbeddedId
	private RattingId rattingId;
	
	private String content;
	private int star;
	
	
	@Transient
	private long tripId;
	



	
	
	
	public RattingId getRattingId() {
		return rattingId;
	}
	public void setRattingId(RattingId rattingId) {
		this.rattingId = rattingId;
	}
	public Ratting(RattingId rattingId, String content, int star) {
		super();
		this.rattingId = rattingId;
		this.content = content;
		this.star = star;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public long getTripId() {
		return tripId;
	}
	public void setTripId(long trip_id) {
		this.tripId = trip_id;
	}
	
	public Ratting() {
		
	}
}

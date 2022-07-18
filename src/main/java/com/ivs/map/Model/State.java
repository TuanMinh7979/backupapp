package com.ivs.map.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="State")
public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stateId  ; 
	
	public State(int stateId, String content) {
		super();
		this.stateId = stateId;
		this.content = content;
	}


	@Column
	private String content ; 
	
	public State() {
		super();
	}

	
	
	
	public int getState_id() {
		return stateId;
	}

	public void setState_id(short state_id) {
		this.stateId = state_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	// ket noi ngoai
	
	
	@OneToMany(mappedBy = "state")
	private Set<Trip> trip ;
	

	
	
	
	
}

package com.ivs.map.mapsimulator.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class Edge {

	private Long neighBourId;
	Double weight;
	public Long getNeighBourId() {
		return neighBourId;
	}
	public void setNeighBourId(Long neighBourIdx) {
		this.neighBourId = neighBourIdx;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}



}
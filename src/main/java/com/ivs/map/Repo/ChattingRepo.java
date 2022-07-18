package com.ivs.map.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivs.map.Model.Chatting;
import com.ivs.map.Model.Trip;

public interface ChattingRepo extends JpaRepository<Chatting, Long>{

	List<Chatting> findByTrip(Trip trip);

}

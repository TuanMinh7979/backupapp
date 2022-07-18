package com.simpleapp.map.Repo;

import java.util.List;

import com.simpleapp.map.Model.Chatting;
import com.simpleapp.map.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepo extends JpaRepository<Chatting, Long>{

	List<Chatting> findByTrip(Trip trip);

}

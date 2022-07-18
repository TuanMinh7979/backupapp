package com.simple.simpleapp.Repo;

import java.util.List;

import com.simple.simpleapp.Model.Chatting;
import com.simple.simpleapp.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepo extends JpaRepository<Chatting, Long>{

	List<Chatting> findByTrip(Trip trip);

}

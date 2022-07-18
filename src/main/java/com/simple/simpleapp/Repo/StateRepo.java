package com.simple.simpleapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.simpleapp.Model.State;
import org.springframework.stereotype.Repository;

@Repository

public interface StateRepo extends JpaRepository<State,Integer> {

}

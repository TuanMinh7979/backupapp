package com.simpleapp.map.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simpleapp.map.Model.User;
@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
}

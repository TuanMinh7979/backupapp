package com.simple.simpleapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple.simpleapp.Model.User;
@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
}

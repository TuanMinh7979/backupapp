package com.ivs.map.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivs.map.Model.User;
@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
}

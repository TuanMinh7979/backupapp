package com.simpleapp.map.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.simpleapp.map.Model.User;
import com.simpleapp.map.Repo.UserRepo;

@RestController
public class UserController {

	@Autowired
	private UserRepo userRepo ; 
	
	// return all User
	@GetMapping(value="/user")    
	public List<User> getListUser() {
		List<User> usr = userRepo.findAll(); 
		return usr ;
	}
	
	// add new user
	@PostMapping(value="/user")  
	public String createUser(@RequestBody User user) {
		userRepo.save(user); 
		return "Thêm thành user thành công" ; 
	}

	
	// get user with user_id
	@GetMapping(value="/user/{user_id}")
	public User getUser(@PathVariable String user_id) {
		User  usr =  userRepo.findById(user_id).get(); 
			return usr;
	}
	
	// delete user
	@DeleteMapping(value="/user/{user_id}")
	public String deleteUser(@PathVariable String user_id) {
		User usr =  userRepo.findById(user_id).get();
		userRepo.delete(usr);
		return "Đã xóa"; 
	}
	

}

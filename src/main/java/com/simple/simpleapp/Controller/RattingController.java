package com.simple.simpleapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.simpleapp.Model.Customer;
import com.simple.simpleapp.Model.Ratting;
import com.simple.simpleapp.Model.Trip;
import com.simple.simpleapp.Repo.AccountRepo;
import com.simple.simpleapp.Repo.CustomerRepo;
import com.simple.simpleapp.Repo.RattingRepo;
import com.simple.simpleapp.Repo.TripRepo;

import Embed.RattingId;

@Controller
@RequestMapping("/customer/ratting")
public class RattingController {
	private String message;
	@Autowired
	private RattingRepo rattingRepo ; 
	@Autowired
	private TripRepo tripRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private CustomerRepo customerRepo;
	
	@GetMapping()   // return list Rating
	String getListRatting(Model model) {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println(principal);
//        String username = ((UserDetails) principal).getUsername();
//        String phone = accountRepo.findById(username).get().getPhone();
        
        String phone="012963";
        
        Customer customer = customerRepo.findById(phone).get() ;
		Trip trip = tripRepo.findById((long) 17).get();
		
    	RattingId rattingId = new RattingId(customer,trip);
    	Ratting ratting = rattingRepo.findById(rattingId).get();
		
		
		
//		List<Ratting> rat = rattingRepo.findAll();
//		
//		if(rat.get(0).getRattingId().getCustomer().getPhone()==phone)
		
		 //model.addAttribute("list-ratting",rat);
		return ratting.getContent();
	}
	
	
	
	@PostMapping()  // add new rating
	String createRatting(@ModelAttribute Ratting ratting, Model model) {
		try {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
	        String username = ((UserDetails) principal).getUsername();
	        String phone = accountRepo.findById(username).get().getPhone();
	        
	        Customer customer = customerRepo.findById(phone).get() ;
	        
	        System.out.println("Star: "+ratting.getStar()+ " Content: "+ratting.getContent());
	        System.out.println("Trip id: " + ratting.getTripId());
			Trip trip = tripRepo.findById(ratting.getTripId()).get();
			//Set new rattingId with trip and customer
			RattingId rattingId = new RattingId(trip.getCustomer(), trip);
			ratting = new Ratting(rattingId,ratting.getContent(),ratting.getStar());
			//Add to rating repository
			rattingRepo.save(ratting);
			message = "Add new rating succesfully!";
			return "redirect:/customer";
			}catch (Exception e) {
				System.out.println(e);
				return "error-page";
			}
		}

	
//	@GetMapping(value="/{user_id}/{trip_id}")
//	ResponseEntity<Ratting> getRatting(@PathVariable String user_id,@PathVariable long trip_id) {
//		
//		List<Ratting> rattingList = rattingRepo.findAll();
//		for(int i =0; i<rattingList.size();i++) {
//			if(rattingList.get(i).getRatting_id().getCustomer().getPhone()==user_id) {
//				if(rattingList.get(i).getRatting_id().getTrip().getTripId()==trip_id) {
//					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rattingList.get(i));
//				}
//			}
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//		
//	}
	
//	
//	@DeleteMapping(value="/{user_id}/{trip_id}")
//	ResponseEntity<String> deleteRatting(@PathVariable String phone,@PathVariable long trip_id) {
//		
//		List<Ratting> rattingList = rattingRepo.findAll();
//		for(int i =0; i<rattingList.size();i++) {
//			if(rattingList.get(i).getRatting_id().getCustomer().getPhone()==phone) {
//				if(rattingList.get(i).getRatting_id().getTrip().getTripId()==trip_id) {
//					rattingRepo.delete(rattingList.get(i));
//					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Xoa thanh cong!");
//				}
//			}
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Xoa khong thanh cong!");
//		
//	}
//	
	

}

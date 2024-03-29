package com.simple.simpleapp.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.simple.simpleapp.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.simple.simpleapp.Model.Customer;
import com.simple.simpleapp.Model.Driver;
import com.simple.simpleapp.Model.Payment;
import com.simple.simpleapp.Model.State;
import com.simple.simpleapp.Model.Trip;
import com.simple.simpleapp.Repo.AccountRepo;
import com.simple.simpleapp.Repo.CustomerRepo;
import com.simple.simpleapp.Repo.DriverRepo;
import com.simple.simpleapp.Repo.PaymentRepo;
import com.simple.simpleapp.Repo.StateRepo;
import com.simple.simpleapp.Repo.TripRepo;

@Controller
public class TripController {
	@Autowired private TripRepo tripRepo;
	@Autowired private StateRepo stateRepo ;
	@Autowired private DriverRepo driverRepo;
	@Autowired private PaymentRepo paymentRepo;
	@Autowired private CustomerRepo customerRepo;
	@Autowired private AccountRepo  accountRepo;

	CustomerController customerController = new CustomerController();

	// add new trip
	@PostMapping(value = "/trip/add")
	ResponseEntity<String> createTrip(@RequestBody Trip trip, Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUserDetail userDetail = (CustomUserDetail) principal;
            System.out.println(userDetail);
	        String username = userDetail.getUsername();
	        System.out.println(username);
	        String phone = accountRepo.findById(username).get().getPhone();
	      
	        
	        System.out.println(phone);
			State st = stateRepo.findById(1).get();
			trip.setState(st);
			
			Payment payment = paymentRepo.findById(1).get();
			trip.setPayment(payment);
			Customer customer = customerRepo.findById(phone).get();
			trip.setCustomer(customer);
			

			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(now.format(dtf));
			trip.setDay_time(now);
			
			tripRepo.save(trip);
		return ResponseEntity.status(HttpStatus.OK).body("Them trip thanh cong!");
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
		}
	}

	
	@GetMapping("/driver/trip") // Trang hiển thị danh sách chuyến đi
	public String getListTrip(Model model) {
		List<Trip> listTrip = tripRepo.findAll();
		model.addAttribute("listTrip", listTrip);
		return "list-trip-driver";
	}
	
	
	
	@GetMapping (value="/driver/trip/list-trip-init")
	public String getListTripInit(Model model) {
		State st = stateRepo.findById(1).get() ; 
		List<Trip> listTrip = tripRepo.findByState(st);
		model.addAttribute("listTrip", listTrip);
		return "list-trip-init" ; 
	}
	
	
	@GetMapping(value = "/{trip_id}") // Tranh hiển thị chi tiết chuyến đi
	public String getTrip(@PathVariable long trip_id, Model model) {
		try {
			Trip t = tripRepo.findById(trip_id).get();
			model.addAttribute("trip", t);	
			return "list-detail";
		} catch (Exception e) {
			return "error-page";
		}

	}
	
	

	
	
	
	//Info driver
			@GetMapping(value = "/trip/infodriver/{trip_id}")
			public Driver getInfoDriver(@PathVariable long trip_id) {
				try {
					Driver t = tripRepo.findById(trip_id).get().getDriver();
					return t;
				} catch (Exception e) {
					Driver t = new Driver();
					return t;
				}
			}

}

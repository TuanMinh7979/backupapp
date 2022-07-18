package com.ivs.map.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.message.Message;
import Embed.RattingId;
import com.ivs.map.Model.Customer;
import com.ivs.map.Model.Ratting;
import com.ivs.map.Model.Trip;
import com.ivs.map.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator;
import com.ivs.map.Model.Account;
import com.ivs.map.Model.Chatting;
import com.ivs.map.Model.State;
import com.ivs.map.Model.User;
import com.ivs.map.Repo.TripRepo;
import com.ivs.map.mapsimulator.PlaceRepo;
import net.bytebuddy.agent.builder.AgentBuilder.InitializationStrategy.SelfInjection.Lazy;
import net.bytebuddy.description.annotation.AnnotationDescription.Latent;

import com.ivs.map.Repo.AccountRepo;
import com.ivs.map.Repo.CustomerRepo;
import com.ivs.map.Repo.RattingRepo;
import com.ivs.map.Repo.StateRepo;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
	private String phone_cancel_driver_socket;
	private boolean socket_cancel = false;
	private String msg;
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private TripRepo tripRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private RattingRepo rattingRepo;

	@Autowired
	private ChattingRepo chattingRepo;

	@GetMapping(value={"/{page}",""} )    // Hiển thị thông tin customer
	public String getCustomer(@PathVariable(name="page",required = false) Integer page,Model model ) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			System.out.println(principal);
			String username = ((UserDetails) principal).getUsername();
			String phone = accountRepo.findById(username).get().getPhone();

			//<-- customer info
			Customer customer = customerRepo.findById(phone).get() ;
			//customer info-->


			// list trip booked with pagination-->
			//List<Trip> listTrip = tripRepo.findByCustomerOrderByTripIdDecs(customer);
			if (page==null) page=1;
			Sort sort = Sort.by(Sort.Direction.DESC,"trip_id");
			Page<Trip> pageTrip = tripRepo.findByCustomer(customer.getPhone(),PageRequest.of(page-1, 6,sort));

			List<Trip> listTrip =pageTrip.getContent();

			model.addAttribute("totalPageTrip",pageTrip.getTotalPages());
			model.addAttribute("currentPage",page);
			//<--rating
			ArrayList<Ratting> rattings = new ArrayList<>();
			Ratting rat = new Ratting();
			rat.setRattingId(null);
			for(int i =0; i<listTrip.size();i++) {
				RattingId rattingId = new RattingId(customer,listTrip.get(i));
				if(rattingRepo.existsById(rattingId)) {
					System.out.println(listTrip.get(i).getTripId());
					if(listTrip.get(i).getState().getState_id()==3) {
						Ratting ratting = rattingRepo.findById(rattingId).get();
						rattings.add(ratting);
					}
				}
				else {
					rattings.add(rat);
				}
			}
			//rating-->

			//<--trip in live
			Trip trip = null;

			if(listTrip.size()!=0) {
				State st1 =  stateRepo.findById(1).get();
				State st2 =  stateRepo.findById(2).get();
				if (tripRepo.findByCustomerAndState(customer,st1).size()>0) {
					trip = tripRepo.findByCustomerAndState(customer,st1).get(0);
				} else if (tripRepo.findByCustomerAndState(customer,st2).size()>0){
					trip = tripRepo.findByCustomerAndState(customer,st2).get(0);
				}
				//trip = tripRepo.findById(listTrip.get(0).getTripId()).get();
			}
			//trip in live-->

			// get chat
			List<Chatting> listChat = chattingRepo.findByTrip(trip);

			//System.out.println(listChat.get(0).getContent());
			model.addAttribute("listChat", listChat);

			//socket_cancel
			model.addAttribute("socket_cancel", socket_cancel);
			socket_cancel = false;
			//phone driver of cancel trip socket
			model.addAttribute("phone_driver_socket_cancel",phone_cancel_driver_socket);


			model.addAttribute("message", msg);
			model.addAttribute("ratting", rattings);
			model.addAttribute("listTrip", listTrip);
			model.addAttribute("trip",trip);
			model.addAttribute("customer",customer);
			return "user-info";
		}catch(Exception e) {
			System.out.println(e);
			return e.toString() ;
		}
	}


	// update user info
	@PostMapping(value="/update")
	String updateCustomer(@ModelAttribute("customer") Customer customer) {
		try {

			customerRepo.save(customer);
			msg = "Update info succesfully";
			return "redirect:/customer";
		}catch(Exception e) {
			return e.toString();
		}
	}

	//cancel trip (setStateId = 4)
	@PostMapping(value="/trip/{trip_id}")
	public String cancelTrip(@ModelAttribute("trip") Trip trip,@PathVariable long trip_id) {
		try {
			if(tripRepo.findById(trip_id).get().getState().getState_id()==1) {
				trip = tripRepo.findById(trip_id).get() ;
				State state = stateRepo.findById(4).get();
				trip.setState(state);
				tripRepo.save(trip);
				msg="Cancel succesfully!";

			}else if(tripRepo.findById(trip_id).get().getState().getState_id()==2) {
				trip = tripRepo.findById(trip_id).get() ;
				State state = stateRepo.findById(4).get();
				trip.setState(state);
				tripRepo.save(trip);
				msg="Cancel succesfully!";
				socket_cancel = true;
				phone_cancel_driver_socket = trip.getDriver().getPhone();
			} else {
				msg="Cancel unsuccesfully!";
			}
			return "redirect:/customer" ;
		}catch(Exception e) {
			return "error-page" ;
		}
	}

	// delete user
	@DeleteMapping(value="/{phone}")
	public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
		String phone ="";
		customerRepo.deleteById(phone);
		return "Đã xóa";
	}
}
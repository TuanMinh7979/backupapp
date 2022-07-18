package com.ivs.map.Controller;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ivs.map.Model.Account;
import com.ivs.map.Model.Chatting;
import com.ivs.map.Model.Driver;
import com.ivs.map.Model.State;
import com.ivs.map.Model.Trip;
import com.ivs.map.Repo.AccountRepo;
import com.ivs.map.Repo.ChattingRepo;
import com.ivs.map.Repo.DriverRepo;
import com.ivs.map.Repo.StateRepo;
import com.ivs.map.Repo.TripRepo;

@Controller
@RequestMapping(value="driver")
public class DriverController {
	
	private String message;
	private String phone_customer_socket;
	private String phone_customer_socket_accept ; 
	
	private boolean socket = false;
	private boolean socketAccept = false;
	
	@Autowired
	private DriverRepo driverRepo ; 
	@Autowired
	private TripRepo tripRepo;
	@Autowired
	private StateRepo stateRepo ; 
	@Autowired
	private AccountRepo accountRepo ;
	
	@Autowired
	private ChattingRepo chattingRepo;
	
	
	
	@GetMapping(value= {"/{page}",""})    // Show information of driver
	public String getDriver(@PathVariable(name="page",required=false)Integer page, Model model ) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String username = ((UserDetails) principal).getUsername();
	        Account ac = accountRepo.findById(username).get() ;
	        
	        
			String driver_id = ac.getPhone() ;

			Driver dr = driverRepo.findById(driver_id).get() ;
			
			State st =  stateRepo.findById(2).get(); 
			

			//List<Trip> listTrip = tripRepo.findByDriver(dr);
			//PAGINATION
			if (page==null) page=1;
			Sort sort = Sort.by(Sort.Direction.DESC,"trip_id");
			Page<Trip> pageTrip = tripRepo.findByDriver(dr.getPhone(),PageRequest.of(page-1, 6,sort));
			
			List<Trip> listTrip =pageTrip.getContent();
			System.out.println(listTrip);
			model.addAttribute("totalPageTrip",pageTrip.getTotalPages());
			model.addAttribute("currentPage",page);
			try {
				Trip liveTrip = tripRepo.findByDriverAndState(dr,st).get(0);
				model.addAttribute("liveTrip", liveTrip);
				// get chat
				List<Chatting> listChat = chattingRepo.findByTrip(liveTrip);
				//System.out.println(listChat.get(0).getContent());
				model.addAttribute("listChat", listChat);
			}catch(Exception e) {
				model.addAttribute("message", "Chưa có cuốc nào nhận");	
			}
			

			if (!listTrip.isEmpty()) { 
				model.addAttribute("listTrip", listTrip);
			}
			
			
			model.addAttribute("socket", socket);
			socket = false;
			model.addAttribute("socketAccept", socketAccept);
			socketAccept = false ; 
			
			
			
			model.addAttribute("driver",dr);
			model.addAttribute("phone_customer_socket", phone_customer_socket);
			phone_customer_socket = "";
			model.addAttribute("phone_customer_socket_accept", phone_customer_socket_accept);
			phone_customer_socket_accept = "";
			model.addAttribute("message",message);
			return "driver-info" ; 
		}catch(Exception e) {
			System.out.println(e);
			return "error-page" ;
		}
	}
	
	//accept trip
			@GetMapping(value = "/trip/trip-accept/{trip_id}") 											
			public String acceptTrip(@PathVariable long trip_id, Model model) {

				try {

					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			        String username = ((UserDetails) principal).getUsername();
			        Account ac = accountRepo.findById(username).get() ;
			           
					String driver_phone = ac.getPhone();
					String kq = "";
					Trip t = tripRepo.findById(trip_id).get();
			
					
					if (t.getState().getState_id() == 4) {
						kq = "Customer canceled";
					}

					
					if (t.getState().getState_id() == 2) {
						kq = "This trip is recived by a other driver";
					}

					
					if (t.getState().getState_id() == 1) {
						
						State tt = stateRepo.findById(2).get();
						Driver dr = driverRepo.findById(driver_phone).get();
						List<Trip> listTripCheck =  tripRepo.findByDriverAndState(dr, tt) ;
						
						if(listTripCheck.size()==0) {
							t.setDriver(dr);
							t.setState(tt);
							tripRepo.save(t);
							kq = "Recived success";
							socketAccept = true ;
							phone_customer_socket_accept = t.getCustomer().getPhone();
						}else {
							kq = " Please complete the current trip " ;
							System.out.println(kq);
							System.out.println(listTripCheck.size());
						}
						
						
					}
					
					message = kq;
					return "redirect:/driver";
					
				} catch (Exception e) {
					return "error-page";
				}
			}
			
	@PostMapping(value = "/trip/TripComplete/{trip_id}") // Xác nhận hoàn thành chuyến đi của tài xế
	public String completeTrip(@PathVariable long trip_id, Model model) {
		System.out.println("CO VO DAY K");
		// Từ username đổi thành driver_phone
		try {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String username = ((UserDetails) principal).getUsername();
			Account ac = accountRepo.findById(username).get() ;
		
			String driver_phone =  ac.getPhone();
			message = "You are not the recipient of the trip";
			Trip t = tripRepo.findById(trip_id).get();
			
			
			if (t.getState().getState_id() == 2 && t.getDriver().getPhone().equals(driver_phone) ) {
				State tt = stateRepo.findById(3).get();
				t.setState(tt);
				tripRepo.save(t);
				message = "Complete the trip";
				socket = true;
				phone_customer_socket = t.getCustomer().getPhone();
			}
			return "redirect:/driver";
			
		} catch (Exception e) {
			
			return "error-page";
		}
	}

	
	
	@PostMapping(value="/update-info")    // Update driver info
	public String updateDriver(@ModelAttribute("driver") Driver driver , Model model ) {
		
		try {
			driverRepo.save(driver);
			message = "Update info successfully!!";
			return "redirect:/driver" ; 
		}catch(Exception e) {
			System.out.println(e);
			return "error-page" ;
		}
	}
	
	
	

	
	
	
	

}
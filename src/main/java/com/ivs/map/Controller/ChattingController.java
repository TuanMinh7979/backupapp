package com.ivs.map.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ivs.map.Model.Chatting;
import com.ivs.map.Model.Trip;
import com.ivs.map.Repo.ChattingRepo;
import com.ivs.map.Repo.TripRepo;

@Controller
public class ChattingController {
	@Autowired
	private ChattingRepo chattingRepo ; 
	
	@Autowired TripRepo tripRepo ; 
	
	@MessageMapping("/hello/customerchat/{phone}/trip/{trip_id}")
	@SendTo("/topic/hello/reciveCustomerChat/{phone}")
    public String userChat(@DestinationVariable String phone ,@DestinationVariable Long trip_id , String content) {
		try {
			
			Chatting chat = new Chatting(); 
			Trip t = tripRepo.findById(trip_id).get() ;	
			
			if(content != "") {
				chat.setTrip(t); 
				chat.setContent(content); 
				chat.setSendTo(true) ;
				chattingRepo.save(chat) ;
			}
			
			return content; 
		} catch (Exception e) {
			return e.toString() ;
		}	
    }
	
	
//	@MessageMapping("/hello/customerchat/{phone}")
//	@SendTo("/topic/hello/reciveCustomerChat/{phone}")
//	public String chatw( @DestinationVariable String phone,String name) {
//		return name;
//	}
	
	@MessageMapping("/hello/driverchat/{phone}/trip/{trip_id}")
	@SendTo("/topic/hello/reciveDriverChat/{phone}")
    public String driverChat(@DestinationVariable String phone,@DestinationVariable Long trip_id , String content) {
		try {

			Chatting chat = new Chatting();
			Trip t = tripRepo.findById(trip_id).get();

			if (content != "") {
				chat.setTrip(t);
				chat.setContent(content);
				chat.setSendTo(false);
				chattingRepo.save(chat) ;
			}

			return content;
		} catch (Exception e) {
			return "error message";
		}
	}
}

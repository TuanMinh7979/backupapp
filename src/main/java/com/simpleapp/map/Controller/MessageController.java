package com.simpleapp.map.Controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	@MessageMapping("/hello/{phone}")
	@SendTo("/topic/hello/{phone}")
    public String send(@DestinationVariable String phone, String name) {
		return "Trip completed!!"; 
    }
	
	
	
	@MessageMapping("/hello/accept/{phone}")
	@SendTo("/topic/hello/accept/{phone}")
    public String sendAccept(@DestinationVariable String phone, String name) {
		return "Trip recived by your driver!"; 
    }
	
	
	@MessageMapping("/hello")
	@SendTo("/topic/messages")
    public String send(String name) {
		return name; 
    }
//	
	
	
	
    
    @MessageMapping("/hello/cancel/{phone}")
    @SendTo("/topic/hello/cancel/{phone}")
    public String sends(@DestinationVariable String phone, String name) {
        return "This trip canceled by customer!";
    }
}

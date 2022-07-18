package com.ivs.map;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
 
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	private String client_socket="0129633216";
	public void setClientSocket(String client_socket) {
		this.client_socket = client_socket;
		System.out.println(client_socket);
	}
	public String getClientSocket() {
		return client_socket;
	}
		
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	String msgString = "/hello";
        registry.addEndpoint(msgString).withSockJS();
    }
 
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}

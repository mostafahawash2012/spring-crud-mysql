package com.task.customer.CustomerCRUD.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //enables WebSocket message handling, backed by a message broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// STOMP communication protocol 
	// The MessageBroker exposes an  endpoint  so that the  client can contact and form a connection
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//Destination (1) means the topic that the client can subscribe, when a topic has messages.
		//The messages will be sent to the clients which have subscribed this topic.
		registry.enableSimpleBroker("/topic");
		//Destination (2) means the places where the client can give messages to the WebSocket Server
		registry.setApplicationDestinationPrefixes("/app");
	}

	
}

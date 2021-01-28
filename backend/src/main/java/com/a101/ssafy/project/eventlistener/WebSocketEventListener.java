package com.a101.ssafy.project.eventlistener;

import org.codehaus.plexus.component.annotations.Component;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.a101.ssafy.project.model.chat.ChatMessage;
import com.a101.ssafy.project.model.chat.MessageType;


//@ComponentScan
@org.springframework.stereotype.Component
public class WebSocketEventListener {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@EventListener //ApplicationEvent를 상속받음
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		//ChatController의 addUser()에서 broadcast해서 사용자 참여 이벤트 알렸어서 여기는 그냥 sysout
		System.out.println("Received a new web socket connection");
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		
		//웹 소켓 세션에서 사용자 이름 추추랗고, 연결된 모든 클라이언트들에게 사용자 퇴장 이벤트 broadcast
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if(username!=null) {
			System.out.println("User disconnected: " +username);
			
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType(MessageType.LEAVE);
			chatMessage.setSender(username);
			
			messagingTemplate.convertAndSend("/a/bb", chatMessage);
		}
	}

}

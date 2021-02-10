package com.a101.ssafy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.a101.ssafy.project.model.chat.ChatMessage;


//WebSocketConfig 에서 /{prefix}로  시작하는 대상이 있는 클라이언트에서 보낸 모든 메시지는 @MessageMapping 으로 라우팅됨
//
@CrossOrigin(origins = { "*" })
@Controller
public class ChatController {
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate; 
	
	final String chatPrefix = "CHAT";
	
	static int temp = 10000;
//	 /api/msg/requestMessage <- message 브로커로 온다는거죠!
	@MessageMapping("/chat.sendMessage") // /prefix/cht.sendMsg 는 여기로 옴
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		temp+=10000;
		System.out.println("야옹");
		chatMessage.setContent(temp+"");
		System.out.println(chatMessage.getContent());
		return chatMessage;
	}
	//////////
	///////////
	////////////
	
	///
	//1. 가격 확인 
	//2. 가격갱신 (한단계 높게)
	////걸리는 억겁의시간의 렉이 걸리면  --> 실시간 처리를 했다고 해도 .... 또!!!! 똑같은 근본적 문제 발생.
	////이를 처리하기위해 service 클래스에 @transactional을 걸껀데. 이거 애노테이션을 좀더 공부해보고 이게 적저한지를 판단하는 작업 거쳐야함.
	//3. 다시 setContent로 가격을 높여주고 그 가격 보냄
	///
	
	////////////
	///////////
	////////////
	
	@MessageMapping("/chat.sendMessage2")
	public void sss(@Payload ChatMessage chatMessage) {
		System.out.println("sss함수를 탔습니다...");
		temp+=10000;
		chatMessage.setContent(temp+"");
		simpMessagingTemplate.convertAndSend("/topic/public"+"/3", chatMessage);
	}
	
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		System.out.println(chatMessage.getType()+"???");
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		
		System.out.println("기얏호웅");
		return chatMessage;
	}
	
}

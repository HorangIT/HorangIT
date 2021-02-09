package com.a101.ssafy.project.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.a101.ssafy.project.model.chat.ChatMessage;


//WebSocketConfig 에서 /{prefix}로  시작하는 대상이 있는 클라이언트에서 보낸 모든 메시지는 @MessageMapping 으로 라우팅됨
//
@Controller
public class ChatController {
	static int temp = 10000;
	// /api/msg/requestMessage <- message 브로커로 온다는거죠!
	@MessageMapping("/chat.sendMessage") // /prefix/cht.sendMsg 는 여기로 옴
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		temp+=10000;
		System.out.println("야옹");
		chatMessage.setContent(temp+"");
		System.out.println(chatMessage.getContent());
		return chatMessage;
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

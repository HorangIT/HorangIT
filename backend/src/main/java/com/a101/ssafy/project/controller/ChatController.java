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
	@MessageMapping("/{cht.sendMsg}")
	@SendTo("/.../...")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}
	
	@MessageMapping("/{~~}")
	@SendTo("/ttt/tttt")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender();
		return chatMessage;
	}
	
}

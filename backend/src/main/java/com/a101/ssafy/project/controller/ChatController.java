package com.a101.ssafy.project.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import com.a101.ssafy.project.model.chat.ChatMessage;
import com.a101.ssafy.project.model.chat.MessageType;
import com.a101.ssafy.project.redis.RedisUtil;


//WebSocketConfig 에서 /{prefix}로  시작하는 대상이 있는 클라이언트에서 보낸 모든 메시지는 @MessageMapping 으로 라우팅됨
//
@CrossOrigin(origins = { "*" })
@Controller
public class ChatController {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 맞출 포맷
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate; 
	
	@Autowired
	RedisUtil redisUtil;
	
	final String ITEM_CHAT_LOG_USER_ID = "itemChatUserId";
	final String ITEM_CHAT_LOG_USER_CONTENT = "itemChatUserContent";
	final String ITEM_CHAT_LOG_USER_TIME = "itemChatUserTime";
	final String ITEM_CHAT_LOG_USER_NICKNAME = "itemChatNickname";

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
	
	@MessageMapping("/chat.sendMessage/{itemId}")
	public void sss(@DestinationVariable("itemId")long itemId, @Payload ChatMessage chatMessage) {
		System.out.println("sss함수를 탔습니다...");
		chatMessage.setType(MessageType.REPLY);
		
		Date date = java.util.Calendar.getInstance().getTime();
		System.out.println(format.format(date));
        
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_ID+itemId, chatMessage.getSender());
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_CONTENT+itemId, chatMessage.getContent()+"");
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_TIME+itemId, format.format(date));
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_NICKNAME+itemId, redisUtil.getHdata("user", chatMessage.getSender())+"");
		
		simpMessagingTemplate.convertAndSend("/topic/chat/"+itemId, chatMessage);
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

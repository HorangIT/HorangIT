package com.a101.ssafy.project.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
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


/** 
 * @author 송은주(OctopusSwellfish)
 * 웹소켓으로 채팅을 구현하기 위한 클래스입니다.
 * 1:1채팅 및 물건 각자에 대한 전체 채팅에 대한 정보들을 저장하고 대응합니다.
 */
//WebSocketConfig 에서 /{prefix}로  시작하는 대상이 있는 클라이언트에서 보낸 모든 메시지는 @MessageMapping 으로 라우팅됨
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
	
	final String ROOM_CHAT_LOG_USER_ID = "roomChatUserId";
	final String ROOM_CHAT_LOG_USER_CONTENT = "roomChatUserContent";
	final String ROOM_CHAT_LOG_USER_TIME = "roomChatUserTime";
	final String ROOM_CHAT_LOG_USER_NICKNAME = "roomChatNickname";
	
	/** 
	 * 아이템 세부 페이지에 들어갔을 때 사람들끼리 채팅하는 함수 
	 * */
	@MessageMapping("/chat.sendMessage/{itemId}")
	public void sss(@DestinationVariable("itemId")long itemId, @Payload ChatMessage chatMessage) {
		chatMessage.setType(MessageType.REPLY);
		
		Date date = java.util.Calendar.getInstance().getTime();
        
		JSONObject jobj = new JSONObject();
		
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_ID+itemId, chatMessage.getSender());
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_CONTENT+itemId, chatMessage.getContent()+"");
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_TIME+itemId, format.format(date));
		redisUtil.setLdata(ITEM_CHAT_LOG_USER_NICKNAME+itemId, redisUtil.getHdata("user", chatMessage.getSender())+"");
		
		jobj.put("userNickname", redisUtil.getLastLdata(ITEM_CHAT_LOG_USER_NICKNAME+itemId).get(0));
		jobj.put("chatContent" ,redisUtil.getLastLdata(ITEM_CHAT_LOG_USER_CONTENT+itemId).get(0));
		jobj.put("chatCreatedAt", redisUtil.getLastLdata(ITEM_CHAT_LOG_USER_TIME+itemId).get(0));
		
		chatMessage.setContent(jobj);
		simpMessagingTemplate.convertAndSend("/topic/chat/"+itemId, chatMessage);
		
	}
	
	/** 
	 * 웹소켓과 처음 연결되고 유저가 들어올 때 대응하는 함수
	 * */
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		
		return chatMessage;
	}
	
	/** 
	 * 거래가 성사되고 나서 사람들끼리 1:1 채팅을 하는 함수 
	 * */
	@MessageMapping("/room.sendMessage/{itemId}")
	public void ddd(@DestinationVariable("itemId")long itemId, @Payload ChatMessage chatMessage) {
		System.out.println("오는지는 ?");
		Date date = java.util.Calendar.getInstance().getTime();
		
		JSONObject jobj = new JSONObject();
		
		redisUtil.setLdata(ROOM_CHAT_LOG_USER_ID+itemId, chatMessage.getSender());
		redisUtil.setLdata(ROOM_CHAT_LOG_USER_CONTENT+itemId, chatMessage.getContent()+"");
		redisUtil.setLdata(ROOM_CHAT_LOG_USER_TIME+itemId, format.format(date));
		redisUtil.setLdata(ROOM_CHAT_LOG_USER_NICKNAME+itemId, redisUtil.getHdata("user", chatMessage.getSender())+"");
		 
		jobj.put("userNickname", redisUtil.getLastLdata(ROOM_CHAT_LOG_USER_NICKNAME+itemId).get(0));
		jobj.put("chatContent" ,redisUtil.getLastLdata(ROOM_CHAT_LOG_USER_CONTENT+itemId).get(0));
		jobj.put("chatCreatedAt", redisUtil.getLastLdata(ROOM_CHAT_LOG_USER_TIME+itemId).get(0));
		
		chatMessage.setContent(jobj);
		simpMessagingTemplate.convertAndSend("/queue/room/"+itemId, chatMessage);
	}
	
}

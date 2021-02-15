package com.a101.ssafy.project.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.auction.AuctionInputDTO;
import com.a101.ssafy.project.model.chat.ChatMessage;
import com.a101.ssafy.project.model.chat.MessageType;
import com.a101.ssafy.project.service.AuctionService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/auction")
public class AuctionController {
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate; 
	
	AuctionService auctionService;
	
	@Autowired
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}
	
	@MessageMapping("/auction.sendMessage/{itemId}")
	public void onAuction(@DestinationVariable("itemId")long itemId, @Payload ChatMessage chatMessage) {
		String getCurrentExpiredValue = auctionService.getCurrentExpiredValue(itemId+"");
		
		if("null".equals(getCurrentExpiredValue)) {
			chatMessage.setContent((String)"이미 끝난 경매입니다.");
			simpMessagingTemplate.convertAndSend("/topic/auction/"+itemId, chatMessage);
			return;
		}
		if(chatMessage.getType()==MessageType.AUCTION) {
			chatMessage.setType(MessageType.REPLY);
			JSONObject jobj = auctionService.auction(chatMessage.getSender(), itemId+"");
			chatMessage.setContent(jobj);
			simpMessagingTemplate.convertAndSend("/topic/auction/"+itemId, chatMessage);
			return;
		}
		
		if(chatMessage.getType()==MessageType.FLEX){
			chatMessage.setType(MessageType.REPLY);
			JSONObject jobj = auctionService.flex(chatMessage.getSender(), itemId+"");			
			chatMessage.setContent(jobj);
			simpMessagingTemplate.convertAndSend("/topic/auction/"+itemId, chatMessage);
			return;
		}
		
	} 	
	
	// /action/log/{id} 응찰 내역을 가져옵니다ㅎㅎ
	@GetMapping("/log/{id}")
	public Object getAuctionLogForItem(@PathVariable("id")long id) {
		ResponseEntity responseEntity = null;
		BasicResponse result = new BasicResponse();
		
		List<String> list = auctionService.getAuctionLog(id+"");
		
		if(list.size()==0) {
			result.status = false;
			result.data = "해당 물품에 대해 아직 아무도 응찰하지 않았어요! 첫번째 주인공이 되어보세요!!";
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}else {
			JSONObject jobj = new JSONObject();
			jobj.put("log", list);

			result.status = true;
			result.data = "응찰 내역입니다.";
			result.object = jobj;
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/buyer/{userId}")
	public Object getReceiptBuyer(@PathVariable("userId")String userId) {
		
		return null;
	}
	@GetMapping("/seller/{userId}")
	public Object getReceiptSeller(@PathVariable("userId")String userId) {
		
		return null;
	}
}

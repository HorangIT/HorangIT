package com.a101.ssafy.project.controller;

import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.auction.AuctionInputDTO;
import com.a101.ssafy.project.model.chat.ChatMessage;
import com.a101.ssafy.project.model.chat.MessageType;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.repository.ItemRepository;
import com.a101.ssafy.project.repository.ReceiptRepository;
import com.a101.ssafy.project.service.AuctionService;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.ReceiptService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/auction")
public class AuctionController {
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate; 
	
	@Autowired
	AuctionService auctionService;
	
	@Autowired
	ReceiptService receiptService;
	
	@Autowired
	ItemService itemService;
		
	@Autowired
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}
	
	@Autowired
	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
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
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
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
	public Object getReceiptBuyer(@PathVariable("userId")long userId) {
		List<Receipt> list = receiptService.getReceiptByBuyerId(userId);
		
		BasicResponse result = new BasicResponse();
		
		JSONArray jarr = new JSONArray();
		result.status = true;
		
		if(list==null) {
			result.data = "조회된 데이터가 없습니다.";
		}else {
			result.data = "데이터 조회에 성공했습니다.";
			for(int i=0; i<list.size(); ++i) {
				Receipt receipt = list.get(i);
				JSONObject jobj = new JSONObject();
				
				jobj.put("itemId", receipt.getItemId());
				jobj.put("sellerId", receipt.getSellerId());
				jobj.put("buyerId", receipt.getBuyerId());
				jobj.put("itemTitle", receipt.getItemTitle());
				jobj.put("status", receipt.getStatus());
				jobj.put("message", "축하합니다!! " + receipt.getFinalPrice() + "원에 낙찰 받았습니다.");
				jobj.put("finalPrice", receipt.getFinalPrice());
				jarr.add(jobj);
				
			}
		}
		result.object = jarr;
		return new ResponseEntity(result, HttpStatus.OK);
	}
	@GetMapping("/seller/{userId}")
	public Object getReceiptSeller(@PathVariable("userId")long userId) {
		List<Receipt> list = receiptService.getReceiptBySellerId(userId);
		
		BasicResponse result = new BasicResponse();
		
		JSONArray jarr = new JSONArray();
		result.status = true;
		
		if(list==null) {
			result.data = "조회된 데이터가 없습니다.";
		}else {
			result.data = "데이터 조회에 성공했습니다.";
			for(int i=0; i<list.size(); ++i) {
				Receipt receipt = list.get(i);
				JSONObject jobj = new JSONObject();
				
				jobj.put("itemId", receipt.getItemId());
				jobj.put("sellerId", receipt.getSellerId());
				jobj.put("buyerId", receipt.getBuyerId());
				jobj.put("itemTitle", receipt.getItemTitle());
				jobj.put("status", receipt.getStatus());
				jobj.put("message", "등록하신 상품이 " + receipt.getFinalPrice() + "원에 낙찰되었습니다.");
				jobj.put("finalPrice", receipt.getFinalPrice());
				jarr.add(jobj);
				
			}
		}
		result.object = jarr;
		return new ResponseEntity(result, HttpStatus.OK);
		
	}
	
	@PatchMapping("/seller/{userId}/{itemId}")
	public Object sendItem(@PathVariable("userId")long userId, @PathVariable("itemId")long itemId) {
		
		BasicResponse result = new BasicResponse();
		
		Receipt receipt = receiptService.setStatusByItemId(itemId+"", 3);
		boolean updateItem = itemService.setStatusById(itemId, 3);
		
		if (receipt != null && updateItem) {
			
			result.data = "판매자가 배송을 했다!";
			result.status = true;
			result.object = null;
			
			return new ResponseEntity(result, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PatchMapping("/buyer/{userId}/{itemId}")
	public Object receiveItem(@PathVariable("userId")long userId, @PathVariable("itemId")long itemId) {
BasicResponse result = new BasicResponse();
		
		Receipt receipt = receiptService.setStatusByItemId(itemId+"", 4);
		boolean updateItem = itemService.setStatusById(itemId, 4);
		
		if (receipt != null && updateItem) {
			
			result.data = "구매자가 받았대!";
			result.status = true;
			result.object = null;
			
			return new ResponseEntity(result, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}

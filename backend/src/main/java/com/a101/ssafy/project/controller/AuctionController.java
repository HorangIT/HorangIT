package com.a101.ssafy.project.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
	AuctionService auctionService;
	
	@Autowired
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}
	
	@MessageMapping("/auction.sendMessage")
	public void onAuction(@Payload ChatMessage chatMessage) {
		chatMessage.setType(MessageType.REPLY);
		if(chatMessage.getType()==MessageType.AUCTION) {
//			JSONObject jobj = auctionService.auction(chatMessage.getSender(), chatMessage.getContent());
		}else if(chatMessage.getType()==MessageType.FLEX){
//			JSONObject jobj = auctionService.auction(chatMessage.getSender(), chatMessage.getContent());			
		}
		
	}
	
	/** 응찰을 시도했을 때  */
	@PostMapping
	public Object onAuction(@RequestBody AuctionInputDTO auctionInputDto) {
		return null;
		
//		ResponseEntity responseEntity = null;
//		BasicResponse result = null;
//		
//		String getCurrentExpiredValue = auctionService.getCurrentExpiredValue(auctionInputDto.getItemId());
//		String getCurrentAuctionValue = auctionService.getCurrentAuctionValue(auctionInputDto.getItemId()); 
//		if("null".equals(getCurrentExpiredValue)) {
//			result = new BasicResponse();
//			result.status = false;
//			result.data = "이미 끝난 경매입니당.";
//			
//			responseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
//		}else if(auctionInputDto.getNowPrice().equals(getCurrentExpiredValue)){ //같으면 결제 반려
//			result = new BasicResponse();
//			
//			result.status = false;
//			result.data = "음..이미 같은가격에 누가시도를 했다. 그래서 이 가격으론 안된다. 새로고침해라 이런의미";
//			
//			responseEntity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
// 		}else { //응찰 시도에 성공
// 			JSONObject jobj = new JSONObject();
//			JSONObject price = auctionService.getPriceAfterAuction(getCurrentAuctionValue, auctionInputDto.getItemId());
//			
//			result = new BasicResponse();
//			result.status = true;
//			result.data = "응찰에 성공하셨습니다.";
//			
//			//우리가 nowPrice도 받을 이유없어요.
//			//근데. nowPrice 안받고싶다? 그러면 아까 여러분이말씀해주신대로 redis에는 옛날값이 들어가는게 맞음. 그리고 그걸 꺼내서 다음가격 저장해주고 다다음가격 저장해서 보내줘야도니ㅡㄴ게 맞는거같아요.
//			//근데 지금 nowPrice 받기때문에 . 그냥 이거 받은값을 믿은상태로... 레디스에 저장하고 로그에저장하고 이거인듯 ?!
//			auctionService.addAuctionLog(auctionInputDto.getUserId(), auctionInputDto.getItemId(), auctionInputDto.getNowPrice());
//		
//			//이 부분은 한번 얘기해보자(json객체)
//			jobj.put("nextPrice", price.get("nextPrice"));
//			jobj.put("nowPrice", price.get("nowPrice"));
//			if(price.get("test")!=null) {
//				jobj.put("test", price.get("test"));
//			}
////			result.object = newPrice; //응찰 성공하고 나서 상품의 가격을 돌려줌
//			
//			if(price.get("test")!=null) {
//				//notification event 받기 (pub/sub) pattern 등록해서 하렴!
//				//이제사야해요!이게들어온건데
//				//이러면
//				
////				~~~Controller 하나의 공통의 컨트롤러 메소드를 타서
////				그 메소드는 무슨역할을 하냐면 : Expired됐을때나, 지금처럼 즉시낙찰 됐을때 동시에 영수증 발행되고 이걸 이어야되는 하나의 메소드(중간다리)만드는게 합당함.
//				
//			}
//			
//			//여기서 해당 메소드를 부르는데. JSONObject를 받아야되잖아요. 응찰 내역이 담긴.
//			List<String>list = auctionService.getAuctionLog(auctionInputDto.getItemId());
//			
//			jobj.put("log", list);
//			
//			result.object = jobj;
//			responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
//		}
//		
//		return responseEntity;
	}
	
	/** flex 해버렸을 때(즉시낙찰) */
	@PostMapping("/flex")
	public Object flex(@RequestBody AuctionInputDTO auctionInputDto) {
		ResponseEntity responseEntity = null;
		BasicResponse result = new BasicResponse();
		
		String getCurrentExpiredValue = auctionService.getCurrentExpiredValue(auctionInputDto.getItemId());

		//auctionService.addAuctionLog(auctionInputDto.getUserId(), auctionInputDto.getItemId(), auctionInputDto.getNowPrice());		
		if("null".equals(getCurrentExpiredValue)) {
			result = new BasicResponse();
			result.status = false;
			result.data = "이미 끝난 경매입니당.";
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}else {
			result = auctionService.flex(auctionInputDto.getItemId(), auctionInputDto.getUserId());
			responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		
		return responseEntity;
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
}

package com.a101.ssafy.project.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.auction.AuctionInputDTO;
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
	
	/** 응찰을 시도했을 때  */
	@PostMapping
	public Object onAuction(AuctionInputDTO auctionInputDto) {
		ResponseEntity responseEntity = null;
		BasicResponse result = null;
		
		String getCurrentExpiredValue = auctionService.getCurrentExpiredValue(auctionInputDto.getItemId());
		String getCurrentAuctionValue = auctionService.getCurrentAuctionValue(auctionInputDto.getItemId()); 
		if("null".equals(getCurrentExpiredValue)) {
			result = new BasicResponse();
			result.status = false;
			result.data = "ㅋㅋ이미끝났지롱!";
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}else if(auctionInputDto.getNowPrice().equals(getCurrentExpiredValue)){ //같으면 결제 반려
			result = new BasicResponse();
			
			result.status = false;
			result.data = "음..이미 같은가격에 누가시도를 했다. 그래서 이 가격으론 안된다. 새로고침해라 이런의미";
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
 		}else { //응찰 시도에 성공
			JSONObject newPrice = auctionService.getPriceAfterAuction(getCurrentAuctionValue, auctionInputDto.getItemId());
			result = new BasicResponse();
			result.status = true;
			result.data = "응찰에 성공하셨습니다.";
			
			result.object = newPrice; //응찰 성공하고 나서 상품의 가격을 돌려줌
			
			if(newPrice.get("test")!=null) {
				//이제사야해요!이게들어온건데
				//이러면
				
//				~~~Controller 하나의 공통의 컨트롤러 메소드를 타서
//				그 메소드는 무슨역할을 하냐면 : Expired됐을때나, 지금처럼 즉시낙찰 됐을때 동시에 영수증 발행되고 이걸 이어야되는 하나의 메소드(중간다리)만드는게 합당함.
				
			}
			

			responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
}

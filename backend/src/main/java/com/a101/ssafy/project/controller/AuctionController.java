package com.a101.ssafy.project.controller;

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
		
		String getCurrentAuctionValue = auctionService.getCurrentAuctionValue(auctionInputDto.getItemId());
		
		if("null".equals(getCurrentAuctionValue)) {
			result = new BasicResponse();
			result.status = false;
			result.data = "ㅋㅋ이미끝났지롱!";
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}else if(auctionInputDto.getNowPrice().equals(getCurrentAuctionValue)){ //같으면 결제 반려
			result = new BasicResponse();
			
			result.status = false;
			result.data = "음..이미 같은가격에 누가시도를 했다. 그래서 이 가격으론 안된다. 새로고침해라 이런의미";
			
			responseEntity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
 		}else { //응찰 시도에 성공
			
		}
//		
//		if(!auctionService.isAuctionNow(auctionInputDto.getItemId())) { //경매가 끝났거나, (시작이 안됐음 :나중에 생각해)
//			result = new BasicResponse();
//			result.status = false;
//			result.data = "ㅋㅋ이미끝났지롱!";
//			
//		}else { //null 이 아니고 무슨 값이 있는 상태
//			String nowPrice
//			result = auctionService.onAuction()
//			//일단은
//			//-1을 첨에 넣은이유가
//			//여기에 price로 갱신을 하기로 했었잖아요..
//			//근데 첨에는 -1이니까. 그때는 item에서 startPrice 그리고 happyPrice 를 받아와야되는데..
//			//
//			
//		}
		
		return responseEntity;
	}
	
}

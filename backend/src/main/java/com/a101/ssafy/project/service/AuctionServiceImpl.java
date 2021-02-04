package com.a101.ssafy.project.service;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.redis.RedisUtil;
import com.a101.ssafy.project.repository.UserRepository;

@Service
public class AuctionServiceImpl implements AuctionService{
	final String ITEM_NAME = "item";
	final String ITEM_EXPIRED = "Expired";
	final String ITEM_START_PRICE = "Start";
	final String ITEM_HAPPY_PRICE = "Happy";

	final String AUCTION = "auction";
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public String getCurrentAuctionValue(String itemId) {
		String value = redisUtil.getData(ITEM_NAME+itemId);
		
		return value==null?"null":value;
	}
	
	@Override
	public String getCurrentExpiredValue(String itemId) {
		String value = redisUtil.getData(ITEM_NAME+itemId+ITEM_EXPIRED);
		
		return value==null?"null":value;
	}

	@Override
	public long getAuctionUnit(String price) { //oldPrice: 응찰을 시도하려고 누른 프론트에서 받은 그가격!
		if("0".equals(price)) { //집 같은거(비싼거)올려놓고 시작가 0원으로 한다고 해도 팔리면 본인손해니까 이렇게해도됨 ㅋㅋ 내손해아님
			return 100L;
		}
		
		char firstChar = price.charAt(0);
		int length = price.length()-2;
		if(firstChar < '5') {
			double temp = Math.pow(10, length);
			
			return (long)temp;
		}else if('5'<=firstChar){
			double temp = Math.pow(10, length);
			
			return (long)(5*temp);
		}
		
		return 10L;
	}

	@Override
	public JSONObject getPriceAfterAuction(String oldPrice, String itemId) {
		if("-1".equals(oldPrice)) {
			oldPrice = redisUtil.getData(ITEM_NAME+itemId+ITEM_START_PRICE);
		}
		
		JSONObject jobj = new JSONObject();
		long newPrice = getAuctionUnit(oldPrice);
		newPrice += Long.parseLong(oldPrice);
		
		long nextPrice = newPrice+getAuctionUnit(newPrice+"");
		
		//happy price 보다 값이 커지는 경우
		long happyPrice = Long.parseLong(redisUtil.getData(ITEM_NAME+itemId+ITEM_HAPPY_PRICE));
		if(happyPrice < newPrice) {
			newPrice = happyPrice;
			jobj.put("test", "응찰 가격을 넘어섰네요  이제 사야해요");
//			done();
		}
//		String now = redisUtil.getData(ITEM_NAME+itemId);
		
		
		redisUtil.setData(ITEM_NAME+itemId, newPrice+""); //레디스 값 갱신
		
		jobj.put("nowPrice", newPrice);
		jobj.put("nextPrice", nextPrice);
		
		return jobj;
	}

	
	@Override
	public BasicResponse flex(String itemId, String userId) {
		BasicResponse result = new BasicResponse();
		
		result.status = true;
		result.data = "flex에 성공하셨습니다!";
		
		redisUtil.setData(ITEM_NAME+itemId, redisUtil.getData(ITEM_NAME+itemId+ITEM_HAPPY_PRICE));
		
//		done(itemId, userId);
		
		return result;
	}
	
	public void done() {
		System.out.println("결제하고");
		System.out.println("채팅창 서로 만들어주고(없으면안만듬)");
		System.out.println("영수증 발행하고");
		System.out.println("레디스 값 지우고(happy price/start price/ expired되지 않았다면 expired 지우고");	
	}

	@Override
	public JSONObject getAuctionLog(String itemId) {
		Map<Object, Object> hm = redisUtil.getAllHdata(AUCTION+itemId);
		if(hm.size()==0) {
			return null;
		}
		
		JSONObject jobj = new JSONObject();
		jobj.putAll(hm);
		return jobj;
	}

	@Override
	public String getNicknameById(String userId) {
		return (String)redisUtil.getHdata("user", userId);
	}

	@Override
	public void addAuctionLog(String userId, String itemId, String nowPrice) {
		String nickname = getNicknameById(userId);
		
		redisUtil.setHdata(AUCTION+itemId, nickname, nowPrice);
	}
	
	

}

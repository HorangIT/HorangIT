package com.a101.ssafy.project.service;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.redis.RedisUtil;

@Service
public class AuctionServiceImpl implements AuctionService{
	final String ITEM_NAME = "item";
	final String ITEM_EXPIRED = "Expired";
	@Autowired
	RedisUtil redisUtil;
	
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
		}else if('5'<firstChar){
			double temp = Math.pow(10, length);
			
			return (long)(5*temp);
		}
		
		return 10L;
	}

	@Override
	public JSONObject getPriceAfterAuction(String oldPrice, String itemId) {
		JSONObject jobj = new JSONObject();
		long newPrice = getAuctionUnit(oldPrice);
		newPrice += Long.parseLong(oldPrice);
		
		redisUtil.setData(ITEM_NAME+itemId, newPrice+""); //레디스 값 갱신
		
		jobj.put("nowPrice", newPrice);
		
		return jobj;		
	}

}

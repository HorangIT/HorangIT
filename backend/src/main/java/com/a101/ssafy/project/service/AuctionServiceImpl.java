package com.a101.ssafy.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.redis.RedisUtil;

@Service
public class AuctionServiceImpl implements AuctionService{
	final String ITEM_NAME = "item";
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public String getCurrentAuctionValue(String itemId) {
		String value = redisUtil.getData(ITEM_NAME+itemId);
		
		return value==null?"null":value;

	}

	@Override
	public long getAuctionUnit() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package com.a101.ssafy.project.service;

import org.json.simple.JSONObject;

import com.a101.ssafy.project.model.BasicResponse;

public interface AuctionService {
	String getCurrentAuctionValue(String itemId); //리턴값 : 가격(없으면 "null"옴)
	String getCurrentExpiredValue(String itemId);
	long getAuctionUnit(String price);
	JSONObject getPriceAfterAuction(String oldPrice, String itemId);
	BasicResponse flex(String itemId, String userId);
	JSONObject getAuctionLog(String itemId); //item Id를 받으면 해당 아이템에 대한 응찰 내역을 JSONObject 타입으로 돌려준다.
}

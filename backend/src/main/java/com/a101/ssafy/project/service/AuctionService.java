package com.a101.ssafy.project.service;

import com.a101.ssafy.project.model.BasicResponse;

public interface AuctionService {
	String getCurrentAuctionValue(String itemId); //리턴값 : 가격(없으면 "null"옴)
	long getAuctionUnit();

}

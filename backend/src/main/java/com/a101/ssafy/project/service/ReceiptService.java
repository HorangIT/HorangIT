package com.a101.ssafy.project.service;

import java.util.List;

import com.a101.ssafy.project.model.receipt.Receipt;

public interface ReceiptService {
	
	// Response Entity를 return하기 위해 object를 returning value로 지정
	Receipt createReceipt(String itemId);
	Receipt setStatusByItemId(String itemId, int status);
	
	List<Receipt> getReceiptBySellerId(long sellerId);
	List<Receipt> getReceiptByBuyerId(long buyerId);
	
	
}

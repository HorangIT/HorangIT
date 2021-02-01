package com.a101.ssafy.project.service;

//import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.receipt.ReceiptDto;

public interface ReceiptService {
	
	// Response Entity를 return하기 위해 object를 returning value로 지정
	Object createReceipt(ReceiptDto receiptDto);
	
}

package com.a101.ssafy.project.service;

import com.a101.ssafy.project.model.payment.PaymentReady;
import com.a101.ssafy.project.model.payment.PaymentApproved;
import com.a101.ssafy.project.model.payment.PaymentDto;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.receipt.ReceiptDto;

public interface PaymentService {

	// 결제 요청
	Object createPaymentRequest(PaymentDto paymentDto);
	
	// 결제 승인
	PaymentApproved approvePaymentRequest(String pg_token);
	
//	Object getPaymentById(Long id);
	
}

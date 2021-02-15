package com.a101.ssafy.project.service;

import com.a101.ssafy.project.model.payment.KakaopayResponse;
import com.a101.ssafy.project.model.payment.PaymentDto;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.receipt.ReceiptDto;

public interface PaymentService {

	Object createPaymentRequest(PaymentDto paymentDto);
	
	Object getPaymentById(Long id);
	
}
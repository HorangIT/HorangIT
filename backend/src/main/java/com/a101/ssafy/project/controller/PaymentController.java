package com.a101.ssafy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.service.PaymentService;

import lombok.extern.java.Log;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@GetMapping
	public void getPayment() {
		
	}
	
	@PostMapping
	public Object createPaymentRequest(String itemName, Long price, int buyerId) {
		
//		System.out.println(receiptDto.toString());
				
		return paymentService.createPaymentRequest(itemName, price, buyerId);
	}
	
	@GetMapping("/kakaoPaySuccess") 
	public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token) {
		
	}
}

package com.a101.ssafy.project.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.payment.PaymentApproved;
import com.a101.ssafy.project.model.payment.PaymentDto;
import com.a101.ssafy.project.model.payment.PaymentReady;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.PaymentService;
import com.a101.ssafy.project.service.ReceiptService;

import lombok.extern.java.Log;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	ReceiptService receiptService;
	
	@Autowired
	ItemService itemService;
	
	PaymentReady preparationResponse;
		
	@PostMapping
	public Object createPaymentRequest(@RequestBody PaymentDto paymentDto) {
		
		BasicResponse result = new BasicResponse();
		Object returnValue = paymentService.createPaymentRequest(paymentDto);
		
		if (returnValue != null) {
			result.status = true;
			result.object = returnValue;
			result.data = "결제 모달 열려라!";
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/success") 
	public Object paymentSuccess(@RequestParam("pg_token") String pg_token) {	
		System.out.println("start success");
		
		PaymentApproved approved = paymentService.approvePaymentRequest(pg_token);
		System.out.println(approved.getItem_code());
		
		// 성공시 status를 2로 바꾸기
		// itemId를 결제 초기부터 success까지 쭉 받아야함
		Receipt updatedStatus = receiptService.setStatusByItemId(approved.getItem_code(), 2);
		boolean updateStatus = itemService.setStatusById(Long.parseLong(approved.getItem_code()), 2);

		BasicResponse result = new BasicResponse();
		JSONObject jobj = new JSONObject();
		jobj.put("itemId", approved.getItem_code());
		jobj.put("itemName", approved.getItem_name());
		jobj.put("partnerID", approved.getPartner_user_id());
		jobj.put("paymentDate",approved.getApproved_at().toString());
		jobj.put("amount",approved.getAmount());
		jobj.put("paymentMethod", approved.getPayment_method_type());
		
		if (updateStatus) {
			result.status = true;
			result.object = jobj;
			result.data = "결제가 완료되었습니다.";
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
}

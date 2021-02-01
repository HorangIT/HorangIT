package com.a101.ssafy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.service.ReceiptService;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/receipt")
public class ReceiptController {
	
	@Autowired
	ReceiptService receiptService;
	
	@PostMapping
	public Object createReceipt(@RequestBody ReceiptDto receiptRequest) {
		
		System.out.println("receiptRequest: "+receiptRequest.toString());
				
		return receiptService.createReceipt(receiptRequest);
	}
	
}

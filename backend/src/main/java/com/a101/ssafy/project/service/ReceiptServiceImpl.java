package com.a101.ssafy.project.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.repository.ReceiptRepository;

@Service
public class ReceiptServiceImpl implements ReceiptService {
	
	@Autowired
	ReceiptRepository receiptRepository;
	
	@Override
	public Object createReceipt(ReceiptDto receiptDto) {
		
		Receipt createdReceipt = new Receipt();
		BeanUtils.copyProperties(receiptDto, createdReceipt);
		
		System.out.println("\n*******\ncreatedReceipt: "+createdReceipt.toString());
		
		receiptRepository.save(createdReceipt);
		
		BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "최종거래정보 등록";	
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}

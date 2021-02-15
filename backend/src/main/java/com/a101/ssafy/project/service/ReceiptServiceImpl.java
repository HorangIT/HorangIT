package com.a101.ssafy.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.redis.RedisUtil;
import com.a101.ssafy.project.repository.AlertRepository;
import com.a101.ssafy.project.repository.ItemRepository;
import com.a101.ssafy.project.repository.ReceiptRepository;

@Service
public class ReceiptServiceImpl implements ReceiptService {
	final String ITEM_NAME = "item";
	final String ITEM_EXPIRED = "Expired";
	final String AUCTION = "auction";
	
	@Autowired
	ReceiptRepository receiptRepository;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	AuctionService AuctionService;
	
	@Override
	public Object createReceipt(String itemId) {
		ReceiptDto receiptDto = new ReceiptDto();
		
		receiptDto.setItemId(Long.parseLong(itemId));
		
		
		
		
		return null;
		
		
////		ReceiptDto recipDto
//
//		Receipt createdReceipt = new Receipt();
////		BeanUtils.copyProperties(receiptDto, createdReceipt);
//		
//		System.out.println("\n*******\ncreatedReceipt: "+createdReceipt.toString());
//		
//		receiptRepository.save(createdReceipt);
//		
//		BasicResponse result = new BasicResponse();
//		result.status = true;
//		result.data = "최종거래정보 등록";	
//		
//		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	
	public Receipt setStatusByItemId(String itemId, int status) {
		Optional<Receipt> opt = receiptRepository.findByItemId(Long.parseLong(itemId));
		
		if(opt.isPresent()) {
			Receipt receipt = opt.get();
			receipt.setStatus(status);
			
			receiptRepository.save(receipt);
			
			return receipt;
		}else {
			return null;
		}
	}
}

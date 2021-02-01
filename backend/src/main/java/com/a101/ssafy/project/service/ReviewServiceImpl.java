package com.a101.ssafy.project.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.review.Review;
import com.a101.ssafy.project.model.review.ReviewDto;
import com.a101.ssafy.project.repository.ReceiptRepository;
import com.a101.ssafy.project.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	ReceiptRepository receiptRepository;

	@Override
	public Object createReview(ReviewDto reviewDto) {
		
		BasicResponse result = new BasicResponse();
		
		// 넘어오는 영수증의 id를 받아서 영수증 객체를 찾아낸 후에 review에 객체로 넣어줄거임
		Optional<Receipt> getReceipt = receiptRepository.findById(reviewDto.getReceiptId());
		
		// 없는 영수증이면 bad request
		if (!getReceipt.isPresent()) {
			result.status = false;
			result.data = "영수증 없음";
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		// 영수증이 있으면 .get()으로 우선 꺼내둔다
		Receipt receipt = getReceipt.get();
				
		// 이미 해당 영수증으로 리뷰를 적은 기록이 있다면 bad request
		if (reviewRepository.findByReceipt(receipt).isPresent()) {
			result.status = false;
			result.data = "이미 등록된 후기";
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		// 등록될 review 객체 생성
		Review createdReview = new Review();
		BeanUtils.copyProperties(reviewDto, createdReview);
		createdReview.setReceipt(receipt);
		
		System.out.println("\n*******\ncreatedReview: "+createdReview);
		
		reviewRepository.save(createdReview);
		
		result.status = true;
		result.data = "후기 등록 성공";	
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	

}

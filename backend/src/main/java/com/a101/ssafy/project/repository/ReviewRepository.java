package com.a101.ssafy.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.review.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	// 이미 후기가 있는 영수증인지 검색하기 위한 find함수
	Optional<Review> findByReceipt(Receipt receipt);
	
}

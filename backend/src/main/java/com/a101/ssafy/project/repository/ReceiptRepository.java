package com.a101.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a101.ssafy.project.model.receipt.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
		
}

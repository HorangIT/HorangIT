package com.a101.ssafy.project.model.payment;

import lombok.Data;

@Data
public class Amount {
	
	int total;		// 전체 결제 금액
	int tax_free;	// 비과세 금액
	int vat;		// 부과세 금액
	int point;		// 사용한 포인트 금액
	int discount;	// 할인 금액
}

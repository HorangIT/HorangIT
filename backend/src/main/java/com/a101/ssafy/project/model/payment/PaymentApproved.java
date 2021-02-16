package com.a101.ssafy.project.model.payment;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentApproved {
	
	String aid;	// 요청 고유 번호
	String tid; // 결제 고유 번호
	String cid; // 가맹점 코드
	
	String partner_order_id;	// 가맹점 주문번호
	String partner_user_id;		// 가맹점 회원 id
	
	String payment_method_type;	// 결제 수단 (CARD or MONEY)
	
	Amount amount;		// 결제 금액 정보
	CardInfo card_info;	// 결제 상세 정보(결제 수단이 카드인 경우만)
	
	String item_name;
	String item_code;
	String quantity;
	Date created_at;
	Date approved_at;
	
	String payload;		// 결제 승인 요청시 전달된 내용

}

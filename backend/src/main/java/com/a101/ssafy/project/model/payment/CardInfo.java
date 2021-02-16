package com.a101.ssafy.project.model.payment;

import lombok.Data;

@Data
public class CardInfo {
	
	String purchase_corp; 		// 카드사 한글명
	String purchase_corp_code;	// 카드사 코드
	String issuer_corp; 		// 카드 발급사 한글명
	String issuer_corp_code;	// 카드 발급사 코드
	
	String kakaopay_purchase_corp; 
	String kakaopay_purchase_corp_code; 
	String kakaopay_issuer_corp;
	String kakaopay_issuer_corp_code;
	
	String bin; 		// 카드 bin
	String card_type;	// 카드 타입
	
	String install_month;	// 할부 개월 수
	String approved_id;		// 카드사 승인번호
	String card_mid;		// 카드사 가맹점 번호
	String interest_free_install; //무이자 할부여부 (Y/N)
	String card_item_code;	// 카드 상품 코드
	
}

package com.a101.ssafy.project.model.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchDto {
		
//	long minPrice;
//	long maxPrice;
//	long currentPrice;
	
	// 주소
	String si;
	String gu;
	
	// 등급
	String grade;
	
	// 카테고리
	String category;
	
	// 경매 진행 여부 status
	boolean status;
	
	// 제품 이름으로 찾기
	String name;
}

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
		
	long minPrice;
	long maxPrice;
	
	// "location": ["서울특별시", "종로구"]
	String[] location;
	
	// "grade":["S","C","B"]
	char[] grade;
	
	long currentPrice;
	
	String[] category;
	
	// status
	int status;

}

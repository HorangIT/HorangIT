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
	long currentPrice;
	
	String si;
	String gu;
	
	// "grade":["S","C","B"]
	char[] grade;
	
	
	String[] category;
	
	// status
	boolean status;
	
	public Object getLocation(String si, String gu) {
		return this.si+" "+this.gu;
	}

}

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
	
	char[] grade;
	
	
}

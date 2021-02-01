package com.a101.ssafy.project.model.review;

import java.util.Date;

import javax.validation.Valid;

import com.a101.ssafy.project.model.receipt.Receipt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Valid
@Setter
@Getter
@ToString
public class ReviewDto {
	
	// 리뷰 작성을 위해 input으로 들어오는 값들:
	// 영수증id, 리뷰내용, 별점, 작성날짜 

	@ApiModelProperty(required = true)
	Long receiptId;
	
	@ApiModelProperty(required = true)
	String content;
	
	@ApiModelProperty(required = true)
	int starRating;
	
	@ApiModelProperty(required = true)
	Date createdAt;
	
}

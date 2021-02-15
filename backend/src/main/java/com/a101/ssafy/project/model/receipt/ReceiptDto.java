package com.a101.ssafy.project.model.receipt;

import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Valid
@Setter
@Getter
@ToString
public class ReceiptDto {
	
	@ApiModelProperty(required = true)
	Long itemId;
	
	@ApiModelProperty(required = true)
	Long buyerId;
	
	@ApiModelProperty(required = true)
	Long sellerId;
	
	@ApiModelProperty(required = true)
	long finalPrice;
	
	@ApiModelProperty(required = true)
	int status;
	
	@ApiModelProperty(required = true)
	String itemTitle;
}

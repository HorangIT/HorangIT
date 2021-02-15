package com.a101.ssafy.project.model.payment;

import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Valid
@Setter
@Getter
@ToString
public class PaymentDto {
	
	@ApiModelProperty(required = true)
	String name;
	
	@ApiModelProperty(required = true)
	Long price;
	
	@ApiModelProperty(required = true)
	Long buyerId;
	
	
}

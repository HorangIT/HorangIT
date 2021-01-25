package com.a101.ssafy.project.model.item;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Valid
@Setter
@Getter
@ToString
public class RegisterRequest {
	@ApiModelProperty(required = true)
	@NotNull
	String title;
	
	@ApiModelProperty(required = true)
//	@NotNull
	String description;
	
	@ApiModelProperty(required = true)
//	@NotNull
	String startPrice;
	
	@ApiModelProperty(required = true)
//	@NotNull
	String happyPrice;
	
	@ApiModelProperty(required = true)
	String grade;
	
	@ApiModelProperty(required = true)
	String direct;
	
	@ApiModelProperty(required = true)
	String startDate;
	
	@ApiModelProperty(required = true)
	String endDate;
	
	@ApiModelProperty(required = true)
	String category;
	
	//@ApiModelProperty(required = true)
	String[] image = new String[3];
	
	@ApiModelProperty(required = true)
	String userId;

}

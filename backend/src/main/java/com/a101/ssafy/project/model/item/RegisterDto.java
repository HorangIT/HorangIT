package com.a101.ssafy.project.model.item;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



/**
 * @author 문어복어(OctopusSwellfish)
 * 아이템을 등록하기 위한 DTO입니다.
 * 
 * 아이템 Entity에는 형식이 지정되어 있지만, 프론트단에서는 모두  String으로 보내주기로 약속했기 때문에,
 * 현재 DTO에 담고 아이템 Entity 형식으로 변환하는 작업이 필요합니다.
 *
 */
@Valid
@Setter
@Getter
@ToString
public class RegisterDto {
	@ApiModelProperty(required = true)
	@NotNull
	String title;
	
	@ApiModelProperty(required = true)
	String description;
	
	@ApiModelProperty(required = true)
	String startPrice;
	
	@ApiModelProperty(required = true)
	String happyPrice;
	
	@ApiModelProperty(required = true)
	String grade;
	
	@ApiModelProperty(required = true)
	String location;
	
	@ApiModelProperty(required = true)
	String direct;
	
	@ApiModelProperty(required = true)
	String startDateTime;
	
	@ApiModelProperty(required = true)
	String endDateTime;
	
	@ApiModelProperty(required = true)
	String category;
	
	@ApiModelProperty(required = true)
	String uid;
}

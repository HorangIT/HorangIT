package com.a101.ssafy.project.model.payment;

import java.util.Date;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Valid
@Setter
@Getter
@ToString
public class PaymentReady {
	
	//결제 고유 번호, 20자
	String tid;	
	
	// 카톡으로 결제 요청을 보내기위한 사용자 정보 입력화면 redirect url
	String next_redirect_pc_url;	
	
	// 결제 준비 요청 시간
	Date created_at;
		
}

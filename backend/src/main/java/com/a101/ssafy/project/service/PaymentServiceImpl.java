package com.a101.ssafy.project.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.payment.PaymentReady;
import com.a101.ssafy.project.model.payment.PaymentApproved;
import com.a101.ssafy.project.model.payment.PaymentDto;
import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.repository.ItemRepository;

import lombok.extern.java.Log;

@Service
@Log
public class PaymentServiceImpl implements PaymentService {
	
	// HOST + 요청보내는 url 방식으로 보낼 예정이기 때문에 앞부분 미리 static으로 박아둔다
	private static final String HOST = "https://kapi.kakao.com";

	// 결제 요청 준비 response
	PaymentReady paymentReady;
	
	// 결제 승인 response
	PaymentApproved paymentApproved;
	
	// 카카오페이 어드민 키
	@Value("${kakao.secret}") String key;

	// 결제 요청을 위한 준비단계
	// 요청 성공시 QR코드로 넘어감
	@Override
	public Object createPaymentRequest(PaymentDto paymentDto) {
		
		JSONObject jobj = new JSONObject();
		
		RestTemplate restTemplate = new RestTemplate();
		
		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		
		// 권한: 발급 받은 admin 키 포함시키기
		headers.add("Authorization", "KakaoAK " + key);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        // 필수 파라미터만 포함했음
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");		// 가맹점코드 : 테스트 결제 코드
        params.add("partner_order_id", "a101");	// 가맹점 주문번호
        params.add("partner_user_id", "호랑it");	// 가맹점 회원 id
        params.add("item_name", paymentDto.getName());		// 상품명
        params.add("item_code", paymentDto.getItemId().toString()); // 상품 코드
        params.add("quantity", "1");			// 상품 수량
        params.add("total_amount", paymentDto.getPrice()+""); // 상품 총액
        params.add("tax_free_amount", "0"); 	// 상품 비과세 금액
        
        // 성공 | 실패 | 취소 url
        params.add("approval_url", "http://i4a101.p.ssafy.io/payment/success");
        params.add("cancel_url", "http://i4a101.p.ssafy.io/payment/cancel");
        params.add("fail_url", "http://i4a101.p.ssafy.io/payment/fail");
//        params.add("approval_url", "http://localhost:8080/api/payment/success");
//        params.add("cancel_url", "http://localhost:8080/api/payment/cancel");
//        params.add("fail_url", "http://localhost:8080/api/payment/fail");
        
        // 헤더와 바디를 붙인다
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String,String>>(params, headers);
        
        try {
        	// RestTemplate을 이용해 카카오페이에 데이터를 보냄
        	// paymentDto.class = 카카오페이에서 받는 응답정보
        	paymentReady = restTemplate.postForObject(new URI(HOST +  "/v1/payment/ready"), body, PaymentReady.class);
        	
        	System.out.println(paymentReady.toString());
        	
        	// 결제가 성공했을 때 결제 준비 api 응답을 받는다 (QR 코드 찍는 화면)
        	jobj.put("successUrl", paymentReady.getNext_redirect_pc_url().toString());
        	return jobj;
        
        } catch (RestClientException e) {
			e.printStackTrace();
		}  catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
        // 여기는 에러났을 때
        return null;
        
	}


	@Override
	public PaymentApproved approvePaymentRequest(String pg_token) {
		System.out.println(paymentReady.getTid());
		JSONObject jobj = new JSONObject();
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "KakaoAK " + key);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");	
        params.add("tid", paymentReady.getTid().toString());
        params.add("partner_order_id", "a101");	
        params.add("partner_user_id", "호랑it");	
        params.add("pg_token", pg_token);		// 결제승인 요청을 인증하는 토큰
		
     // 헤더와 바디를 붙인다
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String,String>>(params, headers);
        
        try {

        	paymentApproved = restTemplate.postForObject(new URI(HOST +  "/v1/payment/approve"), body, PaymentApproved.class);       	

        	return paymentApproved;
        
        } catch (RestClientException e) {
			e.printStackTrace();
		}  catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
        // 여기는 에러났을 때
        return null;
	}
	
	
}

package com.a101.ssafy.project.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.a101.ssafy.project.model.payment.KakaopayResponse;
import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.repository.ItemRepository;

import lombok.extern.java.Log;

@Service
@Log
public class PaymentServiceImpl implements PaymentService {
	
	// HOST + 요청보내는 url 방식으로 보낼 예정이기 때문에 앞부분 미리 static으로 박아둔다
	private static final String HOST = "https://kapi.kakao.com";
	
	@Autowired
	ItemRepository itemRepository;
	
	KakaopayResponse kakaopayResponse;
	
	
	
	
	@Override
	public Object createPaymentRequest(String itemName, Long price, int buyerId) {
		
		JSONObject jobj = new JSONObject();
		
//		Optional<Item> tmpName = itemRepository.findById(receiptDto.getItemId());
//		String itemName = null;
//		if (tmpName.isPresent()) {
//			itemName = tmpName.toString();
//		}
//		
		RestTemplate restTemplate = new RestTemplate();
		
		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		
		// 권한: 발급 받은 admin 키 포함시키기
		headers.add("Authorization", "KakaoAK " + "b72064f93d9c3b3279646385be48d02d");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        // 필수 파라미터만 포함했음
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");		// 가맹점코드 : 테스트 결제 코드
        params.add("partner_order_id", "a101");	// 가맹점 주문번호
        params.add("partner_user_id", "a101");	// 가맹점 회원 id
        params.add("item_name", itemName+"");		// 상품명
        params.add("quantity", "1");			// 상품 수량
        params.add("total_amount", price+""); // 상품 총액
        params.add("tax_free_amount", "0"); 	// 상품 비과세 금액
        
        // 성공 | 실패 | 취소 url
        params.add("approval_url", "http://localhost:8000/payment/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8000/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8000/kakaoPaySuccessFail");
        
        // 헤더와 바디를 붙인다
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String,String>>(params, headers);
        
        try {
        	// RestTemplate을 이용해 카카오페이에 데이터를 보냄
        	// paymentDto.class = 카카오페이에서 받는 응답정보
        	kakaopayResponse = restTemplate.postForObject(new URI(HOST +  "/v1/payment/ready"), body, KakaopayResponse.class);
        	
//        	log.info(kakaopayResponse.toString());
        	System.out.println("");
        	System.out.println(kakaopayResponse.toString());
        	
        	jobj.put("successUrl", kakaopayResponse.getNext_redirect_pc_url().toString());
        	return jobj;
//        	return kakaopayResponse.getNext_redirect_pc_url();
        
        } catch (RestClientException e) {
			e.printStackTrace();
		}  catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
        // 여기는 에러났을 때
        return null;
        

	}

	@Override
	public Object getPaymentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

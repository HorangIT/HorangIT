package com.a101.ssafy.project.eventlistener;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.a101.ssafy.project.redis.RedisUtil;
import com.a101.ssafy.project.service.AuctionService;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.ReceiptService;

@Component
public class KeyExpiredListener implements MessageListener{
	ReceiptService receiptService;
	ItemService itemService;
	
	@Autowired
	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}
	
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	final String ITEM_HAPPY_PRICE = "Happy";

	//message: redis 에서 반환 한 알림
	//body: timeout 키의 이름
	//channel: timeout 이벤트
	
	//만료됐을 때. 키가 삭제됐을 때.
	@Override
	public void onMessage(Message message, byte[] pattern) {
		String msg = new String(message.getBody());
		String channel = new String(message.getChannel());
		
		switch(channel) {
		case "__keyevent@0__:expired":
			System.out.println("expiredEvent ?");
			
			break;
		case "__keyevent@0__:del":
			System.out.println("delEvent ?");

			if(msg.startsWith(ITEM_HAPPY_PRICE)) {
				//영수증 발행
				
			}
			break;
		}
		
			
			

		System.out.println("<<<<<<<<<<<<<<");
	}
 
}

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

/**
 * @author 송은주(OctopusSwellfish)
 * 
 * Redis에서  이벤트를 받아 이후 작업을 처리해주는 클래스입니다.
 * Key가 만료되거나 삭제되었을 때 이벤트를 수신해서 작업을 처리합니다.(RedisConfig.java)
 * 
 * RedisConfig에서 KeyExpiredListener를 Autowired해서 써주지 않으면,(즉 계속 new해서 쓰면)
 * 30~37번째의 Autowired가 먹히지 않습니다.(주의!!)
 * 
 */
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
	final String ITEM_EXPIRED = "Expired";

	
	//message: redis 에서 반환 한 알림
	//body: timeout 키의 이름
	//channel: timeout 이벤트
	/**
	 * 키가 만료되거나, 삭제되었을 때의 상황을 판별해서 각자의 로직을 수행하는 함수입니다.
	 * 한 번이라도 응찰을 시도했을 때, 아무도 응찰하지 않았을 때를 구분할 수 있어야 하므로 2가지의 갈래로 나눠서 수행합니다. 
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		String msg = new String(message.getBody());
		String channel = new String(message.getChannel());
		
		String itemId = "";
		switch(channel) {
		case "__keyevent@0__:expired": 
			itemId = msg.substring(7);
			System.out.println(itemId);
			itemService.setStatusById(Long.parseLong(itemId), 1);
			receiptService.createReceipt(itemId);
			
			break;
		case "__keyevent@0__:del":
			if(msg.startsWith(ITEM_HAPPY_PRICE)) {
				itemId = msg.substring(5);
				itemService.setStatusById(Long.parseLong(itemId), 1);
				receiptService.createReceipt(itemId);
			}
			break;
		}
	}
 
}

package com.a101.ssafy.project.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

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
	final String ITEM_EXPIRED = "Expired";

	//message: redis 에서 반환 한 알림
	//body: timeout 키의 이름
	//channel: timeout 이벤트
	
	//만료됐을 때. 키가 삭제됐을 때.
	@Override
	public void onMessage(Message message, byte[] pattern) {
		String msg = new String(message.getBody());
		String channel = new String(message.getChannel());
		
		String itemId = "";
		switch(channel) {
		case "__keyevent@0__:expired":
			itemId = msg.substring(7);
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

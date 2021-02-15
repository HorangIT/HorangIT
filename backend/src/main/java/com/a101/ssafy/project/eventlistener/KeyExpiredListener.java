package com.a101.ssafy.project.eventlistener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class KeyExpiredListener implements MessageListener{

	//message: redis 에서 반환 한 알림
	//body: timeout 키의 이름
	//channel: timeout 이벤트
	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] body = message.getBody();
//		byte[] channel = message.getChannel();
		System.out.println("Message>>>>>>>");
		System.out.println(new String(body));
//		System.out.println(new String(channel));
	
		System.out.println("<<<<<<<<<<<<<<");
	}
 
}

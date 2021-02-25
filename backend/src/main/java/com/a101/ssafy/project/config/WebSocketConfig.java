package com.a101.ssafy.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/** 
 * @author 송은주(OctopusSwellfish)
 * Websocket 연결 및 기본 설정을 위한 클래스
 * 
 */
@Configuration
@EnableWebSocketMessageBroker //websocket 서버를 활용하는데 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	//SockJS ==> 웹 소켓을 지원하지 않는 브라우저에 폴백 옵션을 활성화하는 데 사용(Fallback: 기능이 동작하지 않을 때 이에 대처하는 기능)
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		//"/{prefix}"로 시작되는 메시지가 message-handling methods로 라우팅 되어야 함
		
		registry.enableSimpleBroker("/topic", "/queue");
		//"/{주소}"로 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의
		//메시지 브로커는 특정 주제를 구독한 연결된 모든 클라이언트에게 메시지를 broadcast함
	}
}

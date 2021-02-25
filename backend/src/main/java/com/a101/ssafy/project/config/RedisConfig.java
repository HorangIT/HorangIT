package com.a101.ssafy.project.config;

import java.time.Duration;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import com.a101.ssafy.project.eventlistener.KeyExpiredListener;
/**
 * @author 송은주(OctopusSwellfish)
 * 레디스 환경설정을 위한 클래스입니다.
 * Lettuce를 이용해서 제작했습니다.
 * 메시지 리스너 추가 (단 레디스에서 환경설정을 추가로 해 줘야 함)
 */
@Configuration
public class RedisConfig {
	@Value("${redis.hostname}")
	private String hostName;
	
	@Value("${redis.port}")
	private int port;
	
	
	//KeyExpiredListener를 Autowired하지 않으면 이벤트를 처리하는 부분에서 다른 컴포넌트들을 Autowired로 받아올 수 없음
	@Autowired
	KeyExpiredListener keyExpiredListener;
	
	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofMinutes(1)) //connection time out
				.shutdownTimeout(Duration.ZERO) //redis client가 graceful 하게 close 될때까지의 timeout 설정(0일경우 제한 X)
				.build();
		
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);
		//redisStandaloneConfiguration.setPassword({password});
		
		return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
	}
	
	/** 레디스 키 삭제/키 만료 이벤트 리스너 등록을 위한 함수 
	 * 	0번 데이터베이스의 키 삭제/만료 이벤트를 수신하겠다는 의미
	 * */
	@Bean
	public RedisMessageListenerContainer getListenerContainer() {
		//create connection container
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		
		//put in redis connection
		container.setConnectionFactory(lettuceConnectionFactory());
		Topic topicExpired = new PatternTopic("__keyevent@0__:expired");
		Topic topicDel = new PatternTopic("__keyevent@0__:del");
		container.addMessageListener(keyExpiredListener, topicExpired);
		container.addMessageListener(keyExpiredListener, topicDel);
		return container;
	}
}

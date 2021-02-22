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
 * @author SongEunjoo
 * 레디스 환경설정을 위한 클래스
 * 
 */
@Configuration
public class RedisConfig {
	@Value("${redis.hostname}")
	private String hostName;
	
	@Value("${redis.port}")
	private int port;
	
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

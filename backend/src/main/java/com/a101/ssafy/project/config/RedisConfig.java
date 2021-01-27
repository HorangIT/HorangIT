package com.a101.ssafy.project.config;

import java.time.Duration;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {
    private final String HOSTNAME;
    private final int PORT;
    private final int DATABASE;
    private final String PASSWORD;
    private final long TIMEOUT;

    public RedisConfig(
        @Value("${redis.hostname}") String hostname,
        @Value("${redis.port}") int port,
        @Value("${redis.database}") int database,
        @Value("${redis.password}") String password,
        @Value("${redis.timeout}") long timeout
    ) {

        this.HOSTNAME = hostname;
        this.PORT = port;
        this.DATABASE = database;
        this.PASSWORD = password;
        this.TIMEOUT = timeout;
    }
    
    /** Redis 접속을 위한 RedisConnectionFactory BEAN */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(HOSTNAME);
        config.setPort(PORT);
        config.setDatabase(DATABASE);
        config.setPassword(PASSWORD);

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ofMillis(TIMEOUT))
            .build();

        return new LettuceConnectionFactory(config, clientConfig);
    }

    /** text를 저장하기 위한 StringRedisTemplate BEAN */
    @Bean
    public StringRedisTemplate stringRedisTemplate(
        @Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory
    ) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        return template;
    }
    
    /** 객체 저장하기 위한 message pack redis template BEAN */
    @Bean
    public RedisTemplate<String, byte[]> messagePackRedisTemplate(
        @Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory
    ) {

        RedisTemplate<String, byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setEnableDefaultSerializer(false);

        return template;
    }
    
    /** 객체 저장이나 조회 시 Serialize 위해 ObjectMapper 빈 작성*/
    @Bean
    public ObjectMapper messagePackObjectMapper() {
        return new ObjectMapper(new MessagePackFactory())
            .registerModule(new JavaTimeModule()) //날짜 지정 위해 JAVA TIME MODULE
            ;
//            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //TIMESTAMP형식으로 하는거 비활성화 옵션
    }
}

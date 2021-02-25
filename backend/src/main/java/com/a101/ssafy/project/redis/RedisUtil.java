package com.a101.ssafy.project.redis;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 송은주(OctopusSwellfish) 
 * This class can save data that type is {key, value}.
 * This class also can save Hash Data and List Data that is provided by Redis.
 * 
 *  We can use this as Service.
 *  You can check these functions in redis-cli.
 *  
 *  */
@Service
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * {key, value} 형태의 데이터를 가져오는 함수입니다.
     * null을 리턴할 수 있습니다.
     */
    public String getData(String key){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * {key, value} 형태의 데이터를 저장하는 함수입니다.
     */
    public void setData(String key, String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * {key, value} 형태의 데이터를 만료시간을 지정하여 저장하는 함수입니다.
     */
    public void setDataExpire(String key,String value,long duration){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }
    
    /**
     * {key, value} 형태의 데이터를 삭제하는 함수입니다.
     */
    public void deleteData(String key){
        stringRedisTemplate.delete(key);
    }
    
    /** 
     * Redis에 Hash데이터 구조로 데이터를 저장합니다.
     * redis Hash구조는 Map<Object, Map<Object, Object> > 의 구조로 저장 
     * 
     * */
    public void setHdata(String redisKey, String key, String value) {
    	HashOperations<String, Object, Object> valueOperations = stringRedisTemplate.opsForHash();    	
    	valueOperations.put(redisKey, key, value);
    }
    
    /**
     * Redis에 Hash데이터 구조에 있는 특정 Key값을 모두 조회합니다. 
     * redis Hash구조는 Map<Object, Map<Object, Object> > 의 구조로 저장
     * null을 리턴할 수 있습니다.
     */
    public Map<Object, Object> getAllHdata(String redisKey) {
    	HashOperations<String, Object, Object> valueOperations = stringRedisTemplate.opsForHash();
    	return valueOperations.entries(redisKey);
    }
    
    /**
     * Redis에 Hash데이터 구조에 있는 특정 Key값의 key값 1개를  조회합니다. 
     * redis Hash구조는 Map<Object, Map<Object, Object> > 의 구조로 저장
     * null을 리턴할 수 있습니다.
     */
    public Object getHdata(String redisKey, String key) {
    	HashOperations<String, Object, Object> valueOperations = stringRedisTemplate.opsForHash();
    	return valueOperations.get(redisKey, key);
    }
    
    /** 
     * Redis List데이터 구조로 저장하기 위한 함수입니다.
     *  
     * */
    public Long setLdata(String key, String value) {
    	ListOperations<String, String> valueOperations = stringRedisTemplate.opsForList();
    	return valueOperations.rightPush(key, value);
    }
    
    /** 
     * Redis List데이터 구조로 저장한 값을 모두 조회 위한 함수입니다.
     * null을 리턴할 수 있습니다.
     * */
    public List<String> getAllLdata(String key){
    	ListOperations<String, String> valueOperations = stringRedisTemplate.opsForList();
    	return valueOperations.range(key, 0, getLSize(key));
    }
    
    /** 
     * Redis List데이터 구조의 사이즈를 조회하기 위한 함수입니다.
     *  
     * */
    public Long getLSize(String key) {
    	ListOperations<String, String> valueOperations = stringRedisTemplate.opsForList();
    	return valueOperations.size(key);
    }
    
    /** 
     * Redis List데이터 구조의 마지막 정보만을 조회하기 위한 함수입니다.
     * null을 리턴할 수 있습니다.
     * */
    public List<String> getLastLdata(String key) {
    	ListOperations<String, String> valueOperations = stringRedisTemplate.opsForList();
    	if(getLSize(key)==0) {
    		return null;
    	}
    	else {
    		return valueOperations.range(key, -1, -1);
    	}
    }
}

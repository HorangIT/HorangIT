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


/** This class can save data that type is {key, value}.
 * 
 *  We can use this as Service.
 *  
 *  You can check these functions in redis-cli.
 *  */
@Service
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getData(String key){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    public void setDataExpire(String key,String value,long duration){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }
    
    public void deleteData(String key){
        stringRedisTemplate.delete(key);
    }
    
    /** redis Hash 구조 저장 및 조회를 위한 함수 3개 */
    public void setHdata(String redisKey, String key, String value) {
    	HashOperations<String, Object, Object> valueOperations = stringRedisTemplate.opsForHash();    	
    	valueOperations.put(redisKey, key, value);
    }
    
    public Map<Object, Object> getAllHdata(String redisKey) {
    	HashOperations<String, Object, Object> valueOperations = stringRedisTemplate.opsForHash();
    	return valueOperations.entries(redisKey);
    }
    
    public Object getHdata(String redisKey, String key) {
    	HashOperations<String, Object, Object> valueOperations = stringRedisTemplate.opsForHash();
    	return valueOperations.get(redisKey, key);
    }
    
    /** redis List 구조 저장 및 조회를 위한 함수 */
    public Long setLdata(String key, String value) {
    	ListOperations<String, String> valueOperations = stringRedisTemplate.opsForList();
    	return valueOperations.rightPush(key, value);
    }
    
    public List<String> getAllLdata(String key){
    	ListOperations<String, String> valueOperations = stringRedisTemplate.opsForList();
    	return valueOperations.range(key, 0, getLSize(key));
    }
    
    public Long getLSize(String key) {
    	ListOperations<String, String> valueOperations = stringRedisTemplate.opsForList();
    	return valueOperations.size(key);
    }
    
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
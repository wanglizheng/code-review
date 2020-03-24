package com.inspur.ihealth.codes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {
	@Autowired
	private RedisConnectionFactory factory;

	@Bean
	public RedisTemplate redisTemplate() {
		/*
		 * RedisTemplate redisTemplate = new RedisTemplate<>();
		 * redisTemplate.setKeySerializer(new StringRedisSerializer());
		 * redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		 * redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		 * redisTemplate.setValueSerializer(new StringRedisSerializer());
		 * redisTemplate.setConnectionFactory(factory); return redisTemplate;
		 * 
		 */
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
	}
	 
}

/*package com.todo.note.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.todo.note.noteservice.interceptor.Interceptor;
import com.todo.note.securityservice.jwt.JwtTokens;
import com.todo.note.userservice.model.User;
*//*************************************************************************************************************
*
* purpose:Redis configuration
* 
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
**************************************************************************************************//*
@ComponentScan("com.todo.note")
@Configuration
public class RedisConfig {

	@SuppressWarnings("deprecation")
	@Bean
	public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		return connectionFactory;
	}

	@Bean
	public RedisTemplate<String, User> redisTemplate() {
		RedisTemplate<String, User> redisTemplate = new RedisTemplate<String, User>();
		redisTemplate.setConnectionFactory(connectionFactory());
		return redisTemplate;
	}

	@Bean
	public JwtTokens getTokens() {

		return new JwtTokens();

	}
	@Bean
	public Interceptor interceptor() {
		return new Interceptor();
	}


}
*/
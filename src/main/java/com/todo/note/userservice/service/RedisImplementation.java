package com.todo.note.userservice.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.todo.note.securityservice.jwt.JwtTokens;
import com.todo.note.userservice.model.User;
@Repository
public class RedisImplementation implements RedisRepository<String, User>
{
	@Autowired
    JwtTokens tokens;
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    private static HashOperations<String, String, String> hashOperations;
    private static String KEY = "Token";


    @Autowired
    public RedisImplementation(RedisTemplate<String, User> redisTemplate) 
    {
        this.redisTemplate = redisTemplate;
    }
    

   /* public RedisImplementation() {
		super();
	}
*/

	private static Logger logger = LoggerFactory.getLogger(RedisRepository.class);

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    
    @Override
    public void setToken(String jwtToken) {
        String userId = tokens.parseJwt(jwtToken).getSubject();
        hashOperations.put(KEY, userId, jwtToken);
        logger.info("Token set in redis");
    }

    
    @Override
    public String getToken(String userId) {
        logger.info("Get token from redis");
        return hashOperations.get(KEY, userId);
    }

    
    

}

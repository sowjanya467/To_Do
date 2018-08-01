package com.todo.note.noteservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.todo.note.securityservice.jwt.JwtTokens;
/*************************************************************************************************************
*
* purpose:Interceptor
* 
* @author sowjanya467
* @version 1.0
* @since 19-07-18
*
**************************************************************************************************/



@Component
public class Interceptor extends HandlerInterceptorAdapter {
	
	JwtTokens jwtToken=new JwtTokens();
			
	public static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		logger.info("prehandling");
		String token = request.getHeader("token");
		String userId = jwtToken.parseJwt(token).getSubject();
		
		request.setAttribute("userId", userId);
		return true;

	}
	
	

	/*public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("After handling the request");
		super.postHandle(request, response, handler, modelAndView);
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("After rendering the view");
		super.afterCompletion(request, response, handler, ex);
	}*/
}

package com.todo.note.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.todo.note.noteservice.interceptor.Interceptor;

@Configuration
public class InterceptorWebConfiguration implements WebMvcConfigurer {

	@Autowired
	private Interceptor interceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( interceptor).addPathPatterns("/note/**");
	}

}

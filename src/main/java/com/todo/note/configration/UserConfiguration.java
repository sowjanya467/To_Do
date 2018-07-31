package com.todo.note.configration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todo.note.noteservice.interceptor.Interceptor;

/*************************************************************************************************************
 *
 * purpose:user configuration
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 19-07-18
 *
 **************************************************************************************************/

@Configuration
public class UserConfiguration {
	/**
	 * Password encoder
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * modelMapper configuration
	 * 
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Interceptor interceptor()
	{
		return new Interceptor();
	}
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
      Resource resource;
      String activeProfile;
      
      PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =  new PropertySourcesPlaceholderConfigurer();
      
      // get active profile
      activeProfile = System.getProperty("spring.profiles.active");
    
   
      // choose different property files for different active profile
      if ("development".equals(activeProfile)) {
        resource = new ClassPathResource("/META_INF/applicationDevelopment.properties");
        System.out.println(activeProfile+" profile selected");
      } else if ("test".equals(activeProfile)) {
        resource = new ClassPathResource("/META_INF/applicationTest.properties");
        System.out.println(activeProfile+" profile selected");
      } else {
        resource = new ClassPathResource("/META_INF/applicationProduction.properties");
        System.out.println(activeProfile+" profile selected");
      }
      
      // load the property file
      propertySourcesPlaceholderConfigurer.setLocation(resource);
      
      return propertySourcesPlaceholderConfigurer;
    }
}

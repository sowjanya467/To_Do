package com.todo.note.configration;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todo.note.noteservice.interceptor.Interceptor;
import com.todo.note.securityservice.jwt.JwtTokens;
import com.todo.note.userservice.model.User;

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
	public Interceptor interceptor() {
		return new Interceptor();
	}

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
	@Value("")
	String queueName;

	@Value("")
	String exchanger;

	@Value("")
	private String routingKey;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchanger);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setMessageConverter(jsonMessageConverter());
		return factory;
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		Resource resource;
		String activeProfile;

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

		// get active profile
		activeProfile = System.getProperty("spring.profiles.active");

		// choose different property files for different active profile
		if ("development".equals(activeProfile)) {
			resource = new ClassPathResource("/META_INF/applicationdevelopment.properties");
			System.out.println("currently in " + activeProfile + " profile");
		} else if ("test".equals(activeProfile)) {
			resource = new ClassPathResource("/META_INF/applicationtest.properties");
			System.out.println("currently in " + activeProfile + " profile");
		} else {
			resource = new ClassPathResource("/META_INF/applicationproduction.properties");
			System.out.println("currently in " + activeProfile + " profile");
		}

		// load the property file
		propertySourcesPlaceholderConfigurer.setLocation(resource);

		return propertySourcesPlaceholderConfigurer;
	}
}

package com.scheduler;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
	
	
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("test.exchange");
	}
	
	@Bean
	public Queue appQueue() {
		return QueueBuilder.durable("test.queue").build();
	}
	
	@Bean
	public Binding binding(Queue appQueue, DirectExchange directExchange) {
		return BindingBuilder.bind(appQueue).to(directExchange).with("test.queue");
	}
	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("test.topic.exchange");
	}
	
	@Bean
	public Queue topicQueueSucc() {
		return QueueBuilder.durable("topic.queue.succ").build();
	}
	
	@Bean
	public Queue topicQueueFail() {
		return QueueBuilder.durable("topic.queue.fail").build();
	}
	
	@Bean
	public Binding topicBindingSucc(Queue topicQueueSucc, TopicExchange topicExchange) {
		return BindingBuilder.bind(topicQueueSucc).to(topicExchange).with("success.*");
	}
	
	@Bean
	public Binding topicBindingFail(Queue topicQueueFail, TopicExchange topicExchange) {
		return BindingBuilder.bind(topicQueueFail).to(topicExchange).with("fail.*");
	}

	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) 
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
}

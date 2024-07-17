package com.saga.order_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderRabbitMQConfig {

    @Value("${order.queue}")
    private String orderQueue;

    @Value("${order.exchange}")
    private String orderExchange;

    @Value("${order.routingkey}")
    private String orderRoutingKey;

    @Bean
    public Queue orderQueue(){
        return new Queue(orderQueue);
    }

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with(orderRoutingKey);
    }



}

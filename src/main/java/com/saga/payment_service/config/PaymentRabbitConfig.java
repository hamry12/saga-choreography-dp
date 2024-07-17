package com.saga.payment_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRabbitConfig {

    @Value("${payment.queue}")
    private String paymentQueue;

    @Value("${payment.exchange}")
    private String paymentExchange;

    @Value("${payment.routingkey}")
    private String paymentRoutingKey;

    @Bean
    public Queue paymentQueue(){
        return new Queue(paymentQueue);
    }

    @Bean
    public TopicExchange paymentExchange(){
        return new TopicExchange(paymentExchange);
    }

    @Bean
    public Binding paymentBinding(){
        return BindingBuilder.bind(paymentQueue())
                .to(paymentExchange())
                .with(paymentRoutingKey);
    }
}

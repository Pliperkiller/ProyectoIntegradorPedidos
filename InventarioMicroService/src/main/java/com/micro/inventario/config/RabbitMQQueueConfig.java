package com.micro.inventario.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueueConfig {

    @Bean
    public Queue orderItemsQueue() {
        return new Queue("order_items_queue", true);
    }

    @Bean
    public Queue orderConfirmationQueue() {
        return new Queue("order_confirmation_queue", true);
    }
}
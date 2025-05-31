package com.micro.inventario.infrastructure.adapters.out.rabbitmq;

import com.micro.inventario.domain.ports.output.MessageBroker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitMQMessageBroker implements MessageBroker {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQMessageBroker(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(String queueName, Map<String, Object> message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("Mensaje enviado a la cola: " + queueName + " con contenido: " + message);
    }
}
package com.micro.inventario.infrastructure.adapters.out.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.inventario.domain.ports.output.MessageBroker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitMQMessageBroker implements MessageBroker {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQMessageBroker(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(String queueName, Map<String, Object> message) {
        try {
            //convertir el mensaje a JSON
            String jsonMessage = objectMapper.writeValueAsString(message);

            // enviar mensaje
            rabbitTemplate.convertAndSend(queueName, jsonMessage);
            System.out.println("Mensaje enviado a la cola: " + queueName + " con contenido: " + jsonMessage);
        } catch (Exception e) {
            System.err.println("Error al convertir el mensaje a JSON: " + e.getMessage());
        }
    }
}
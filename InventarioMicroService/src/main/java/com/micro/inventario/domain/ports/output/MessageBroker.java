package com.micro.inventario.domain.ports.output;

import java.util.Map;

public interface MessageBroker {
    void publish(String queueName, Map<String, Object> message);
}
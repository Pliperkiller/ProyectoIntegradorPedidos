package com.micro.inventario.infrastructure.adapters.in.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.inventario.domain.entities.ItemPedido;
import com.micro.inventario.domain.entities.Pedido;
import com.micro.inventario.application.services.ServicioInventario;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RabbitMQListener {

    private final ServicioInventario servicioInventario;
    private final ObjectMapper objectMapper;

    public RabbitMQListener(ServicioInventario servicioInventario, ObjectMapper objectMapper) {
        this.servicioInventario = servicioInventario;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "order_items_queue")
    public void listen(String message) {
        try {
            // Deserializar el mensaje JSON a un objeto PedidoMessage
            System.out.println("Mensaje recibido: " + message);
            PedidoMessage pedidoMessage = objectMapper.readValue(message, PedidoMessage.class);
            pedidoMessage.getItems().forEach(item -> System.out.println("Item recibido: productId=" + item.getProductId() + ", amount=" + item.getAmount()));

            // Convertir PedidoMessage a Pedido
            List<ItemPedido> items = pedidoMessage.getItems().stream()
                    .map(item -> new ItemPedido(item.getProductId(), item.getAmount()))
                    .collect(Collectors.toList());
            
            // Crear un objeto Pedido y validar los ingredientes
            Pedido pedido = new Pedido(pedidoMessage.getOrderId(), items);
            boolean valid = servicioInventario.validarIngredientes(pedido);

            // Procesar el pedido según la validación
            if (valid) {
                System.out.println("Ingredientes válidos para el pedido: " + pedido.getId());
                servicioInventario.procesarPedido(pedido);
                servicioInventario.confirmarPedido(pedido.getId());
            } else {
                System.out.println("Ingredientes insuficientes para el pedido: " + pedido.getId());
                servicioInventario.rechazarPedido(pedido.getId());
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }
}
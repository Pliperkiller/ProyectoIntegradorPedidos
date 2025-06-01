package com.micro.inventario.infrastructure.adapters.in.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.inventario.application.services.ServicioInventario;
import com.micro.inventario.domain.entities.Pedido;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RabbitMQListenerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ServicioInventario servicioInventario;

    /**
     * Tests the listen method when the ingredients are not valid for the order.
     * This test verifies that when the ServicioInventario.validarIngredientes method
     * returns false, indicating insufficient ingredients, the appropriate message is
     * printed to the console.
     */
    @Test
    public void testListenWithInvalidIngredients() throws Exception {

        ServicioInventario mockServicioInventario = Mockito.mock(ServicioInventario.class);
        ObjectMapper mockObjectMapper = Mockito.mock(ObjectMapper.class);
        // Creating the RabbitMQListener instance
        RabbitMQListener listener = new RabbitMQListener(mockServicioInventario, mockObjectMapper);
        List<PedidoMessage.ItemMessage> items = new ArrayList<>();
        items.add(new PedidoMessage.ItemMessage());

        // Mocking the behavior of validarIngredientes to return false
        when(mockServicioInventario.validarIngredientes(any(Pedido.class))).thenReturn(false);

        // Create and configure the mock PedidoMessage
        PedidoMessage mockPedidoMessage = Mockito.mock(PedidoMessage.class);
        when(mockPedidoMessage.getItems()).thenReturn(items);
        when(mockPedidoMessage.getOrderId()).thenReturn(123L);

        // Mock the ObjectMapper to return our mock PedidoMessage
        when(mockObjectMapper.readValue(anyString(), eq(PedidoMessage.class))).thenReturn(mockPedidoMessage);

        // Prepare test data
        String message = "{\"orderId\":\"123\",\"items\":[{\"productId\":\"1\",\"amount\":2}]}";

        // Redirect System.out to capture printed messages
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Call the method under test
        listener.listen(message);

        // Verify that the correct message was printed
        String expectedOutput = "Ingredientes insuficientes para el pedido: 123";
        assert(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    /**
     * Tests the behavior of the listen method when an invalid JSON message is provided.
     * This test verifies that the method handles the exception thrown by ObjectMapper
     * when trying to parse an invalid JSON string.
     */
    @Test
    public void testListenWithInvalidJsonMessage() throws JsonProcessingException {

        ObjectMapper mockObjectMapper = Mockito.mock(ObjectMapper.class);
        // Creating the RabbitMQListener instance
        RabbitMQListener rabbitMQListener = new RabbitMQListener(servicioInventario, mockObjectMapper);

        // Invalid JSON message
        String invalidJsonMessage = "{ invalid json }";

        // Execute the method
        rabbitMQListener.listen(invalidJsonMessage);

        // Verify that the error was logged (indirectly, since we can't mock System.err directly)
        verify(mockObjectMapper, times(1)).readValue(eq(invalidJsonMessage), eq(PedidoMessage.class));
    }

    /**
     * Test case for the listen method when the ingredients are valid.
     * This test verifies that the method correctly processes a valid order message,
     * calls the servicioInventario to validate ingredients, and logs the appropriate message.
     */
    @Test
    public void testListenWithValidIngredients() throws Exception {
        // Mocking dependencies
        ServicioInventario servicioInventario = Mockito.mock(ServicioInventario.class);
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);

        // Creating the RabbitMQListener instance
        RabbitMQListener listener = new RabbitMQListener(servicioInventario, objectMapper);

        // Preparing test data
        String message = "{\"orderId\":\"123\",\"items\":[{\"productId\":\"1\",\"amount\":2}]}";
        PedidoMessage pedidoMessage = new PedidoMessage();
        List<PedidoMessage.ItemMessage> items = new ArrayList<>();
        items.add(new PedidoMessage.ItemMessage());
        items.add(new PedidoMessage.ItemMessage());
        pedidoMessage.setItems(items);
        pedidoMessage.setOrderId(123L);
        pedidoMessage.setItems(java.util.Collections.singletonList(pedidoMessage.getItems().get(0)));

        // Mocking behavior
        when(objectMapper.readValue(message, PedidoMessage.class)).thenReturn(pedidoMessage);
        when(servicioInventario.validarIngredientes(any(Pedido.class))).thenReturn(true);

        // Executing the method
        listener.listen(message);

        // Verifying the interactions
        verify(servicioInventario).validarIngredientes(any(Pedido.class));
    }

    /**
     * Tests the behavior of the RabbitMQListener when an invalid JSON message is received.
     * This test verifies that the listener properly handles and logs the exception
     * when it fails to parse the incoming message.
     */
    @Test
    public void testRabbitMQListener_InvalidJsonMessage() throws JsonProcessingException {
        ObjectMapper mockObjectMapper = Mockito.mock(ObjectMapper.class);
        ServicioInventario mockServicioInventario = Mockito.mock(ServicioInventario.class);
        RabbitMQListener listener = new RabbitMQListener(mockServicioInventario, mockObjectMapper);
        String invalidJsonMessage = "This is not a valid JSON";

        when(mockObjectMapper.readValue(invalidJsonMessage, PedidoMessage.class))
                .thenThrow(new RuntimeException("Invalid JSON"));

        listener.listen(invalidJsonMessage);

        // Verify that no further processing occurs after the exception
        verify(mockServicioInventario, never()).validarIngredientes(any());
    }

    /**
     * Test case for RabbitMQListener constructor
     * Verifies that the RabbitMQListener is correctly initialized with the provided dependencies
     */
    @Test
    public void test_RabbitMQListener_Constructor() {
        ServicioInventario mockServicioInventario = Mockito.mock(ServicioInventario.class);
        ObjectMapper mockObjectMapper = Mockito.mock(ObjectMapper.class);

        RabbitMQListener listener = new RabbitMQListener(mockServicioInventario, mockObjectMapper);

        // Assert that the listener is not null, indicating successful initialization
        org.junit.jupiter.api.Assertions.assertNotNull(listener);
    }

}

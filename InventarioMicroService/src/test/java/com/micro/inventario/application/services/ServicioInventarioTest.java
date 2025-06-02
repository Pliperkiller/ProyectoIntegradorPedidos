package com.micro.inventario.application.services;

import com.micro.inventario.application.ports.out.ActualizarInventarioPort;
import com.micro.inventario.application.ports.out.ObtenerIngredientePort;
import com.micro.inventario.application.ports.out.ObtenerProductoPort;
import com.micro.inventario.domain.entities.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import com.micro.inventario.domain.ports.output.MessageBroker;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServicioInventarioTest {


    @Mock
    private MessageBroker messageBroker;
    @Mock
    private ActualizarInventarioPort actualizarInventarioPort;

    @Mock
    private ObtenerIngredientePort obtenerIngredientePort;

    @Mock
    private ObtenerProductoPort obtenerProductoPort;

    @InjectMocks
    private ServicioInventario servicioInventario;

    /**
     * Tests the procesarPedido method when validarIngredientes returns false.
     * Verifies that the method returns false in this case.
     */
    @Test
    public void testProcesarPedidoWhenValidarIngredientesFails() {
        ServicioInventario servicioInventario = Mockito.mock(ServicioInventario.class);
        Pedido pedido = new Pedido(null, Collections.emptyList());

        Mockito.when(servicioInventario.validarIngredientes(pedido)).thenReturn(false);
        Mockito.when(servicioInventario.procesarPedido(pedido)).thenCallRealMethod();

        Boolean result = servicioInventario.procesarPedido(pedido);

        assertFalse(result);
        Mockito.verify(servicioInventario).validarIngredientes(pedido);
    }

    /**
     * Tests the constructor of ServicioInventario to ensure it properly initializes the object
     * with the provided port interfaces.
     */
    @Test
    public void testServicioInventarioConstructor() {
        // Mock the required ports
        ObtenerProductoPort mockObtenerProductoPort = Mockito.mock(ObtenerProductoPort.class);
        ObtenerIngredientePort mockObtenerIngredientePort = Mockito.mock(ObtenerIngredientePort.class);
        ActualizarInventarioPort mockActualizarInventarioPort = Mockito.mock(ActualizarInventarioPort.class);

        // Create an instance of ServicioInventario using the constructor
        ServicioInventario servicioInventario = new ServicioInventario(
            mockObtenerProductoPort,
            mockObtenerIngredientePort,
            mockActualizarInventarioPort,
                null
        );

        // Assert that the object is not null (basic check to ensure object creation)
        org.junit.jupiter.api.Assertions.assertNotNull(servicioInventario);
    }

    /**
     * Tests the validarIngredientes method when an ingredient is not found or has insufficient stock.
     * This test verifies that the method returns false when either condition is met.
     */
    @Test
    public void testValidarIngredientesWhenIngredientNotFoundOrInsufficientStock() {

        // Creating test data
        ItemPedido item = new ItemPedido(1L, 1);
        Pedido pedido = new Pedido(1L, Collections.singletonList(item));
        Map<Long, Integer> ingredientesMap = new HashMap<>();
        ingredientesMap.put(1L, 3);
        Receta receta = new Receta(1L, 1L, ingredientesMap);
        Producto producto = new Producto(1L, "Hamburguesa", receta);
        Map<Long, Integer> ingredientesRequeridos = new HashMap<>();
        ingredientesRequeridos.put(1L, 3);

        // Mocking behavior
        when(obtenerIngredientePort.obtenerIngredientePorId(1L)).thenReturn(Optional.empty());
        when(obtenerProductoPort.ObtenerProductoPorId(1L)).thenReturn(Optional.ofNullable(producto));

        // Executing the method
        boolean result = servicioInventario.validarIngredientes(pedido);

        // Asserting the result
        assertFalse(result);
    }

    /**
     * Test case for processing a pedido when there are insufficient ingredients.
     * This test verifies that the procesarPedido method returns false when
     * validarIngredientes returns false, indicating that there are not enough
     * ingredients available to fulfill the order.
     */
    @Test
    public void test_procesarPedido_insufficient_ingredients() {
        // Arrange
        ItemPedido item = new ItemPedido(1L, 1);
        Pedido pedido = new Pedido(4L, Collections.singletonList(item));

        // Create a spy of the service
        ServicioInventario spyService = spy(servicioInventario);

        // Mock the validarIngredientes method
        doReturn(false).when(spyService).validarIngredientes(any(Pedido.class));

        // Act
        Boolean result = spyService.procesarPedido(pedido);

        // Assert
        assertFalse(result);
        verify(spyService).validarIngredientes(any(Pedido.class));
        verifyNoMoreInteractions(actualizarInventarioPort);
    }

    /**
     * Test case for procesarPedido method when ingredients are valid
     * This test verifies that the method returns true when validarIngredientes returns true
     */
    @Test
    public void test_procesarPedido_whenIngredientsAreValid() {
        // Arrange
        ItemPedido item = new ItemPedido(1L, 1);
        Pedido pedido = new Pedido(3L, Collections.singletonList(item));
        
        // Create a spy of the service
        ServicioInventario spyService = spy(servicioInventario);
        
        // Mock the validarIngredientes method
        doReturn(true).when(spyService).validarIngredientes(any(Pedido.class));
        
        // Act
        Boolean result = spyService.procesarPedido(pedido);
        
        // Assert
        assertTrue(result);
    }

    /**
     * Tests the validarIngredientes method when all ingredients are available in sufficient stock.
     * This test case covers the scenario where the method should return true.
     */
    @Test
    public void test_validarIngredientes_whenAllIngredientsAvailable() {
        // Creating test data
        ItemPedido item = new ItemPedido(1L, 2);
        Pedido pedido = new Pedido(1L,Arrays.asList(item) );// Assuming ItemPedido constructor takes idProducto and cantidad

        Ingrediente ingrediente = new Ingrediente(1L, "Lechuga", 10);
        Map<Long, Integer> ingredientesMap = new HashMap<>();
        ingredientesMap.put(1L, 3);
        Receta receta = new Receta(1L, 1L, ingredientesMap);
        Producto producto = new Producto(1L, "Hamburguesa", receta);// Ingredient 1, quantity 3


        // Mocking behavior
        Mockito.when(obtenerProductoPort.ObtenerProductoPorId(1L)).thenReturn(Optional.of(producto));
        Mockito.when(obtenerIngredientePort.obtenerIngredientePorId(1L)).thenReturn(Optional.of(ingrediente));

        // Executing the method
        boolean result = servicioInventario.validarIngredientes(pedido);

        // Asserting the result
        assertTrue(result);
    }


    /**
     * Tests the confirmarPedido method to ensure it publishes the correct message to the message broker.
     * Verifies that the method creates a message with the correct orderId and status,
     * and publishes it to the "order_confirmation_queue".
     */
    @Test
    public void testConfirmarPedidoPublishesCorrectMessage() {
        Long orderId = 123L;
        String queueName = "order_confirmation_queue";
        Map<String, Object> expectedMessage = new HashMap<>();
        expectedMessage.put("orderId", orderId);
        expectedMessage.put("status", 1);

        servicioInventario.confirmarPedido(orderId);

        verify(messageBroker).publish(queueName, expectedMessage);
    }

    /**
     * Tests the procesarPedido method when ingredients are valid and inventory is updated successfully.
     * This test verifies that the method returns true when the order can be processed.
     */
    @Test
    public void testProcesarPedidoWhenIngredientsValidAndInventoryUpdated() {
        // Arrange
        Pedido pedido = new Pedido(1L, Collections.singletonList(new ItemPedido(1L, 1)));

        when(obtenerProductoPort.ObtenerProductoPorId(1L)).thenReturn(Optional.of(new Producto(1L, "Test Product", new Receta(1L, 1L, Collections.singletonMap(1L, 1)))));
        when(obtenerIngredientePort.obtenerIngredientePorId(1L)).thenReturn(Optional.of(new Ingrediente(1L, "Test Ingredient", 10)));

        // Act
        Boolean result = servicioInventario.procesarPedido(pedido);

        // Assert
        assertTrue(result);
        verify(actualizarInventarioPort, times(1)).actualizarIngrediente(any(Ingrediente.class));
    }

    /**
     * Tests the rechazarPedido method to ensure it publishes the correct message to the message broker.
     * Verifies that the method creates the expected message with orderId and status,
     * and publishes it to the correct queue.
     */
    @Test
    public void testRechazarPedidoPublishesCorrectMessage() {
        Long orderId = 123L;
        String expectedQueueName = "order_confirmation_queue";
        Map<String, Object> expectedMessage = new HashMap<>();
        expectedMessage.put("orderId", orderId);
        expectedMessage.put("status", 0);

        servicioInventario.rechazarPedido(orderId);

        verify(messageBroker).publish(expectedQueueName, expectedMessage);
    }

    /**
     * Tests the constructor of ServicioInventario to ensure it properly initializes
     * the object with all required dependencies.
     */
    @Test
    public void testServicioInventarioConstructorWithAllDependencies() {
        ServicioInventario servicioInventario = new ServicioInventario(
                obtenerProductoPort,
                obtenerIngredientePort,
                actualizarInventarioPort,
                messageBroker
        );

        assertNotNull(servicioInventario);
    }

    /**
     * Tests the constructor of ServicioInventario when null values are provided for all parameters.
     * This test verifies that the constructor does not throw any exceptions when initialized with null values.
     */
    @Test
    public void testServicioInventarioConstructor_withNullParameters() {
        // Act & Assert
        assertDoesNotThrow(() -> new ServicioInventario(null, null, null, null));
    }

    /**
     * Tests the validarIngredientes method when all ingredients are available and have sufficient stock.
     * This test covers the path where the method returns true because all ingredients are present
     * and have enough stock to fulfill the order.
     */
    @Test
    public void testValidarIngredientesSufficientStock() {
        // Arrange
        ItemPedido item = new ItemPedido(1L, 2);
        Pedido pedido = new Pedido(1L,Arrays.asList(item));

        Receta receta = new Receta(1L,1L, Collections.singletonMap(1L, 1));
        Producto producto = new Producto(1L, "Hamburguesa", receta);
        Map<Long, Integer> ingredientes = new HashMap<>();
        ingredientes.put(1L, 3);

        Ingrediente ingrediente = new Ingrediente(1L, "Lechuga", 10);

        when(obtenerProductoPort.ObtenerProductoPorId(1L)).thenReturn(Optional.of(producto));
        when(obtenerIngredientePort.obtenerIngredientePorId(1L)).thenReturn(Optional.of(ingrediente));

        // Act
        boolean result = servicioInventario.validarIngredientes(pedido);

        // Assert
        assertTrue(result);
    }

    /**
     * Tests the validarIngredientes method when an ingredient is not found in the inventory.
     * This test verifies that the method returns false when an ingredient required for the order is missing.
     */
    @Test
    public void testValidarIngredientesWhenIngredientNotFound() {
        // Arrange
        // Arrange
        ItemPedido item = new ItemPedido(1L, 2);
        Pedido pedido = new Pedido(1L,Arrays.asList(item));

        Receta receta = new Receta(1L,1L, Collections.singletonMap(1L, 1));
        Producto producto = new Producto(1L, "Hamburguesa", receta);
        Map<Long, Integer> ingredientes = new HashMap<>();
        ingredientes.put(1L, 3);

        Ingrediente ingrediente = new Ingrediente(1L, "Lechuga", 10);

        when(obtenerProductoPort.ObtenerProductoPorId(1L)).thenReturn(Optional.of(producto));
        when(obtenerIngredientePort.obtenerIngredientePorId(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = servicioInventario.validarIngredientes(pedido);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests the procesarPedido method when ingredient validation fails.
     * This test verifies that the method returns false when ingredient validation fails.
     */
    @Test
    public void test_procesarPedido_whenIngredientValidationFails() {
        // Creating test data
        Pedido pedido = new Pedido(1L, Collections.emptyList());

        // Create a spy of the service to mock only the validarIngredientes method
        ServicioInventario spyService = spy(servicioInventario);
        
        // Mocking behavior with doReturn instead of when
        doReturn(false).when(spyService).validarIngredientes(any(Pedido.class));

        // Executing the method
        Boolean result = spyService.procesarPedido(pedido);

        // Asserting the result
        assertFalse(result);
    }
}

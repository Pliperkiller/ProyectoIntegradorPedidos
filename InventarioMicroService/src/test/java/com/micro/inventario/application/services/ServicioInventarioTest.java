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

@ExtendWith(MockitoExtension.class)
public class ServicioInventarioTest {

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
            mockActualizarInventarioPort
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

}

package com.micro.inventario.infrastructure.adapters.in.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemPedidoRequestTest {

    /**
     * Test case for getCantidad() method of ItemPedidoRequest class.
     * Verifies that the method correctly returns the cantidad value.
     */
    @Test
    public void test_getCantidad_returnsCantidadValue() {
        ItemPedidoRequest itemPedidoRequest = new ItemPedidoRequest();
        int expectedCantidad = 5;
        itemPedidoRequest.setCantidad(expectedCantidad);

        int actualCantidad = itemPedidoRequest.getCantidad();

        assertEquals(expectedCantidad, actualCantidad, "getCantidad() should return the set cantidad value");
    }

    /**
     * Tests that the getIdProducto method returns the correct idProducto value.
     * This test creates an ItemPedidoRequest object, sets an idProducto,
     * and verifies that getIdProducto returns the expected value.
     */
    @Test
    public void test_getIdProducto_returnsCorrectId() {
        ItemPedidoRequest itemPedidoRequest = new ItemPedidoRequest();
        String expectedId = "PROD123";
        itemPedidoRequest.setIdProducto(expectedId);

        String actualId = itemPedidoRequest.getIdProducto();

        assertEquals(expectedId, actualId, "getIdProducto should return the correct idProducto");
    }

    /**
     * Test getIdProducto when idProducto is null.
     * This test verifies that getIdProducto returns null when the idProducto field is not set.
     */
    @Test
    public void test_getIdProducto_whenIdProductoIsNull() {
        ItemPedidoRequest request = new ItemPedidoRequest();
        assert request.getIdProducto() == null;
    }

    /**
     * Tests setting a negative quantity, which is an edge case for an inventory item.
     * This test verifies that the setCantidad method allows setting a negative value,
     * as there's no explicit validation in the current implementation.
     */
    @Test
    public void test_setCantidad_negative_value() {
        ItemPedidoRequest item = new ItemPedidoRequest();
        int negativeCantidad = -5;
        item.setCantidad(negativeCantidad);
        assert item.getCantidad() == negativeCantidad : "Expected cantidad to be set to a negative value";
    }

    /**
     * Test case for setCantidad method
     * Verifies that the cantidad field is correctly set when setCantidad is called
     */
    @Test
    public void test_setCantidad_setsCorrectValue() {
        ItemPedidoRequest itemPedidoRequest = new ItemPedidoRequest();
        int expectedCantidad = 5;

        itemPedidoRequest.setCantidad(expectedCantidad);

        assertEquals(expectedCantidad, itemPedidoRequest.getCantidad());
    }

    /**
     * Tests that setIdProducto correctly sets the idProducto field.
     * This test verifies that when setIdProducto is called with a valid String,
     * the idProducto field is updated accordingly and can be retrieved using getIdProducto.
     */
    @Test
    public void test_setIdProducto_updatesIdProductoField() {
        ItemPedidoRequest itemPedidoRequest = new ItemPedidoRequest();
        String expectedIdProducto = "PROD123";

        itemPedidoRequest.setIdProducto(expectedIdProducto);

        assertEquals(expectedIdProducto, itemPedidoRequest.getIdProducto());
    }

    /**
     * Tests the setIdProducto method with null input.
     * This test verifies that the method accepts null values without throwing an exception,
     * as the current implementation does not explicitly handle null inputs.
     */
    @Test
    public void test_setIdProducto_withNullInput() {
        ItemPedidoRequest request = new ItemPedidoRequest();
        request.setIdProducto(null);
        // No assertion needed as the method doesn't throw an exception or have any validation
    }

}

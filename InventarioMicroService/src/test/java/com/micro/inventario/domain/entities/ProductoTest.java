package com.micro.inventario.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    /**
     * Test case for getNombre() method of Producto class.
     * Verifies that the method correctly returns the nombre (name) of the product.
     */
    @Test
    public void testGetNombreReturnsCorrectName() {
        String expectedName = "Test Product";
        Producto producto = new Producto(1L, expectedName, null);

        String actualName = producto.getNombre();

        assertEquals(expectedName, actualName, "getNombre() should return the correct product name");
    }

    /**
     * Tests the constructor of Producto with null values for all parameters.
     * This test verifies that the constructor accepts null values without throwing exceptions,
     * as the current implementation does not explicitly handle null inputs.
     */
    @Test
    public void testProductoConstructorWithNullValues() {
        Producto producto = new Producto(null, null, null);
        assertNull(producto.getId());
        assertNull(producto.getNombre());
        assertNull(producto.getReceta());
    }

    /**
     * Test the constructor of Producto class
     * Verifies that the Producto object is created with the correct id, nombre, and receta
     */
    @Test
    public void test_Producto_Constructor() {
        Long id = 1L;
        String nombre = "Test Producto";
        Map<Long, Integer> ingredientes = new HashMap<>();
        ingredientes.put(id, 1);
        Receta receta = new Receta(2L, id, ingredientes); // Assuming Receta has a no-arg constructor

        Producto producto = new Producto(id, nombre, receta);

        assertEquals(id, producto.getId());
        assertEquals(nombre, producto.getNombre());
        assertEquals(receta, producto.getReceta());
    }

    /**
     * Test case for getId method of Producto class.
     * Verifies that the getId method returns the correct id value.
     */
    @Test
    public void test_getId_returnsCorrectId() {
        Long expectedId = 1L;
        Producto producto = new Producto(expectedId, "Test Product", null);
        assertEquals(expectedId, producto.getId(), "getId should return the correct id");
    }

    /**
     * Test case for the getReceta method of the Producto class.
     * This test verifies that the getReceta method correctly returns the Receta object
     * associated with the Producto instance.
     */
    @Test
    public void test_getReceta_returnsCorrectReceta() {
        Map<Long, Integer> ingredientes = new HashMap<>();
        ingredientes.put(1L, 1);
        Receta expectedReceta = new Receta(1L, 1L, ingredientes);
        Producto producto = new Producto(1L, "TestProducto", expectedReceta);

        Receta actualReceta = producto.getReceta();

        assertSame(expectedReceta, actualReceta, "The getReceta method should return the correct Receta object");
    }

}

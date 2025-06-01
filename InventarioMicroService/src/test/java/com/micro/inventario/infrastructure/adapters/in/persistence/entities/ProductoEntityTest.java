package com.micro.inventario.infrastructure.adapters.in.persistence.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoEntityTest {

    /**
     * Test case for ProductoEntity constructor with id and nombre parameters
     * Verifies that the constructor correctly initializes the id and nombre fields
     */
    @Test
    public void testProductoEntityConstructorWithIdAndNombre() {
        Long id = 1L;
        String nombre = "Test Product";

        ProductoEntity producto = new ProductoEntity(id, nombre);

        assertEquals(id, producto.getId());
        assertEquals(nombre, producto.getNombre());
    }

    /**
     * Test case for getId() method of ProductoEntity
     * Verifies that getId() returns the correct id value
     */
    @Test
    public void test_getId_returnsCorrectId() {
        Long expectedId = 1L;
        ProductoEntity productoEntity = new ProductoEntity(expectedId, "Test Product");

        Long actualId = productoEntity.getId();

        assertEquals(expectedId, actualId, "getId() should return the correct id value");
    }

    /**
     * Tests that the getNombre method returns the correct name of the product.
     * This test creates a ProductoEntity with a known name and verifies that
     * getNombre returns that exact name.
     */
    @Test
    public void test_getNombre_returnsCorrectName() {
        String expectedName = "Test Product";
        ProductoEntity producto = new ProductoEntity(1L, expectedName);

        String actualName = producto.getNombre();

        assertEquals(expectedName, actualName, "getNombre should return the correct product name");
    }

    /**
     * Test case for setId method of ProductoEntity
     * Verifies that the id is correctly set and can be retrieved
     */
    @Test
    public void test_setId_setsIdCorrectly() {
        ProductoEntity producto = new ProductoEntity();
        Long expectedId = 1L;
        producto.setId(expectedId);
        assertEquals(expectedId, producto.getId());
    }

    /**
     * Tests the setNombre method of ProductoEntity
     * Verifies that the nombre field is correctly set when calling setNombre
     */
    @Test
    public void test_setNombre_setsNameCorrectly() {
        ProductoEntity producto = new ProductoEntity();
        String expectedNombre = "Test Product";
        producto.setNombre(expectedNombre);
        assertEquals(expectedNombre, producto.getNombre());
    }

}

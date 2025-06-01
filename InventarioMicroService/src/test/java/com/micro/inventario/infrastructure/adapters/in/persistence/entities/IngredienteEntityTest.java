package com.micro.inventario.infrastructure.adapters.in.persistence.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredienteEntityTest {

    /**
     * Test case for getId() method of IngredienteEntity
     * 
     * Verifies that the getId() method correctly returns the id value
     * that was set during object creation.
     */
    @Test
    public void testGetIdReturnsCorrectId() {
        Long expectedId = 1L;
        IngredienteEntity ingrediente = new IngredienteEntity(expectedId, "Test Ingredient", 100);

        Long actualId = ingrediente.getId();

        assertEquals(expectedId, actualId, "getId() should return the correct id value");
    }

    /**
     * Test case for IngredienteEntity constructor with valid parameters
     * Verifies that the constructor correctly initializes the object's properties
     */
    @Test
    public void testIngredienteEntityConstructor() {
        Long id = 1L;
        String nombre = "Tomate";
        int stock = 100;

        IngredienteEntity ingrediente = new IngredienteEntity(id, nombre, stock);

        assertEquals(id, ingrediente.getId());
        assertEquals(nombre, ingrediente.getNombre());
        assertEquals(stock, ingrediente.getStock());
    }

    /**
     * Tests the IngredienteEntity constructor with a negative stock value.
     * This test verifies that the constructor accepts and sets a negative stock value,
     * which may be an undesired behavior but is currently allowed by the implementation.
     */
    @Test
    public void testIngredienteEntityWithNegativeStock() {
        IngredienteEntity ingrediente = new IngredienteEntity(1L, "Test Ingredient", -10);
        assertEquals(-10, ingrediente.getStock(), "The stock should be set to -10 even though it's negative");
    }

    /**
     * Tests setting the id of IngredienteEntity with a null value.
     * This test verifies that the setId method accepts null values without throwing an exception,
     * which is the current behavior of the method based on its implementation.
     */
    @Test
    public void testSetIdWithNullValue() {
        IngredienteEntity ingrediente = new IngredienteEntity();
        ingrediente.setId(null);
        // No assertion is needed as we're just verifying that no exception is thrown
    }

    /**
     * Test case for getNombre() method
     * Verifies that the method correctly returns the nombre (name) of the IngredienteEntity
     */
    @Test
    public void test_getNombre_ReturnsCorrectName() {
        IngredienteEntity ingrediente = new IngredienteEntity(1L, "Salt", 100);
        assertEquals("Salt", ingrediente.getNombre(), "getNombre should return the correct name");
    }

    /**
     * Test case for getStock() method of IngredienteEntity
     * Verifies that the method correctly returns the stock value
     */
    @Test
    public void test_getStock_returnsCorrectStockValue() {
        IngredienteEntity ingrediente = new IngredienteEntity(1L, "Tomate", 100);
        assertEquals(100, ingrediente.getStock(), "getStock() should return the correct stock value");
    }

    /**
     * Test case for setId method of IngredienteEntity
     * Verifies that the id is correctly set and retrieved
     */
    @Test
    public void test_setId_setsIdCorrectly() {
        IngredienteEntity ingrediente = new IngredienteEntity();
        Long expectedId = 1L;

        ingrediente.setId(expectedId);

        assertEquals(expectedId, ingrediente.getId());
    }

    /**
     * Test case for setNombre method of IngredienteEntity
     * Verifies that the nombre field is correctly set when calling setNombre
     */
    @Test
    public void test_setNombre_setsNameCorrectly() {
        IngredienteEntity ingrediente = new IngredienteEntity();
        String expectedNombre = "Tomate";

        ingrediente.setNombre(expectedNombre);

        assertEquals(expectedNombre, ingrediente.getNombre(), "The nombre should be set correctly");
    }

    /**
     * Test case for setStock method
     * Verifies that the stock value is correctly set and retrieved
     */
    @Test
    public void test_setStock_setsCorrectValue() {
        IngredienteEntity ingrediente = new IngredienteEntity();
        int expectedStock = 10;
        ingrediente.setStock(expectedStock);
        assertEquals(expectedStock, ingrediente.getStock());
    }

}

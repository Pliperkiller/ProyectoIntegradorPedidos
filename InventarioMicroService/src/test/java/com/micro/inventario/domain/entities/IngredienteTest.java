package com.micro.inventario.domain.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredienteTest {

    /**
     * Test case for getId() method of Ingrediente class.
     * Verifies that the method correctly returns the id of the Ingrediente.
     */
    @Test
    public void testGetIdReturnsCorrectId() {
        Long expectedId = 1L;
        Ingrediente ingrediente = new Ingrediente(expectedId, "Sal", 100);

        Long actualId = ingrediente.getId();

        assertEquals(expectedId, actualId, "getId() should return the correct id");
    }

    /**
     * Test the getStock method of Ingrediente class.
     * This is not a negative test as the getStock method doesn't handle any edge cases or error conditions.
     * It simply returns the value of the stock field.
     */
    @Test
    public void testGetStock() {
        Ingrediente ingrediente = new Ingrediente(1L, "Test Ingredient", 10);
        assertEquals(10, ingrediente.getStock());
    }

    /**
     * Test case for the Ingrediente constructor.
     * Verifies that the constructor correctly initializes the id, nombre, and stock fields.
     */
    @Test
    public void testIngredienteConstructor() {
        Long id = 1L;
        String nombre = "Sal";
        int stock = 100;

        Ingrediente ingrediente = new Ingrediente(id, nombre, stock);

        assertEquals(id, ingrediente.getId());
        assertEquals(nombre, ingrediente.getNombre());
        assertEquals(stock, ingrediente.getStock());
    }

    /**
     * Tests that usarStock throws an IllegalArgumentException when the requested quantity
     * exceeds the available stock.
     */
    @Test
    public void testUsarStockInsufficientStock() {
        Ingrediente ingrediente = new Ingrediente(1L, "Sal", 10);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ingrediente.usarStock(15);
        });

        assertEquals("Stock insuficiente para el ingrediente: Sal", exception.getMessage());
    }

    /**
     * Test case for getNombre() method of Ingrediente class.
     * Verifies that the method correctly returns the name of the ingredient.
     */
    @Test
    public void test_getNombre_returnsCorrectName() {
        String expectedName = "Salt";
        Ingrediente ingrediente = new Ingrediente(1L, expectedName, 100);

        String actualName = ingrediente.getNombre();

        assertEquals(expectedName, actualName, "getNombre() should return the correct ingredient name");
    }

    /**
     * Test case for getStock() method of Ingrediente class
     * Verifies that the method correctly returns the stock value
     */
    @Test
    public void test_getStock_returnsCorrectStockValue() {
        Ingrediente ingrediente = new Ingrediente(1L, "Test Ingredient", 100);
        assertEquals(100, ingrediente.getStock(), "getStock() should return the correct stock value");
    }

    /**
     * Test case for surtirStock method
     * Verifies that the stock is correctly increased by the given quantity
     */
    @Test
    public void test_surtirStock_increasesStockCorrectly() {
        Ingrediente ingrediente = new Ingrediente(1L, "Salt", 100);
        int initialStock = ingrediente.getStock();
        int quantityToAdd = 50;

        ingrediente.surtirStock(quantityToAdd);

        assertEquals(initialStock + quantityToAdd, ingrediente.getStock(), 
            "Stock should be increased by the quantity added");
    }

    /**
     * Test surtirStock with a negative quantity.
     * This tests the edge case where a negative value is provided to surtirStock,
     * which could potentially decrease the stock instead of increasing it.
     * The current implementation does not handle this case explicitly,
     * so we're verifying the actual behavior.
     */
    @Test
    public void test_surtirStock_negative_quantity() {
        Ingrediente ingrediente = new Ingrediente(1L, "Test", 10);
        int initialStock = ingrediente.getStock();
        ingrediente.surtirStock(-5);
        assertEquals(initialStock - 5, ingrediente.getStock(), "Stock should decrease when negative quantity is provided");
    }

    /**
     * Test case for usarStock method when the requested quantity exceeds available stock.
     * This test verifies that an IllegalArgumentException is thrown when attempting to use
     * more stock than is available for the ingredient.
     */
    @Test
    public void test_usarStock_whenQuantityExceedsStock_throwsIllegalArgumentException() {
        Ingrediente ingrediente = new Ingrediente(1L, "Sal", 5);
        assertThrows(IllegalArgumentException.class, () -> ingrediente.usarStock(10));
    }

    /**
     * Tests the usarStock method when the requested quantity is less than or equal to the available stock.
     * This test ensures that the stock is correctly reduced when a valid quantity is used.
     */
    @Test
    public void test_usarStock_whenQuantityLessThanOrEqualToStock() {
        Ingrediente ingrediente = new Ingrediente(1L, "Sal", 100);
        int initialStock = ingrediente.getStock();
        int quantityToUse = 50;

        ingrediente.usarStock(quantityToUse);

        assertEquals(initialStock - quantityToUse, ingrediente.getStock(), 
            "Stock should be reduced by the used quantity");
    }

}

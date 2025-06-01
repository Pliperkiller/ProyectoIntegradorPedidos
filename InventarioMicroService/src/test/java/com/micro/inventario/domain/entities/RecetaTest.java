package com.micro.inventario.domain.entities;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecetaTest {

    /**
     * Test case for Receta constructor
     * Verifies that a Receta object is correctly created with the given id, idProducto, and ingredientes
     */
    @Test
    public void testRecetaConstructor() {
        Long id = 1L;
        Long idProducto = 100L;
        Map<Long, Integer> ingredientes = new HashMap<>();
        ingredientes.put(1L, 2);
        ingredientes.put(2L, 3);

        Receta receta = new Receta(id, idProducto, ingredientes);

        assertEquals(id, receta.getId());
        assertEquals(idProducto, receta.getIdProducto());
        assertEquals(ingredientes, receta.getIngredientes());
    }

    /**
     * Test case for getIdProducto method
     * Verifies that the method correctly returns the idProducto value
     */
    @Test
    public void test_getIdProducto_returnsCorrectId() {
        // Arrange
        Long expectedId = 1L;
        Receta receta = new Receta(1L, expectedId, null);

        // Act
        Long actualId = receta.getIdProducto();

        // Assert
        assertEquals(expectedId, actualId, "getIdProducto should return the correct idProducto");
    }

    /**
     * Test case for getId() method of Receta class.
     * This test verifies that the getId() method correctly returns the id of the Receta object.
     */
    @Test
    public void test_getId_returnsCorrectId() {
        Long expectedId = 1L;
        Receta receta = new Receta(expectedId, 2L, null);
        assertEquals(expectedId, receta.getId(), "getId() should return the correct id");
    }

    /**
     * Test to verify that getIngredientes returns an empty map when no ingredients are present.
     * This tests the edge case of an empty ingredient list, which is implicitly handled
     * by the method's implementation returning the map as-is.
     */
    @Test
    public void test_getIngredientes_emptyMap() {
        Receta receta = new Receta(1L, 2L, Map.of());
        assertTrue(receta.getIngredientes().isEmpty(), "The ingredients map should be empty");
    }

    /**
     * Test case for getIngredientes method of Receta class.
     * Verifies that the method returns the correct Map of ingredients.
     */
    @Test
    public void test_getIngredientes_returnsCorrectIngredientes() {
        // Arrange
        Map<Long, Integer> expectedIngredientes = new HashMap<>();
        expectedIngredientes.put(1L, 100);
        expectedIngredientes.put(2L, 200);
        Receta receta = new Receta(1L, 10L, expectedIngredientes);

        // Act
        Map<Long, Integer> actualIngredientes = receta.getIngredientes();

        // Assert
        assertEquals(expectedIngredientes, actualIngredientes, "The returned ingredients map should match the one used to create the Receta object");
    }

}

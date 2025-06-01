package com.micro.inventario.infrastructure.adapters.in.persistence.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecetaIngredienteEntityTest {

    /**
     * Test case for getCantidad method of RecetaIngredienteEntity
     * 
     * This test verifies that the getCantidad method correctly returns the cantidad value
     * set in the RecetaIngredienteEntity object.
     */
    @Test
    public void testGetCantidad() {
        // Arrange
        int expectedCantidad = 5;
        RecetaIngredienteEntity entity = new RecetaIngredienteEntity();
        entity.setCantidad(expectedCantidad);

        // Act
        int actualCantidad = entity.getCantidad();

        // Assert
        assertEquals(expectedCantidad, actualCantidad, "The getCantidad method should return the correct cantidad value");
    }

    /**
     * Test case for getIngrediente method of RecetaIngredienteEntity
     * Verifies that the method correctly returns the ingrediente field
     */
    @Test
    public void testGetIngrediente() {
        IngredienteEntity expectedIngrediente = new IngredienteEntity();
        RecetaIngredienteEntity recetaIngrediente = new RecetaIngredienteEntity();
        recetaIngrediente.setIngrediente(expectedIngrediente);

        IngredienteEntity result = recetaIngrediente.getIngrediente();

        assertEquals(expectedIngrediente, result, "getIngrediente should return the set ingrediente");
    }

    /**
     * Test case for getReceta method of RecetaIngredienteEntity
     * 
     * This test verifies that the getReceta method correctly returns the RecetaEntity
     * associated with the RecetaIngredienteEntity.
     */
    @Test
    public void testGetRecetaReturnsCorrectRecetaEntity() {
        // Arrange
        RecetaEntity expectedReceta = new RecetaEntity();
        RecetaIngredienteEntity recetaIngrediente = new RecetaIngredienteEntity();
        recetaIngrediente.setReceta(expectedReceta);

        // Act
        RecetaEntity actualReceta = recetaIngrediente.getReceta();

        // Assert
        assertSame(expectedReceta, actualReceta, "getReceta should return the same RecetaEntity that was set");
    }

    /**
     * Tests the RecetaIngredienteEntity constructor with a negative quantity value.
     * This test verifies that the constructor accepts a negative quantity without throwing an exception,
     * as the current implementation does not explicitly handle this case.
     */
    @Test
    public void testRecetaIngredienteEntityWithNegativeQuantity() {
        RecetaEntity receta = new RecetaEntity();
        IngredienteEntity ingrediente = new IngredienteEntity();
        int negativeCantidad = -1;

        RecetaIngredienteEntity entity = new RecetaIngredienteEntity(receta, ingrediente, negativeCantidad);

        assertEquals(negativeCantidad, entity.getCantidad());
        assertEquals(receta, entity.getReceta());
        assertEquals(ingrediente, entity.getIngrediente());
    }

    /**
     * Test setting null RecetaEntity to RecetaIngredienteEntity.
     * This test verifies that the setReceta method accepts a null value
     * without throwing an exception, as the method does not include
     * any explicit null checks.
     */
    @Test
    public void testSetRecetaWithNullValue() {
        RecetaIngredienteEntity entity = new RecetaIngredienteEntity();
        entity.setReceta(null);
        assertNull(entity.getReceta());
    }

    /**
     * Tests the constructor of RecetaIngredienteEntity to ensure it correctly initializes
     * the object with the provided RecetaEntity, IngredienteEntity, and cantidad.
     */
    @Test
    public void test_RecetaIngredienteEntityConstructor() {
        RecetaEntity receta = new RecetaEntity();
        IngredienteEntity ingrediente = new IngredienteEntity();
        int cantidad = 5;

        RecetaIngredienteEntity recetaIngrediente = new RecetaIngredienteEntity(receta, ingrediente, cantidad);

        assertNotNull(recetaIngrediente);
        assertEquals(receta, recetaIngrediente.getReceta());
        assertEquals(ingrediente, recetaIngrediente.getIngrediente());
        assertEquals(cantidad, recetaIngrediente.getCantidad());
    }

    /**
     * Test case for setting and getting the cantidad (quantity) of a RecetaIngredienteEntity.
     * This test verifies that the setCantidad method correctly sets the cantidad
     * and that the getCantidad method returns the expected value.
     */
    @Test
    public void test_setCantidad_1() {
        RecetaIngredienteEntity entity = new RecetaIngredienteEntity();
        int expectedCantidad = 5;

        entity.setCantidad(expectedCantidad);

        assertEquals(expectedCantidad, entity.getCantidad());
    }

    /**
     * Test case for setIngrediente method
     * Verifies that the ingrediente field is correctly set when calling setIngrediente
     */
    @Test
    public void test_setIngrediente_setsIngredienteCorrectly() {
        RecetaIngredienteEntity recetaIngrediente = new RecetaIngredienteEntity();
        IngredienteEntity ingrediente = new IngredienteEntity();

        recetaIngrediente.setIngrediente(ingrediente);

        assertEquals(ingrediente, recetaIngrediente.getIngrediente());
    }

    /**
     * Tests that the setReceta method correctly sets the receta field.
     * This test creates a new RecetaEntity, sets it using setReceta,
     * and then verifies that getReceta returns the same entity.
     */
    @Test
    public void test_setReceta_setsRecetaCorrectly() {
        RecetaIngredienteEntity recetaIngrediente = new RecetaIngredienteEntity();
        RecetaEntity receta = new RecetaEntity();

        recetaIngrediente.setReceta(receta);

        assertEquals(receta, recetaIngrediente.getReceta());
    }

}

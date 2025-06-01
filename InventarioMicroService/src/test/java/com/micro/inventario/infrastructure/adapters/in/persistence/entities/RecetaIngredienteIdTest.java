package com.micro.inventario.infrastructure.adapters.in.persistence.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecetaIngredienteIdTest {

    /**
     * Tests the equals method with an object of a different class.
     * This is an edge case explicitly handled in the equals method.
     */
    @Test
    public void testEqualsWithDifferentClass() {
        RecetaIngredienteId id = new RecetaIngredienteId(1L, 2L);
        assertFalse(id.equals(new Object()));
    }

    /**
     * Tests the equals method with a null object.
     * This is an edge case explicitly handled in the equals method.
     */
    @Test
    public void testEqualsWithNullObject() {
        RecetaIngredienteId id = new RecetaIngredienteId(1L, 2L);
        assertFalse(id.equals(null));
    }

    /**
     * Test case for RecetaIngredienteId constructor with valid input parameters.
     * Verifies that the constructor correctly initializes the object with given receta and ingrediente values.
     */
    @Test
    public void test_RecetaIngredienteId_ConstructorWithValidInput() {
        Long receta = 1L;
        Long ingrediente = 2L;
        RecetaIngredienteId recetaIngredienteId = new RecetaIngredienteId(receta, ingrediente);

        assertEquals(receta, recetaIngredienteId.getReceta());
        assertEquals(ingrediente, recetaIngredienteId.getIngrediente());
    }

    /**
     * Test case for equals method when comparing two identical RecetaIngredienteId objects.
     * 
     * This test verifies that the equals method returns true when comparing
     * a RecetaIngredienteId object with another instance that has the same
     * receta and ingrediente values.
     */
    @Test
    public void test_equals_identical_objects() {
        RecetaIngredienteId id1 = new RecetaIngredienteId(1L, 2L);
        RecetaIngredienteId id2 = new RecetaIngredienteId(1L, 2L);

        assertTrue(id1.equals(id2), "Two RecetaIngredienteId objects with the same values should be equal");
    }

    /**
     * Tests the equals method when comparing two RecetaIngredienteId objects with the same receta and ingrediente values.
     * Verifies that the method returns true for equal objects.
     */
    @Test
    public void test_equals_whenObjectsHaveSameValues_returnsTrue() {
        RecetaIngredienteId id1 = new RecetaIngredienteId(1L, 2L);
        RecetaIngredienteId id2 = new RecetaIngredienteId(1L, 2L);

        assertTrue(id1.equals(id2));
    }

    /**
     * Tests the equals method when comparing two different RecetaIngredienteId objects
     * with the same receta and ingrediente values.
     * 
     * Path constraints: !((this == o))
     * Expected result: Should return true if both objects have equal receta and ingrediente values.
     */
    @Test
    public void test_equals_when_objects_have_same_values() {
        RecetaIngredienteId id1 = new RecetaIngredienteId(1L, 2L);
        RecetaIngredienteId id2 = new RecetaIngredienteId(1L, 2L);

        assertTrue(id1.equals(id2));
    }

    /**
     * Test case for the hashCode method of RecetaIngredienteId class.
     * Verifies that the hashCode is calculated correctly using Objects.hash(receta, ingrediente).
     */
    @Test
    public void test_hashCode_calculatesCorrectly() {
        RecetaIngredienteId id1 = new RecetaIngredienteId(1L, 2L);
        RecetaIngredienteId id2 = new RecetaIngredienteId(1L, 2L);
        RecetaIngredienteId id3 = new RecetaIngredienteId(2L, 1L);

        assertEquals(id1.hashCode(), id2.hashCode(), "Hash codes should be equal for identical objects");
        assertNotEquals(id1.hashCode(), id3.hashCode(), "Hash codes should be different for objects with different values");
    }

}

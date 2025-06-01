package com.micro.inventario.infrastructure.adapters.in.persistence.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecetaEntityTest {

    /**
     * Tests the getProducto method when the producto field is null.
     * This test verifies that the method correctly returns null when no product is associated with the recipe.
     */
    @Test
    public void testGetProductoWhenProductoIsNull() {
        RecetaEntity receta = new RecetaEntity();
        assertNull(receta.getProducto(), "getProducto should return null when no product is set");
    }

    /**
     * Test case for setId method of RecetaEntity
     * Verifies that the id is correctly set and can be retrieved
     */
    @Test
    public void testSetAndGetId() {
        RecetaEntity recetaEntity = new RecetaEntity();
        Long expectedId = 1L;
        recetaEntity.setId(expectedId);
        assertEquals(expectedId, recetaEntity.getId(), "The id should be set and retrieved correctly");
    }

    /**
     * Test case for RecetaEntity constructor with valid parameters
     * Verifies that the RecetaEntity object is correctly initialized with the given id and producto
     */
    @Test
    public void test_RecetaEntity_ConstructorWithValidParameters() {
        Long id = 1L;
        ProductoEntity producto = new ProductoEntity();
        RecetaEntity receta = new RecetaEntity(id, producto);

        assertEquals(id, receta.getId());
        assertEquals(producto, receta.getProducto());
    }

    /**
     * Test case for getId() method of RecetaEntity
     * 
     * This test verifies that the getId() method correctly returns the id of the RecetaEntity.
     * It creates a RecetaEntity with a known id, calls getId(), and asserts that the returned value matches the expected id.
     */
    @Test
    public void test_getId_returnsCorrectId() {
        Long expectedId = 1L;
        RecetaEntity recetaEntity = new RecetaEntity(expectedId, null);

        Long actualId = recetaEntity.getId();

        assertEquals(expectedId, actualId, "getId() should return the correct id");
    }

    /**
     * Test case for getProducto() method of RecetaEntity
     * Verifies that the method correctly returns the associated ProductoEntity
     */
    @Test
    public void test_getProducto_returnsAssociatedProduct() {
        ProductoEntity expectedProduct = new ProductoEntity();
        RecetaEntity receta = new RecetaEntity();
        receta.setProducto(expectedProduct);

        ProductoEntity actualProduct = receta.getProducto();

        assertEquals(expectedProduct, actualProduct, "getProducto should return the associated ProductoEntity");
    }

    /**
     * Test case for setProducto method of RecetaEntity
     * 
     * This test verifies that the setProducto method correctly sets the producto field
     * of a RecetaEntity instance.
     */
    @Test
    public void test_setProducto_setsProductoCorrectly() {
        RecetaEntity receta = new RecetaEntity();
        ProductoEntity producto = new ProductoEntity();

        receta.setProducto(producto);

        assertEquals(producto, receta.getProducto());
    }

}

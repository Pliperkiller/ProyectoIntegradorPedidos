package com.micro.inventario.infrastructure.adapters.in.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PedidoResponseTest {

    /**
     * Tests the constructor of PedidoResponse class.
     * Verifies that the pedidoId and mensage are correctly set when creating a new PedidoResponse object.
     */
    @Test
    public void testPedidoResponseConstructor() {
        Long pedidoId = 1L;
        String mensage = "Test message";

        PedidoResponse response = new PedidoResponse(pedidoId, mensage);

        assertEquals(pedidoId, response.getPedidoId());
        assertEquals(mensage, response.getMensaje());
    }

    /**
     * Test setting pedidoId to null.
     * This test verifies that the setPedidoId method accepts a null value without throwing an exception.
     */
    @Test
    public void testSetPedidoIdWithNull() {
        PedidoResponse pedidoResponse = new PedidoResponse(1L, "Test");
        pedidoResponse.setPedidoId(null);
        // No assertion needed as we're just verifying that no exception is thrown
    }

    /**
     * Test case for getMensaje() method of PedidoResponse class.
     * Verifies that the method correctly returns the message stored in the object.
     */
    @Test
    public void test_getMensaje_returnsStoredMessage() {
        String expectedMessage = "Test message";
        PedidoResponse pedidoResponse = new PedidoResponse(1L, expectedMessage);

        String actualMessage = pedidoResponse.getMensaje();

        assertEquals(expectedMessage, actualMessage, "getMensaje() should return the stored message");
    }

    /**
     * Tests that getPedidoId returns the correct pedidoId value
     * when it is set in the constructor.
     */
    @Test
    public void test_getPedidoId_returnsCorrectValue() {
        Long expectedId = 123L;
        PedidoResponse pedidoResponse = new PedidoResponse(expectedId, "Test message");

        Long actualId = pedidoResponse.getPedidoId();

        assertEquals(expectedId, actualId, "getPedidoId should return the id set in the constructor");
    }

    /**
     * Test case for setMensaje method of PedidoResponse class.
     * It verifies that the mensaje field is correctly set when calling setMensaje.
     */
    @Test
    public void test_setMensaje_setsMessageCorrectly() {
        PedidoResponse pedidoResponse = new PedidoResponse(1L, "");
        String expectedMessage = "Test message";
        pedidoResponse.setMensaje(expectedMessage);
        assertEquals(expectedMessage, pedidoResponse.getMensaje());
    }

    /**
     * Test case for setPedidoId method of PedidoResponse class.
     * Verifies that the pedidoId is correctly set and can be retrieved.
     */
    @Test
    public void test_setPedidoId_setsCorrectValue() {
        PedidoResponse pedidoResponse = new PedidoResponse(null, null);
        Long expectedPedidoId = 123L;

        pedidoResponse.setPedidoId(expectedPedidoId);

        assertEquals(expectedPedidoId, pedidoResponse.getPedidoId());
    }

}

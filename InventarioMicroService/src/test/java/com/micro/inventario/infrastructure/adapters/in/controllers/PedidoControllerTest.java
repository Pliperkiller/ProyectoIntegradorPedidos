package com.micro.inventario.infrastructure.adapters.in.controllers;

import com.micro.inventario.application.ports.in.ProcesarPedidoUseCase;
import com.micro.inventario.domain.entities.Pedido;
import com.micro.inventario.infrastructure.adapters.in.dto.PedidoRequest;
import com.micro.inventario.infrastructure.adapters.in.dto.PedidoResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private ProcesarPedidoUseCase procesarPedidoUseCase;

    @InjectMocks
    private PedidoController pedidoController;

    /**
     * Tests the checkDatabaseConnection method when a SQLException is thrown.
     * Verifies that the method returns an INTERNAL_SERVER_ERROR response with
     * an appropriate error message.
     */
    @Test
    public void testCheckDatabaseConnection_WhenSQLExceptionThrown() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException("Test SQL Exception"));

        PedidoController controller = new PedidoController(null, dataSource);

        ResponseEntity<String> response = controller.checkDatabaseConnection();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al conectar con la base de datos: Test SQL Exception", response.getBody());
    }

    /**
     * Test case for the PedidoController constructor.
     * Verifies that the PedidoController is correctly instantiated with the provided dependencies.
     */
    @Test
    public void testPedidoControllerInstantiation() {
        MockitoAnnotations.openMocks(this);
        PedidoController pedidoController = new PedidoController(procesarPedidoUseCase, dataSource);
        assertNotNull(pedidoController, "PedidoController should be instantiated successfully");
    }

    /**
     * Test case for processing a valid order successfully.
     * Verifies that when the order is processed correctly, the controller returns
     * a ResponseEntity with OK status and a PedidoResponse containing the order ID
     * and a success message.
     */
    @Test
    public void testProcesarPedidoSuccessful() {
        // Arrange
        ProcesarPedidoUseCase procesarPedidoUseCase = Mockito.mock(ProcesarPedidoUseCase.class);
        DataSource dataSource = Mockito.mock(DataSource.class);
        PedidoController controller = new PedidoController(procesarPedidoUseCase, dataSource);

        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setItems(new ArrayList<>());

        when(procesarPedidoUseCase.procesarPedido(any(Pedido.class))).thenReturn(true);

        // Act
        ResponseEntity<PedidoResponse> response = controller.procesarPedido(pedidoRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pedido procesado correctamente", response.getBody().getMensaje());
    }

    /**
     * Tests the scenario where the database connection is closed.
     * This is an edge case explicitly handled in the checkDatabaseConnection method.
     */
    @Test
    public void test_checkDatabaseConnection_connectionClosed() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isClosed()).thenReturn(true);

        PedidoController controller = new PedidoController(null, dataSource);

        ResponseEntity<String> response = controller.checkDatabaseConnection();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("No se pudo abrir la conexión con la base de datos", response.getBody());
    }

    /**
     * Tests the scenario where a SQLException is thrown when trying to connect to the database.
     * This is an error condition explicitly handled in the checkDatabaseConnection method.
     */
    @Test
    public void test_checkDatabaseConnection_sqlException() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException("Connection failed"));

        PedidoController controller = new PedidoController(null, dataSource);

        ResponseEntity<String> response = controller.checkDatabaseConnection();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al conectar con la base de datos: Connection failed", response.getBody());
    }

    /**
     * Tests the checkDatabaseConnection method when the database connection is successful.
     * It verifies that the method returns a ResponseEntity with OK status and the expected success message.
     */
    @Test
    public void test_checkDatabaseConnection_whenConnectionIsSuccessful() throws SQLException {
        // Arrange
        DataSource mockDataSource = Mockito.mock(DataSource.class);
        Connection mockConnection = Mockito.mock(Connection.class);
        Mockito.when(mockDataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.isClosed()).thenReturn(false);

        PedidoController controller = new PedidoController(null, mockDataSource);

        // Act
        ResponseEntity<String> response = controller.checkDatabaseConnection();

        // Assert
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Conexión a la base de datos exitosa");

        Mockito.verify(mockConnection).close();
    }

    /**
     * Test case for procesarPedido when the order processing fails due to insufficient inventory.
     * This test verifies that the controller returns a bad request response with the appropriate message.
     */
    @Test
    public void test_procesarPedido_InsufficientInventory() {
        // Arrange
        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setItems(new ArrayList<>());
        when(procesarPedidoUseCase.procesarPedido(any(Pedido.class))).thenReturn(false);

        // Act
        ResponseEntity<PedidoResponse> response = pedidoController.procesarPedido(pedidoRequest);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Inventario insuficiente", response.getBody().getMensaje());
        assertEquals(null, response.getBody().getPedidoId());
    }

    /**
     * Tests the scenario where the procesarPedido method receives a request with empty items,
     * which should result in a bad request response indicating insufficient inventory.
     */
    @Test
    public void test_procesarPedido_emptyItems() {
        ProcesarPedidoUseCase procesarPedidoUseCase = mock(ProcesarPedidoUseCase.class);
        PedidoController pedidoController = new PedidoController(procesarPedidoUseCase, null);

        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setItems(Collections.emptyList());

        when(procesarPedidoUseCase.procesarPedido(org.mockito.ArgumentMatchers.any()))
                .thenReturn(false);

        ResponseEntity<PedidoResponse> response = pedidoController.procesarPedido(pedidoRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Inventario insuficiente", response.getBody().getMensaje());
    }

    /**
     * Tests that the status() method returns a ResponseEntity with status OK and the correct message.
     */
    @Test
    public void test_status_returns_ok_response_with_correct_message() {
        PedidoController controller = new PedidoController(null, null);
        ResponseEntity<String> response = controller.status();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("API funcionando correctamente", response.getBody());
    }

}

package com.micro.inventario.infrastructure.adapters.in.controllers;

import com.micro.inventario.application.ports.in.ProcesarPedidoUseCase;
import com.micro.inventario.domain.entities.ItemPedido;
import com.micro.inventario.domain.entities.Pedido;
import com.micro.inventario.infrastructure.adapters.in.dto.ErrorResponse;
import com.micro.inventario.infrastructure.adapters.in.dto.PedidoRequest;
import com.micro.inventario.infrastructure.adapters.in.dto.PedidoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Inventario management endpoints")
public class PedidoController {
    private final ProcesarPedidoUseCase procesarPedidoUseCase;
    private final DataSource dataSource;

    public PedidoController(ProcesarPedidoUseCase procesarPedidoUseCase, DataSource dataSource) {
        this.procesarPedidoUseCase = procesarPedidoUseCase;
        this.dataSource = dataSource;
    }

    @PostMapping("/procesar")
    @Operation(summary = "Procesa un pedido", description = "Retorna si un pedido ha sido procesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido procesado correctamente"),
            @ApiResponse(responseCode = "400", description = "Inventario insuficiente o datos inválidos", 
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<PedidoResponse> procesarPedido(@RequestBody PedidoRequest pedidoRequest) {
        if (pedidoRequest.getItems() == null) {
            return ResponseEntity.badRequest().body(new PedidoResponse(null, "Inventario insuficiente"));
        }
        
        List<ItemPedido> items = pedidoRequest.getItems().stream()
                .map(item -> new ItemPedido(Long.valueOf(item.getIdProducto()), item.getCantidad()))
                .collect(Collectors.toList());

        Pedido pedido = new Pedido(null, items);

        boolean processado = procesarPedidoUseCase.procesarPedido(pedido);

        if (processado) {
            return ResponseEntity.ok(new PedidoResponse(pedido.getId(), "Pedido procesado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PedidoResponse(null, "Inventario insuficiente"));
        }
    }

    @GetMapping("/status")
    @Operation(summary = "Obtiene el estado de la API", description = "Retorna el estado de la API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API funciona correctamente"),
            @ApiResponse(responseCode = "500", description = "Hay un error técnico con la API",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("API funcionando correctamente");
    }

    @GetMapping("/status/db")
    @Operation(summary = "Verifica la conexión a la base de datos", description = "Comprueba si la conexión a la base de datos está funcionando correctamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conexión a la base de datos exitosa"),
            @ApiResponse(responseCode = "500", description = "Error al conectar con la base de datos",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<String> checkDatabaseConnection() {
        try (Connection conn = dataSource.getConnection()) {
            if (!conn.isClosed()) {
                return ResponseEntity.ok("Conexión a la base de datos exitosa");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("No se pudo abrir la conexión con la base de datos");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
}
package com.micro.inventario.infrastructure.adapters.in.controllers;

import com.micro.inventario.application.ports.in.ProcesarPedidoUseCase;
import com.micro.inventario.domain.entities.ItemPedido;
import com.micro.inventario.domain.entities.Pedido;
import com.micro.inventario.infrastructure.adapters.in.dto.PedidoRequest;
import com.micro.inventario.infrastructure.adapters.in.dto.PedidoResponse;
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
public class PedidoController {
    private final ProcesarPedidoUseCase procesarPedidoUseCase;
    private final DataSource dataSource;

    public PedidoController(ProcesarPedidoUseCase procesarPedidoUseCase, DataSource dataSource) {
        this.procesarPedidoUseCase = procesarPedidoUseCase;
        this.dataSource = dataSource;
    }

    @PostMapping("/procesar")
    public ResponseEntity<PedidoResponse> procesarPedido(@RequestBody PedidoRequest pedidoRequest) {
        List<ItemPedido> items = pedidoRequest.getItems().stream()
                .map(item -> new ItemPedido(Long.valueOf(item.getIdProducto()), item.getCantidad()))
                .collect(Collectors.toList());

        Pedido pedido = new Pedido(null, items);

        boolean processado = procesarPedidoUseCase.procesarPedido(pedido);

        if (processado) {
            return ResponseEntity.ok(new PedidoResponse(pedido.getId(), "Pedido procesado correctamente"));
        } else {
            return ResponseEntity.badRequest().body(new PedidoResponse(null, "Inventario insuficiente"));
        }

    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("API funcionando correctamente");
    }

    @GetMapping("/status/db")
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

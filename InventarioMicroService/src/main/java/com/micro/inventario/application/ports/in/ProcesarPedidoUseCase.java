package com.micro.inventario.application.ports.in;

import com.micro.inventario.domain.entities.Pedido;

public interface ProcesarPedidoUseCase {
    Boolean procesarPedido(Pedido pedido);
}

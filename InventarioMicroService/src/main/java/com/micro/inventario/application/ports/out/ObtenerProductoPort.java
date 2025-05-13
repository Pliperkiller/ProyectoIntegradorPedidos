package com.micro.inventario.application.ports.out;

import com.micro.inventario.domain.entities.Producto;

import java.util.Optional;

public interface ObtenerProductoPort {
    Optional<Producto> ObtenerProductoPorId(Long id);
}

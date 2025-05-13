package com.micro.inventario.domain.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receta {
    private final Long id;
    private final Long idProducto;
    private final Map<Long, Integer> ingredientes;

    public Receta(Long id, Long idProducto, Map<Long, Integer> ingredientes) {
        this.id = id;
        this.idProducto = idProducto;
        this.ingredientes = ingredientes;
    }

    public Map<Long, Integer> getIngredientes() {
        return ingredientes;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public Long getId() {
        return id;
    }
}

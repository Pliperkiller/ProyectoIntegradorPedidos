package com.micro.inventario.domain.entities;

public class ItemPedido {
    private Long IdProducto;
    private int cantidad;

    public ItemPedido(Long idProducto, int cantidad) {
        IdProducto = idProducto;
        this.cantidad = cantidad;
    }

    public Long getIdProducto() {
        return IdProducto;
    }

    public int getCantidad() {
        return cantidad;
    }
}

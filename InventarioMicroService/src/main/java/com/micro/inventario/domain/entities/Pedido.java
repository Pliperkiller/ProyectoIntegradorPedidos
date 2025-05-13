package com.micro.inventario.domain.entities;

import java.util.List;

public class Pedido {
    private Long id;
    private List<ItemPedido> items;

    public Pedido(Long id, List<ItemPedido> items) {
        this.id = id;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public List<ItemPedido> getItems() {
        return items;
    }
}

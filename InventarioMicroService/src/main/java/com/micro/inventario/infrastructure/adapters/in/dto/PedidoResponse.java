package com.micro.inventario.infrastructure.adapters.in.dto;

public class PedidoResponse {
    private Long pedidoId;
    private String mensage;

    public PedidoResponse(Long pedidoId, String mensage) {
        this.pedidoId = pedidoId;
        this.mensage = mensage;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getMensage() {
        return mensage;
    }

    public void setMensage(String mensage) {
        this.mensage = mensage;
    }
}

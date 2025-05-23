package com.micro.inventario.infrastructure.adapters.in.rabbitmq;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PedidoMessage {
    @JsonProperty("orderId")
    private Long orderId;
    private List<ItemMessage> items;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<ItemMessage> getItems() {
        return items;
    }

    public void setItems(List<ItemMessage> items) {
        this.items = items;
    }

    public static class ItemMessage {
        @JsonProperty("product_id")
        private Long productId;
        private int amount;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
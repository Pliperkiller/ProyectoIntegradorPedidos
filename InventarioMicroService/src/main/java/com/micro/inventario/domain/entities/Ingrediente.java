package com.micro.inventario.domain.entities;

public class Ingrediente {
    private final Long id;
    private final String nombre;
    private int stock;

    public Ingrediente(Long id, String nombre, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public void usarStock(int cantidad) {
        if (cantidad > stock) {
            throw new IllegalArgumentException("Stock insuficiente para el ingrediente: " + nombre);
        }
        this.stock -= cantidad;
    }

    public void surtirStock(int cantidad) {
        this.stock += cantidad;
    }
}

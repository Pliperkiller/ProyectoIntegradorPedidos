package com.micro.inventario.domain.entities;

public class Producto {
    private Long id;
    private String nombre;
    private Receta receta;

    public Producto(Long id, String nombre, Receta receta) {
        this.id = id;
        this.nombre = nombre;
        this.receta = receta;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Receta getReceta() {
        return receta;
    }
}

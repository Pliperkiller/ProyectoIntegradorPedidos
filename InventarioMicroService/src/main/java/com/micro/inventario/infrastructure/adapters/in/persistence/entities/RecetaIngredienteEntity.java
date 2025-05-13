package com.micro.inventario.infrastructure.adapters.in.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "recetas_ingredientes")
@IdClass(RecetaIngredienteId.class)

public class RecetaIngredienteEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_receta")
    private RecetaEntity receta;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private IngredienteEntity ingrediente;

    private int cantidad;


    public RecetaIngredienteEntity() {
    }

    public RecetaIngredienteEntity(RecetaEntity receta, IngredienteEntity ingrediente, int cantidad) {
        this.receta = receta;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
    }

    public RecetaEntity getReceta() {
        return receta;
    }

    public void setReceta(RecetaEntity receta) {
        this.receta = receta;
    }

    public IngredienteEntity getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteEntity ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}


package com.micro.inventario.infrastructure.adapters.in.persistence.entities;

import java.io.Serializable;
import java.util.Objects;

public class RecetaIngredienteId implements Serializable {
    private Long receta;
    private Long ingrediente;

    public RecetaIngredienteId() {
    }

    public RecetaIngredienteId(Long receta, Long ingrediente) {
        this.receta = receta;
        this.ingrediente = ingrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetaIngredienteId that = (RecetaIngredienteId) o;
        return Objects.equals(receta, that.receta) &&
                Objects.equals(ingrediente, that.ingrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receta, ingrediente);
    }

}

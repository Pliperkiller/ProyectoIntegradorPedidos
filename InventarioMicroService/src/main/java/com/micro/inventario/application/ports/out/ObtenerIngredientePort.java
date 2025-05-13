package com.micro.inventario.application.ports.out;

import com.micro.inventario.domain.entities.Ingrediente;

import java.util.Optional;

public interface ObtenerIngredientePort {
    Optional<Ingrediente> obtenerIngredientePorId(Long id);
}

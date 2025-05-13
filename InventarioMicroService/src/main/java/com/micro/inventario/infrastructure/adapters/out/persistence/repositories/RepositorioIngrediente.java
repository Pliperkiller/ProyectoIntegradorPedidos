package com.micro.inventario.infrastructure.adapters.out.persistence.repositories;

import com.micro.inventario.infrastructure.adapters.in.persistence.entities.IngredienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioIngrediente extends JpaRepository<IngredienteEntity, Long> {
}

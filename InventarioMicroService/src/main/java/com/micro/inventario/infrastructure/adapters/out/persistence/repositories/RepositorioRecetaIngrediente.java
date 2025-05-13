package com.micro.inventario.infrastructure.adapters.out.persistence.repositories;

import com.micro.inventario.infrastructure.adapters.in.persistence.entities.RecetaIngredienteEntity;
import com.micro.inventario.infrastructure.adapters.in.persistence.entities.RecetaIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioRecetaIngrediente extends JpaRepository<RecetaIngredienteEntity, RecetaIngredienteId> {
    List<RecetaIngredienteEntity> findByRecetaId(Long idReceta);

}

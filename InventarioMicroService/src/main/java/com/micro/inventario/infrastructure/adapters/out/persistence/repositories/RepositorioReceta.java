package com.micro.inventario.infrastructure.adapters.out.persistence.repositories;

import com.micro.inventario.infrastructure.adapters.in.persistence.entities.RecetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioReceta extends JpaRepository<RecetaEntity, Long> {
    RecetaEntity findByProductoId(Long idProducto);

}

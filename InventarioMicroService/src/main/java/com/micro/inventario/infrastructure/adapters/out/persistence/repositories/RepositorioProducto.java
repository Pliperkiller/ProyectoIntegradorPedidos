package com.micro.inventario.infrastructure.adapters.out.persistence.repositories;


import com.micro.inventario.infrastructure.adapters.in.persistence.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProducto extends JpaRepository<ProductoEntity, Long> {
}

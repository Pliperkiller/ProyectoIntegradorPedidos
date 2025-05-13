package com.micro.inventario.infrastructure.adapters.in.persistence.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "recetas")
public class RecetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;

    public RecetaEntity() {
    }

    public RecetaEntity(Long id, ProductoEntity producto) {
        this.id = id;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }
}

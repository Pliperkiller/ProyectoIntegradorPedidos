package com.micro.inventario.infrastructure.adapters.out.persistence;

import com.micro.inventario.application.ports.out.ActualizarInventarioPort;
import com.micro.inventario.application.ports.out.ObtenerIngredientePort;
import com.micro.inventario.application.ports.out.ObtenerProductoPort;
import com.micro.inventario.domain.entities.Ingrediente;
import com.micro.inventario.domain.entities.Producto;
import com.micro.inventario.domain.entities.Receta;
import com.micro.inventario.infrastructure.adapters.in.persistence.entities.RecetaEntity;
import com.micro.inventario.infrastructure.adapters.in.persistence.entities.RecetaIngredienteEntity;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioIngrediente;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioProducto;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioReceta;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioRecetaIngrediente;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InventarioPersistenceAdapter implements ObtenerProductoPort, ObtenerIngredientePort, ActualizarInventarioPort {

    private final RepositorioProducto repositorioProducto;
    private final RepositorioIngrediente repositorioIngrediente;
    private final RepositorioReceta repositorioReceta;
    private final RepositorioRecetaIngrediente repositorioRecetaIngrediente;

    public InventarioPersistenceAdapter(RepositorioProducto repositorioProducto, RepositorioIngrediente repositorioIngrediente, RepositorioReceta repositorioReceta, RepositorioRecetaIngrediente repositorioRecetaIngrediente) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioIngrediente = repositorioIngrediente;
        this.repositorioReceta = repositorioReceta;
        this.repositorioRecetaIngrediente = repositorioRecetaIngrediente;
    }

    @Override
    public Optional<Producto> ObtenerProductoPorId(Long id) {
        return repositorioProducto.findById(id)
                .map(productoEntity -> {
                    RecetaEntity recetaEntity = repositorioReceta.findByProductoId(id);
                    if (recetaEntity == null) {
                        return null;
                    }

                    List<RecetaIngredienteEntity> recetaIngredientes =
                            repositorioRecetaIngrediente.findByRecetaId(recetaEntity.getId());

                    Map<Long, Integer> ingredientes = new HashMap<>();
                    for (RecetaIngredienteEntity ri : recetaIngredientes) {
                        ingredientes.put(ri.getIngrediente().getId(), ri.getCantidad());
                    }

                    Receta receta = new Receta(recetaEntity.getId(), id, ingredientes);
                    return new Producto(id, productoEntity.getNombre(), receta);
                });
    }

    @Override
    public Optional<Ingrediente> obtenerIngredientePorId(Long id) {
        return repositorioIngrediente.findById(id)
                .map(entity -> new Ingrediente(entity.getId(), entity.getNombre(), entity.getStock()));
    }

    @Override
    public void actualizarIngrediente(Ingrediente ingrediente) {
        repositorioIngrediente.findById(ingrediente.getId())
                .ifPresent(entity -> {
                    entity.setStock(ingrediente.getStock());
                    repositorioIngrediente.save(entity);
                });
    }

}

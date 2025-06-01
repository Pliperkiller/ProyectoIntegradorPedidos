package com.micro.inventario;

import com.micro.inventario.domain.entities.Ingrediente;
import com.micro.inventario.domain.entities.Producto;
import com.micro.inventario.domain.entities.Receta;
import com.micro.inventario.infrastructure.adapters.in.persistence.entities.IngredienteEntity;
import com.micro.inventario.infrastructure.adapters.in.persistence.entities.ProductoEntity;
import com.micro.inventario.infrastructure.adapters.in.persistence.entities.RecetaEntity;
import com.micro.inventario.infrastructure.adapters.in.persistence.entities.RecetaIngredienteEntity;
import com.micro.inventario.infrastructure.adapters.out.persistence.InventarioPersistenceAdapter;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioIngrediente;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioProducto;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioReceta;
import com.micro.inventario.infrastructure.adapters.out.persistence.repositories.RepositorioRecetaIngrediente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioApplicationTests {
	@Mock
	private RepositorioProducto repositorioProducto;

	@Mock
	private RepositorioIngrediente repositorioIngrediente;

	@Mock
	private RepositorioReceta repositorioReceta;

	@Mock
	private RepositorioRecetaIngrediente repositorioRecetaIngrediente;

	@InjectMocks
	private InventarioPersistenceAdapter adapter;

	@Test
	public void getProductByIdTest() {
		Long id = 1L;

		ProductoEntity producto = new ProductoEntity(id, "Hamburguesa");

		when(repositorioProducto.findById(id)).thenReturn(Optional.of(producto));

		RecetaEntity recetaEntity = new RecetaEntity(1L, producto);

		when(repositorioReceta.findByProductoId(id)).thenReturn(recetaEntity);

		IngredienteEntity ingredienteEntity = new IngredienteEntity(1L,"Tomate",10);

		RecetaIngredienteEntity recetaIngredienteEntity = new RecetaIngredienteEntity(recetaEntity, ingredienteEntity, 20);

		when(repositorioRecetaIngrediente.findByRecetaId(id)).thenReturn(List.of(recetaIngredienteEntity));


		Optional<Producto> productoOptional = adapter.ObtenerProductoPorId(id);


		assertTrue(productoOptional.isPresent());
		Producto productoResult = productoOptional.get();
		assertEquals("Hamburguesa", producto.getNombre());
		assertNotNull(productoResult.getReceta());
		assertEquals(1, productoResult.getReceta().getIngredientes().size());
	}

	@Test
	public void getProductById_ProductNotFound_ReturnsEmptyOptionalTest() {
		Long id = 1L;
		when(repositorioProducto.findById(id)).thenReturn(Optional.empty());

		Optional<Producto> productoOptional = adapter.ObtenerProductoPorId(id);

		assertTrue(productoOptional.isEmpty());
	}

	@Test
	public void getProductById_RecetaNotFound_ReturnsEmptyOptionalTest() {
		Long id = 1L;
		ProductoEntity productoEntity = new ProductoEntity(id, "Pizza");

		when(repositorioProducto.findById(id)).thenReturn(Optional.of(productoEntity));
		when(repositorioReceta.findByProductoId(id)).thenReturn(null);


		Optional<Producto> productoOptional = adapter.ObtenerProductoPorId(id);

		assertTrue(productoOptional.isEmpty());
	}

	@Test
	public void getProductById_RecetaWithoutIngredientes_ReturnsProductoWithEmptyIngredientesTest() {
		Long id = 1L;
		ProductoEntity productoEntity = new ProductoEntity(id, "Ensalada");
		RecetaEntity recetaEntity = new RecetaEntity(1L, productoEntity);

		when(repositorioProducto.findById(id)).thenReturn(Optional.of(productoEntity));
		when(repositorioReceta.findByProductoId(id)).thenReturn(recetaEntity);
		when(repositorioRecetaIngrediente.findByRecetaId(recetaEntity.getId())).thenReturn(List.of());

		Optional<Producto> productoOptional = adapter.ObtenerProductoPorId(id);

		assertTrue(productoOptional.isPresent());
		assertNotNull(productoOptional.get().getReceta());
		assertTrue(productoOptional.get().getReceta().getIngredientes().isEmpty());
	}

	@Test
	public void getProductById_WithMultipleIngredientes_ReturnsProductoWithCorrectMapTest() {
		Long id = 1L;
		ProductoEntity productoEntity = new ProductoEntity(id, "Taco");
		RecetaEntity recetaEntity = new RecetaEntity(1L, productoEntity);

		IngredienteEntity ing1 = new IngredienteEntity(1L, "Carne", 5);
		IngredienteEntity ing2 = new IngredienteEntity(2L, "Cebolla", 3);

		RecetaIngredienteEntity ri1 = new RecetaIngredienteEntity(recetaEntity, ing1, 100);
		RecetaIngredienteEntity ri2 = new RecetaIngredienteEntity(recetaEntity, ing2, 50);

		when(repositorioProducto.findById(id)).thenReturn(Optional.of(productoEntity));
		when(repositorioReceta.findByProductoId(id)).thenReturn(recetaEntity);
		when(repositorioRecetaIngrediente.findByRecetaId(recetaEntity.getId())).thenReturn(List.of(ri1, ri2));


		Optional<Producto> productoOptional = adapter.ObtenerProductoPorId(id);

		assertTrue(productoOptional.isPresent());
		Receta receta = productoOptional.get().getReceta();
		assertEquals(2, receta.getIngredientes().size());
		assertEquals(100, receta.getIngredientes().get(1L));
		assertEquals(50, receta.getIngredientes().get(2L));
	}

	@Test
	public void obtenerIngredientePorId_deberiaRetornarIngredienteCuandoExiste() {
		Long id = 1L;
		IngredienteEntity entity = new IngredienteEntity(id, "Tomate", 100);

		when(repositorioIngrediente.findById(id)).thenReturn(Optional.of(entity));

		Optional<Ingrediente> resultado = adapter.obtenerIngredientePorId(id);

		assertTrue(resultado.isPresent());
		assertEquals("Tomate", resultado.get().getNombre());
		assertEquals(100, resultado.get().getStock());
	}

	@Test
	public void obtenerIngredientePorId_deberiaRetornarOptionalVacioCuandoNoExiste() {
		Long id = 2L;
		when(repositorioIngrediente.findById(id)).thenReturn(Optional.empty());

		Optional<Ingrediente> resultado = adapter.obtenerIngredientePorId(id);

		assertFalse(resultado.isPresent());
	}

	@Test
	public void actualizarIngrediente_deberiaActualizarStockCuandoExiste() {
		Long id = 3L;
		IngredienteEntity entity = new IngredienteEntity(id, "Lechuga", 50);
		Ingrediente nuevo = new Ingrediente(id, "Lechuga", 80);

		when(repositorioIngrediente.findById(id)).thenReturn(Optional.of(entity));

		adapter.actualizarIngrediente(nuevo);

		assertEquals(80, entity.getStock());
		verify(repositorioIngrediente, times(1)).save(entity);
	}

	@Test
	public void actualizarIngrediente_noHaceNadaSiIngredienteNoExiste() {
		Long id = 4L;
		Ingrediente nuevo = new Ingrediente(id, "Cebolla", 70);

		when(repositorioIngrediente.findById(id)).thenReturn(Optional.empty());

		adapter.actualizarIngrediente(nuevo);

		verify(repositorioIngrediente, never()).save(any());
	}

}

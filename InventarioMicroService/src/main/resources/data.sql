-- Insertar ingredientes
INSERT INTO ingredientes (nombre, stock) VALUES ('Cheese', 100);
INSERT INTO ingredientes (nombre, stock) VALUES ('Tomato', 80);
INSERT INTO ingredientes (nombre, stock) VALUES ('Flour', 200);
INSERT INTO ingredientes (nombre, stock) VALUES ('Meat', 50);
INSERT INTO ingredientes (nombre, stock) VALUES ('Bread', 200);

-- Insertar productos
INSERT INTO productos (nombre) VALUES ('Pizza Margherita');
INSERT INTO productos (nombre) VALUES ('Burger');

-- -- Insertar recetas
INSERT INTO recetas (id_producto) VALUES (1);
INSERT INTO recetas (id_producto) VALUES (2);

-- -- Insertar ingredientes de recetas
-- -- Para Pizza Margherita
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (1, 1, 3); -- 3 unidades de queso
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (1, 2, 2); -- 2 unidades de tomate
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (1, 3, 5); -- 5 unidades de harina

-- -- Para Burger
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (2, 1, 1); -- 1 unidad de queso
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (2, 2, 1); -- 1 unidad de tomate
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (2, 4, 2); -- 2 unidades de carne
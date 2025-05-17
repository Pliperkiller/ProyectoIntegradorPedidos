-- Insertar ingredientes
INSERT INTO ingredientes (nombre, stock) VALUES ('Bread', 200); -- Pan
INSERT INTO ingredientes (nombre, stock) VALUES ('Potato', 300); -- Papa
INSERT INTO ingredientes (nombre, stock) VALUES ('Sausage', 150); -- Salchicha
INSERT INTO ingredientes (nombre, stock) VALUES ('Pasta', 100); -- Pasta
INSERT INTO ingredientes (nombre, stock) VALUES ('Corn', 250); -- Maíz
INSERT INTO ingredientes (nombre, stock) VALUES ('Meat', 100); -- Carne

-- Insertar productos
INSERT INTO productos (nombre) VALUES ('Burguer');
INSERT INTO productos (nombre) VALUES ('Fries');
INSERT INTO productos (nombre) VALUES ('Hot dog');
INSERT INTO productos (nombre) VALUES ('Spaggethy');
INSERT INTO productos (nombre) VALUES ('Corn');

-- Insertar recetas
INSERT INTO recetas (id_producto) VALUES (1); -- Receta para Burguer
INSERT INTO recetas (id_producto) VALUES (2); -- Receta para Fries
INSERT INTO recetas (id_producto) VALUES (3); -- Receta para Hot dog
INSERT INTO recetas (id_producto) VALUES (4); -- Receta para Spaggethy
INSERT INTO recetas (id_producto) VALUES (5); -- Receta para Corn

-- Insertar ingredientes de recetas
-- Para Burguer
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (1, 1, 1); -- 1 unidad de pan
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (1, 6, 2); -- 2 unidades de carne

-- Para Fries
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (2, 2, 3); -- 3 unidades de papa

-- Para Hot dog
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (3, 1, 1); -- 1 unidad de pan
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (3, 3, 1); -- 1 unidad de salchicha

-- Para Spaggethy
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (4, 4, 1); -- 1 unidad de pasta

-- Para Corn
INSERT INTO recetas_ingredientes (id_receta, id_ingrediente, cantidad) VALUES (5, 5, 1); -- 1 unidad de maíz
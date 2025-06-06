# Proyecto Integrador de Pedidos e Inventario

Este proyecto es una solución basada en microservicios que integra dos servicios principales: **Pedidos** y **Inventario**. Está diseñado para gestionar pedidos de clientes y verificar la disponibilidad de inventario en tiempo real. Los microservicios están implementados utilizando **Python (Flask)** y **Java (Spring Boot)**, y se comunican entre sí mediante **RabbitMQ** como sistema de mensajería.

## Tabla de Contenidos
- [Arquitectura](#arquitectura)
- [Microservicios](#microservicios)
  - [Microservicio de Pedidos](#microservicio-de-pedidos)
  - [Microservicio de Inventario](#microservicio-de-inventario)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Configuración del Proyecto](#configuración-del-proyecto)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
- [Endpoints Disponibles](#endpoints-disponibles)

---

## Arquitectura

El proyecto sigue una arquitectura basada en microservicios, donde cada servicio es independiente y se comunica con otros mediante **RabbitMQ**. Los servicios principales son:

1. **Microservicio de Pedidos**:
   - Gestiona la creación, actualización y eliminación de pedidos.
   - Publica eventos en RabbitMQ para notificar al servicio de inventario sobre los pedidos realizados.

2. **Microservicio de Inventario**:
   - Gestiona el inventario de productos.
   - Escucha eventos de RabbitMQ para actualizar el inventario en función de los pedidos procesados.

### Diagrama de Arquitectura

```plaintext
+-------------------+       +-------------------+       +-------------------+
| Microservicio     |       | RabbitMQ          |       | Microservicio     |
| Pedidos (Flask)   | <---> | (Mensajería)      | <---> | Inventario (Java) |
+-------------------+       +-------------------+       +-------------------+
       |                                                        |
       |                                                        |
+-------------------+                                  +-------------------+
| Base de Datos     |                                  | Base de Datos     |
| PostgreSQL        |                                  | MySQL             |
+-------------------+                                  +-------------------+
```

---

## Microservicios

### Microservicio de Pedidos
- **Lenguaje**: Python (Flask)
- **Base de Datos**: PostgreSQL
- **Responsabilidades**:
  - Crear, actualizar, eliminar y consultar pedidos.
  - Publicar eventos en RabbitMQ para notificar al servicio de inventario.

#### Código relevante:
- **Controlador**: 

order_controller.py


- **Servicio**: 

order_service_impl.py



---

### Microservicio de Inventario
- **Lenguaje**: Java (Spring Boot)
- **Base de Datos**: MySQL
- **Responsabilidades**:
  - Gestionar el inventario de productos.
  - Escuchar eventos de RabbitMQ para actualizar el inventario en función de los pedidos procesados.

#### Código relevante:
- **Controlador**: 

PedidoController.java


- **Servicio**: 

ProcesarPedidoUseCase



---

## Tecnologías Utilizadas

- **Lenguajes**:
  - Python (Flask)
  - Java (Spring Boot)
- **Bases de Datos**:
  - PostgreSQL (Pedidos)
  - MySQL (Inventario)
- **Mensajería**:
  - RabbitMQ
- **Contenedores**:
  - Docker y Docker Compose
- **Documentación**:
  - Swagger para APIs REST

---

## Requisitos Previos

1. **Docker** y **Docker Compose** instalados.
2. **Java 11+** y **Python 3.9+** instalados (si deseas ejecutar los servicios localmente sin Docker).
3. **RabbitMQ** configurado (se incluye en el archivo docker-compose.yml).

---

## Configuración del Proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tu-usuario/ProyectoIntegradorPedidos.git
   cd ProyectoIntegradorPedidos
   ```

2. Asegúrate de que Docker esté ejecutándose.

3. Construye las imágenes y levanta los contenedores:
   ```bash
   docker-compose up --build
   ```

---

## Ejecución del Proyecto

1. Una vez que los contenedores estén en ejecución, los servicios estarán disponibles en las siguientes rutas:
   - **Microservicio de Pedidos**: 

    http://localhost:5000


   - **Microservicio de Inventario**: 

    http://localhost:8081


   - **RabbitMQ Management**: 

    http://localhost:15672

 (usuario: `guest`, contraseña: `guest`)

2. Verifica los endpoints disponibles accediendo a las rutas de Swagger:
   - Pedidos: 

    http://localhost:5000/swagger


   - Inventario: 

    http://localhost:8081/swagger-ui.html



---

## Endpoints Disponibles

### Microservicio de Pedidos
| Método | Endpoint              | Descripción                          |
|--------|-----------------------|--------------------------------------|
| POST   | `/api/v1/orders`      | Crear un nuevo pedido                |
| GET    | `/api/v1/orders/<id>` | Obtener un pedido por ID             |
| PUT    | `/api/v1/orders/<id>` | Actualizar el estado de un pedido    |
| DELETE | `/api/v1/orders/<id>` | Eliminar un pedido                   |

### Microservicio de Inventario
| Método | Endpoint              | Descripción                          |
|--------|-----------------------|--------------------------------------|
| POST   | `/api/inventario/procesar` | Procesar un pedido                  |
| GET    | `/api/inventario/status`   | Verificar el estado de la API       |
| GET    | `/api/inventario/status/db` | Verificar la conexión a la base de datos |


---

## Notas Adicionales

- Asegúrate de que los servicios estén en la misma red (`red_global`) para que puedan comunicarse correctamente.
- Si tienes problemas con RabbitMQ o las bases de datos, verifica los logs de los contenedores:
  ```bash
  docker logs <nombre-del-contenedor>
  ```

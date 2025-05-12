# Orders MicroService

Este proyecto es un microservicio para la gestión de pedidos, desarrollado en Python utilizando Flask. Proporciona endpoints RESTful para crear, actualizar, eliminar y consultar pedidos, así como para gestionar los elementos de los pedidos y los productos.

---

## **Requisitos**

Antes de comenzar, asegúrate de tener instalados los siguientes requisitos:

- Python 3.8 o superior
- PostgreSQL (o SQLite para pruebas locales)
- Pipenv o un gestor de dependencias como `pip`

---

## **Instalación**

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/Pliperkiller/OrdersMicroservice
   cd OrdersMicroservice
   ```

2. **Crea un entorno virtual:**
   ```bash
   python -m venv venv
   source venv/bin/activate  # En Windows: venv\Scripts\activate
   ```

3. **Instala las dependencias:**
   ```bash
   pip install -r requirements.txt
   ```

4. **Configura el entorno:**
   - Configura las variables de entorno en el archivo config.py:
    ```bash
    import os
    class Config:
        SQLALCHEMY_DATABASE_URI = os.getenv('DATABASE_URL', 'postgresql://postgres:postgres@localhost:5432/orders_db')
        SQLALCHEMY_TRACK_MODIFICATIONS = False
        DEBUG = True
    ```


---

## **Ejecución**

1. **Inicia la api:**
   ```bash
   python app.py
   ```

2. **Accede a la API:**
   - La API estará disponible en `http://127.0.0.1:5000`.

---

## **Endpoints**

### **Pedidos**
- **Crear un pedido:**
  - `POST /api/v1/orders`
  - **Body:**
    ```json
    {
      "client_id": 123,
      "items": [
        {"product_id": 1, "amount": 2},
        {"product_id": 2, "amount": 1}
      ]
    }
    ```

- **Obtener un pedido por ID:**
  - `GET /api/v1/orders/<order_id>`

- **Actualizar el estado de un pedido:**
  - `PUT /api/v1/orders/<order_id>/status`
  - **Body:**
    ```json
    {
      "status": "COMPLETED"
    }
    ```

- **Eliminar un pedido:**
  - `DELETE /api/v1/orders/<order_id>`

---

## **Pruebas**

1. **Ejecuta las pruebas:**
   ```bash
   pytest
   ```

2. **Genera un reporte de cobertura:**
   ```bash
   pytest --cov=src --cov-report=html
   ```

3. **Accede al reporte de cobertura:**
   - Abre el archivo 

index.html

 en tu navegador.

---

## **Estructura del proyecto**

```plaintext
PedidosMicroService/
├── src/
│   ├── application/
│   ├── domain/
│   ├── infrastructure/
│   ├── main.py
├── tests/
│   ├── test_order_endpoints.py
│   ├── test_repositories.py
│   ├── test_services.py
├── app.py
├── requirements.txt
├── .gitignore
├── README.md
```

## **Ejecución con Docker**

Puedes ejecutar el proyecto utilizando Docker y Docker Compose. Sigue estos pasos:

1. **Construye y ejecuta los servicios:**
   ```bash
   docker-compose up --build
   ```

2. **Accede a la aplicación:**
   - La API estará disponible en `http://127.0.0.1:5000`.

3. **Detén los servicios:**
   ```bash
   docker-compose down
   ```

4. **Nota sobre el puerto de la base de datos:**
   - Si el puerto `5432` ya está en uso en tu máquina, puedes cambiar el puerto externo en el archivo

docker-compose.yml

. Por ejemplo:
     ```yaml
     db:
       ports:
         - "5433:5432"  # Cambia el puerto externo a 5433
     ```
   - Luego, asegúrate de reiniciar los servicios con:
     ```bash
     docker-compose down
     docker-compose up --build
     ```

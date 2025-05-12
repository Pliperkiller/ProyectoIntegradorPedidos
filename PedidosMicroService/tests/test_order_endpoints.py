def test_create_order(client):
    order_data = {
        "client_id": 123,
        "items": [
            {"product_id": 1, "amount": 2},
            {"product_id": 2, "amount": 3}
        ]
    }

    response = client.post('/api/v1/orders', json=order_data)

    assert response.status_code == 201
    response_data = response.get_json()
    assert response_data['client_id'] == 123
    assert len(response_data['items']) == 2


def test_update_order_status(client):
    order_data = {
        "client_id": 123,
        "items": [{"product_id": 1, "amount": 2}]
    }
    create_response = client.post('/api/v1/orders', json=order_data)
    order_id = create_response.get_json()['id']

    status_data = {"status": "COMPLETED"}
    response = client.put(f'/api/v1/orders/{order_id}/status', json=status_data)

    assert response.status_code == 200
    response_data = response.get_json()
    assert response_data['status'] == "COMPLETED"


def test_get_all_orders(client):

    order_data = {
        "client_id": 123,
        "items": [{"product_id": 1, "amount": 2}]
    }
    client.post('/api/v1/orders', json=order_data)

    response = client.get('/api/v1/orders')
    assert response.status_code == 200
    response_data = response.get_json()
    assert len(response_data) > 0


def test_create_order_repository(app):
    from src.infrastructure.adapters.repositories.posgre.pg_order_repository import PgOrderRepository
    from src.domain.entities.order import Order
    from src.domain.value_objects.order_status import OrderStatus

    with app.app_context():
        repository = PgOrderRepository()
        order = Order(client_id=123, status=OrderStatus.PENDING , total_value=100.0)
        created_order = repository.create(order)

        assert created_order.id is not None
        assert created_order.client_id == 123


def test_create_order_item_repository(app):
    from src.infrastructure.adapters.repositories.posgre.pg_order_item_repository import PgOrderItemRepository
    from src.infrastructure.adapters.repositories.posgre.pg_order_repository import PgOrderRepository
    from src.infrastructure.adapters.repositories.posgre.pg_product_repository import PgProductRepository
    from src.domain.entities.order import Order
    from src.domain.entities.order_item import OrderItem
    from src.domain.value_objects.order_status import OrderStatus

    with app.app_context():
        # Crea un pedido en la base de datos
        order_repository = PgOrderRepository()
        order = Order(client_id=123, status=OrderStatus.PENDING, total_value=200.0)
        created_order = order_repository.create(order)

        # Obtén un producto existente
        product_repository = PgProductRepository()
        product = product_repository.get_by_id(1)

        # Crea un elemento del pedido
        order_item_repository = PgOrderItemRepository()
        order_item = OrderItem(product=product, amount=2)

        # Inserta el elemento del pedido asociado al pedido creado
        created_item = order_item_repository.create(created_order.id, order_item)

        # Verifica que el elemento del pedido se haya creado correctamente
        assert created_item.id is not None
        assert created_item.amount == 2

def test_get_order_success(client):
    # Crea un pedido de prueba
    order_data = {
        "client_id": 123,
        "items": [{"product_id": 1, "amount": 2}]
    }
    create_response = client.post('/api/v1/orders', json=order_data)
    assert create_response.status_code == 201
    created_order = create_response.get_json()

    # Verifica que el pedido se haya creado correctamente
    assert created_order is not None

    # Obtén el pedido creado
    response = client.get(f'/api/v1/orders/{created_order["id"]}')
    assert response.status_code == 200
    response_data = response.get_json()
    assert response_data is not None

    # Verifica los datos del pedido
    assert response_data['id'] == created_order['id']
    assert response_data['client_id'] == 123
    assert len(response_data['items']) == 1
    assert response_data['items'][0]['amount'] == 2


def test_get_order_not_found(client):
    # Intenta obtener un pedido con un ID inexistente
    response = client.get('/api/v1/orders/9999')  # ID que no existe
    assert response.status_code == 404
    response_data = response.get_json()

    # Verifica el mensaje de error
    assert response_data['error'] == "Pedido no encontrado"


def test_delete_order_success(client):
    # Crea un pedido de prueba
    order_data = {
        "client_id": 123,
        "items": [{"product_id": 1, "amount": 2}]
    }
    create_response = client.post('/api/v1/orders', json=order_data)
    assert create_response.status_code == 201
    created_order = create_response.get_json()

    # Verifica que el pedido se haya creado correctamente
    assert created_order is not None

    # Elimina el pedido creado
    response = client.delete(f'/api/v1/orders/{created_order["id"]}')
    assert response.status_code == 200
    response_data = response.get_json()

    # Verifica la respuesta de eliminación
    assert response_data['message'] == "Pedido eliminado exitosamente"

    # Verifica que el pedido ya no exista
    get_response = client.get(f'/api/v1/orders/{created_order["id"]}')
    assert get_response.status_code == 404
    get_response_data = get_response.get_json()
    assert get_response_data['error'] == "Pedido no encontrado"

def test_delete_order_not_found(client):
    # Intenta eliminar un pedido con un ID inexistente
    response = client.delete('/api/v1/orders/9999')  # ID que no existe
    assert response.status_code == 404
    response_data = response.get_json()

    # Verifica el mensaje de error
    assert response_data['error'] == "Pedido no encontrado"

def test_create_order_publishes_message(client, mocker):
    # Mock del adaptador de RabbitMQ
    mock_broker = mocker.patch('src.infrastructure.adapters.message_broker.rabbitmq_message_broker.RabbitMQMessageBroker.publish')

    # Datos del pedido
    order_data = {
        "client_id": 123,
        "items": [{"product_id": 1, "amount": 2}, {"product_id": 2, "amount": 1}]
    }

    # Crear el pedido
    response = client.post('/api/v1/orders', json=order_data)
    assert response.status_code == 201

    # Verificar que se haya llamado al método publish con los datos correctos
    mock_broker.assert_called_once_with("order_items_queue", {"items": order_data["items"]})
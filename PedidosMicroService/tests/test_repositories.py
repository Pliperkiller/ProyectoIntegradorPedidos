def test_create_order_repository(app):
    from src.infrastructure.adapters.repositories.posgre.pg_order_repository import PgOrderRepository
    from src.domain.entities.order import Order
    from src.domain.value_objects.order_status import OrderStatus

    with app.app_context():
        repository = PgOrderRepository()
        order = Order(client_id=123, status=OrderStatus.PENDING, total_value=100.0)
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

def test_get_order_item_not_found(app):
    from src.infrastructure.adapters.repositories.posgre.pg_order_item_repository import PgOrderItemRepository

    with app.app_context():
        order_item_repository = PgOrderItemRepository()
        result = order_item_repository.get_by_id(999)

        # Verifica que el resultado sea None
        assert result is None

def test_delete_order_items_by_order_id(app):
    from src.infrastructure.adapters.repositories.posgre.pg_order_item_repository import PgOrderItemRepository
    from src.infrastructure.adapters.repositories.posgre.pg_order_repository import PgOrderRepository
    from src.infrastructure.adapters.repositories.posgre.pg_product_repository import PgProductRepository
    from src.domain.entities.order import Order
    from src.domain.entities.order_item import OrderItem
    from src.infrastructure.database.models.order_item_model import OrderItemModel

    with app.app_context():
        # Crea un pedido en la base de datos
        order_repository = PgOrderRepository()
        order = Order(client_id=123, status="PENDING", total_value=200.0)
        created_order = order_repository.create(order)

        # Obtén un producto existente
        product_repository = PgProductRepository()
        product = product_repository.get_by_id(1)

        # Crea un elemento del pedido
        order_item_repository = PgOrderItemRepository()
        order_item = OrderItem(product=product, amount=2)
        created_item = order_item_repository.create(created_order.id, order_item)

        # Verifica que el elemento del pedido se haya creado correctamente
        assert created_item.id is not None

        # Elimina todos los elementos del pedido asociados al pedido creado
        result = order_item_repository.delete_by_order_id(created_order.id)

        # Verifica que el método haya devuelto True
        assert result is True

        # Verifica que no existan elementos del pedido asociados al pedido
        items = OrderItemModel.query.filter_by(order_id=created_order.id).all()
        assert len(items) == 0
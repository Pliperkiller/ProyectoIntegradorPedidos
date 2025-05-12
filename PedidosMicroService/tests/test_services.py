def test_create_order_service(app):
    from src.application.services.order_service_impl import OrderServiceImpl
    from src.infrastructure.adapters.repositories.posgre.pg_order_repository import PgOrderRepository
    from src.infrastructure.adapters.repositories.posgre.pg_order_item_repository import PgOrderItemRepository
    from src.infrastructure.adapters.repositories.posgre.pg_product_repository import PgProductRepository

    with app.app_context():
        order_repository = PgOrderRepository()
        order_item_repository = PgOrderItemRepository()
        product_repository = PgProductRepository()

        service = OrderServiceImpl(order_repository, order_item_repository, product_repository)
        order = service.create_order(123, [{"product_id": 1, "amount": 2}])

        assert order.id is not None
        assert order.client_id == 123

def test_create_order_invalid_data(app):
    from src.domain.exceptions.invalid_order_data_exception import InvalidOrderDataException
    from src.application.services.order_service_impl import OrderServiceImpl
    from src.infrastructure.adapters.repositories.posgre.pg_order_repository import PgOrderRepository
    from src.infrastructure.adapters.repositories.posgre.pg_order_item_repository import PgOrderItemRepository
    from src.infrastructure.adapters.repositories.posgre.pg_product_repository import PgProductRepository

    with app.app_context():


        order_repository = PgOrderRepository()
        order_item_repository = PgOrderItemRepository()
        product_repository = PgProductRepository()

        service = OrderServiceImpl(order_repository, order_item_repository, product_repository)

        try:
            service.create_order(123, [{"product_id": 1, "amount": -2}])
            assert False, "Expected InvalidOrderDataException"
        except InvalidOrderDataException:
            assert True
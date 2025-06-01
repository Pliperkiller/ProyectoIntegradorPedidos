from src.domain.ports.output.order_repository import OrderRepository
from src.domain.ports.output.order_item_repository import OrderItemRepository
from src.domain.ports.output.product_repository import ProductRepository
from src.application.usecases.create_order_usecase_impl import CreateOrderUsecaseImpl
from src.application.usecases.delete_order_usecase_impl import DeleteOrderUseCaseImpl
from src.application.usecases.get_order_usecase_impl import GetOrderUsecaseImpl
from src.application.usecases.update_order_usecase_impl import UpdateOrderUsecaseImpl
from src.application.services.order_service import OrderService
from src.domain.exceptions.invalid_order_data_exception import InvalidOrderDataException
from src.domain.ports.output.message_broker import MessageBroker
from typing_extensions import override


class OrderServiceImpl(OrderService):
    def __init__(self,
                 order_repository: OrderRepository,
                 order_item_repository: OrderItemRepository,
                 product_repository: ProductRepository,
                 message_broker: MessageBroker
                 ):

        self.create_order_usecase = CreateOrderUsecaseImpl(order_repository,
                                                            order_item_repository,
                                                            product_repository,
                                                            message_broker)
        
        self.delete_order_usecase = DeleteOrderUseCaseImpl(order_repository,
                                                            order_item_repository)
        
        self.get_order_usecase = GetOrderUsecaseImpl(order_repository)

        self.update_order_usecase = UpdateOrderUsecaseImpl(order_repository)

    @override
    def create_order(self, client_id, order_items):
        if not order_items:
            raise InvalidOrderDataException("Order must have at least one item.")

        for item in order_items:
            if item["amount"] <= 0:
                error_message = f"Invalid amount: {item['amount']} for product {item['product_id']}"
                raise InvalidOrderDataException(error_message)
            
        return self.create_order_usecase.create_order(client_id, order_items)
    
    @override
    def delete_order(self, order_id):
        return self.delete_order_usecase.delete_order(order_id)
    
    @override
    def get_order(self, order_id):
        return self.get_order_usecase.get_order(order_id)
    
    @override
    def get_all_orders(self):
        return self.get_order_usecase.get_all_orders()
    
    @override
    def update_order(self, order_id, order):
        return self.update_order_usecase.update_order(order_id, order)
    
    @override
    def update_order_status(self, order_id, new_status):
        return self.update_order_usecase.update_order_status(order_id, new_status)
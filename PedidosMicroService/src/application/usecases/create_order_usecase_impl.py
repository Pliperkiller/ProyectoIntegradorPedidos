from src.domain.ports.output.order_repository import OrderRepository
from src.domain.ports.output.order_item_repository import OrderItemRepository
from src.domain.ports.output.product_repository import ProductRepository
from src.domain.ports.output.message_broker import MessageBroker
from src.domain.entities.order_item import OrderItem
from src.domain.entities.factories.order_builder import OrderBuilder
from src.domain.ports.input.create_order_usecase import CreateOrderUsecase
from typing_extensions import override
from typing import List, Dict


class CreateOrderUsecaseImpl(CreateOrderUsecase):
    def __init__(self,
                 order_repository : OrderRepository,
                 order_item_repository : OrderItemRepository,
                 product_repository : ProductRepository,
                 message_broker: MessageBroker
                 ):
        self.order_repository = order_repository
        self.order_item_repository = order_item_repository
        self.product_repository = product_repository
        self.message_broker = message_broker

    @override
    def create_order(self, client_id: int, order_items: List[Dict[str, int]]):
        
        builder = OrderBuilder()
        builder.set_client_id(client_id)

        items = []
        for item in order_items:
            product_id = item['product_id']
            amount = item['amount']
            product = self.product_repository.get_by_id(product_id)
            order_item = OrderItem(product=product, amount=amount)
            items.append(order_item)
            builder.add_item(order_item)

        builder.calculate_total()
        order = builder.build()
        created_order = self.order_repository.create(order)

        for item in items:
            self.order_item_repository.create(created_order.id,item)

        self.message_broker.publish("order_items_queue", 
                                    {"orderId": created_order.id,
                                     "items": order_items})

        return order


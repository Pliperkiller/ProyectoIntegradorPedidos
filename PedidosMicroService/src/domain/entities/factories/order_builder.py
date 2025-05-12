
from src.domain.entities.order import Order
from src.domain.entities.order_item import OrderItem

class OrderBuilder:
    def __init__(self):
        self._order = Order()

    def set_id(self, id: int):
        self._order.id = id
        return self

    def set_client_id(self, client_id: int):
        self._order.client_id = client_id
        return self

    def add_item(self, item: OrderItem):
        self._order.items.append(item)
        return self
    
    def calculate_total(self):
        self._order.calculate_total()
        return self

    def build(self) -> Order:
        order = self._order
        self._order = None
        return order
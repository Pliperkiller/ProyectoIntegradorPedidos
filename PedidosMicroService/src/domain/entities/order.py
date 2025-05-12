from src.domain.value_objects.order_status import OrderStatus
from src.domain.entities.order_item import OrderItem

class Order:
    def __init__(self, 
                 id : int = None,
                 client_id : int = None, 
                 status : OrderStatus = OrderStatus.PENDING, 
                 items : list[OrderItem] = None, 
                 total_value : float = 0):
        self.id = id
        self.client_id = client_id
        self.status = OrderStatus(status) if isinstance(status, str) else status
        self.items = items if items else []
        self.total_value = total_value

    def calculate_total(self):
        self.total_value = sum(item.subtotal for item in self.items)
        return self.total_value

    def add_item(self, item):
        self.items.append(item)
        self.calculate_total()

    def update_status(self, new_status:OrderStatus):
        if new_status in OrderStatus:
            self.status = new_status
            return True
        return False

    def to_dict(self):
        return {
            "id": self.id,
            "client_id": self.client_id,
            "status": self.status.value,
            "items": [item.to_dict() for item in self.items],
            "total_value": self.total_value
        }
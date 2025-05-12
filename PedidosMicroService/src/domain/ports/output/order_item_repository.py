from abc import ABC, abstractmethod
from src.domain.entities.order_item import OrderItem
class OrderItemRepository(ABC):
    @abstractmethod
    def create(self, order_id : int ,order_item : OrderItem):
        pass
        
    @abstractmethod
    def get_by_id(self, item_id : int):
        pass
        
    @abstractmethod
    def delete_by_order_id(self, order_id : int):
        pass
        
    @abstractmethod
    def get_by_order_id(self, order_id : int):
        pass
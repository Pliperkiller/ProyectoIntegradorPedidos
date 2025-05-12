from abc import ABC, abstractmethod
from typing import List, Dict
from src.domain.entities.order import Order
from src.domain.value_objects.order_status import OrderStatus

class OrderService(ABC):

    @abstractmethod
    def create_order(self, client_id : int, order_items: List[Dict[str, int]]) -> Order:
        pass
    @abstractmethod
    def delete_order(self, order_id : int) -> bool:
        pass
    
    @abstractmethod
    def get_order(self, order_id : int) -> Order:
        pass
    
    @abstractmethod
    def get_all_orders(self) -> List[Order]:
        pass
    
    @abstractmethod
    def update_order(self, order_id : int, order):
        pass
    
    @abstractmethod
    def update_order_status(self, order_id : int, new_status : str) -> Order:
        pass
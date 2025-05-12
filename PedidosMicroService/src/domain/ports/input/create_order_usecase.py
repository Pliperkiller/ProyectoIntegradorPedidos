from abc import ABC, abstractmethod
from src.domain.entities.order_item import OrderItem
from typing import List, Dict


class CreateOrderUsecase(ABC):
    @abstractmethod
    def create_order(self, client_id: int, order_items: List[Dict[str, int]]):
        pass
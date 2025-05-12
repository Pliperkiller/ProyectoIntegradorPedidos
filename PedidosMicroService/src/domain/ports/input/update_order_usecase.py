from abc import ABC, abstractmethod
from src.domain.entities.order import Order
from src.domain.value_objects.order_status import OrderStatus 

class UpdateOrderUsecase(ABC):

    @abstractmethod
    def update_order(self,order_id:int,  order:Order):
        pass

    @abstractmethod
    def update_order_status(self, order_id: int, new_status: OrderStatus):
        pass
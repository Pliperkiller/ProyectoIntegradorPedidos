from abc import ABC, abstractmethod

class GetOrderUsecase(ABC):
    @abstractmethod
    def get_order(self, order_id:int):
        pass

    @abstractmethod
    def get_all_orders(self):
        pass
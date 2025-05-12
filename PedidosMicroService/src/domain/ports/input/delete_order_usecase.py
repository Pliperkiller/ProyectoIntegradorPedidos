from abc import ABC, abstractmethod

class DeleteOrderUseCase(ABC):
    @abstractmethod
    def delete_order(self, order_id: int) -> bool:
        pass
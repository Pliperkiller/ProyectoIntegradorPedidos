
from src.domain.ports.input.delete_order_usecase import DeleteOrderUseCase
from src.domain.ports.output.order_repository import OrderRepository
from src.domain.ports.output.order_item_repository import OrderItemRepository
from typing_extensions import override

class DeleteOrderUseCaseImpl(DeleteOrderUseCase):
    def __init__(self,
                 order_repository:OrderRepository,
                 order_item_repository:OrderItemRepository):
        self.order_repository = order_repository
        self.order_item_repository = order_item_repository

    @override
    def delete_order(self, order_id):
        try:
            self.order_item_repository.delete_by_order_id(order_id)
            result = self.order_repository.delete(order_id)
            if not result:
                return False
            return True
        except Exception as e:
            print(f"Error deleting order: {e}")
            return False
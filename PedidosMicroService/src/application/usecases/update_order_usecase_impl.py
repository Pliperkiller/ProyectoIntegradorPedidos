from src.domain.value_objects.order_status import get_order_status_from_str
from src.domain.ports.input.update_order_usecase import UpdateOrderUsecase
from src.domain.ports.output.order_repository import OrderRepository
from src.domain.entities.order import Order
from typing_extensions import override

class UpdateOrderUsecaseImpl(UpdateOrderUsecase):
    def __init__(self, order_repository:OrderRepository):
        self.order_repository = order_repository

    @override
    def update_order(self, order_id : int, order : Order):
        return self.order_repository.update(order_id, order)
    
    @override
    def update_order_status(self, order_id : int, new_status : str):
        order = self.order_repository.get_by_id(order_id)
        order.status = get_order_status_from_str(new_status)
        return self.order_repository.update(order_id, order)
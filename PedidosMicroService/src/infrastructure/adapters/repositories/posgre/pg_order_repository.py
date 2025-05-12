from src.domain.ports.output.order_repository import OrderRepository
from src.domain.entities.order import Order
from src.domain.value_objects.order_status import OrderStatus, get_order_status_from_str
from src.infrastructure.database.models.order_model import OrderModel
from src.infrastructure.database.models.table_base_model import db
from src.infrastructure.database.models.order_item_model import OrderItemModel
from src.infrastructure.adapters.repositories.posgre.pg_order_item_repository import PgOrderItemRepository
from typing_extensions import override


class PgOrderRepository(OrderRepository):
    @override
    def create(self, order):
        order_model = OrderModel(
            client_id=order.client_id,
            status=order.status.value,
            total_value=order.total_value
        )
        
        db.session.add(order_model)
        db.session.commit()
        
        order.id = order_model.id
        return order

    @override
    def get_by_id(self, order_id:int):
        order_model = OrderModel.query.get(order_id)
        if not order_model:
            return None
        
        order_items = PgOrderItemRepository().get_by_order_id(order_id)
        order_items = [item for item in order_items]
        
        order = Order(
            id=order_model.id,
            client_id=order_model.client_id,
            status=order_model.status,
            items=order_items,
            total_value=order_model.total_value
        )
        


        return order

    @override
    def update(self, order_id : int ,order:Order):
        order_model = OrderModel.query.get(order_id)
        if not order_model:
            return None
        
        order_model.status = order.status.value
        order_model.total_value = order.total_value
        
        db.session.commit()
        return order

    @override
    def delete(self, order_id:int):
        order_model = OrderModel.query.get(order_id)
        if order_model:
            db.session.delete(order_model)
            db.session.commit()
            return True
        return False

    @override
    def get_all(self):
        order_models = OrderModel.query.all()
        orders = []
        
        for order_model in order_models:
            order = Order(
                id=order_model.id,
                client_id=order_model.client_id,
                status=order_model.status,
                total_value=order_model.total_value
            )
            orders.append(order)
        
        return orders
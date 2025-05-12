from src.domain.ports.output.order_item_repository import OrderItemRepository
from src.infrastructure.database.models.order_item_model import OrderItemModel
from src.domain.entities.order_item import OrderItem
from src.domain.entities.product import Product
from src.infrastructure.database.models.table_base_model import db
from typing_extensions import override

class PgOrderItemRepository(OrderItemRepository):
    @override
    def create(self, order_id , order_item):
        order_item_model = OrderItemModel(
            order_id = order_id,
            product_id = order_item.product.id,
            amount = order_item.amount,
            subtotal = order_item.subtotal
        )
        
        db.session.add(order_item_model)
        db.session.commit()
        
        order_item.id = order_item_model.id
        return order_item

    @override
    def get_by_id(self, item_id):
        item_model = OrderItemModel.query.get(item_id)
        if not item_model:
            return None
            
        product = Product(
            id=item_model.product.id,
            name=item_model.product.name,
            price=item_model.product.price
        )
        
        order_item = OrderItem(
            id=item_model.id,
            product=product,
            amount=item_model.amount
        )
        
        return order_item

    @override
    def delete_by_order_id(self, order_id):
        items = OrderItemModel.query.filter_by(order_id=order_id).all()
        for item in items:
            db.session.delete(item)
        db.session.commit()
        return True
    
    @override
    def get_by_order_id(self, order_id):
        item_models = OrderItemModel.query.filter_by(order_id=order_id).all()
        order_items = []
        
        for item_model in item_models:
            product = Product(
                id=item_model.product.id,
                name=item_model.product.name,
                price=item_model.product.price
            )
            
            order_item = OrderItem(
                id=item_model.id,
                product=product,
                amount=item_model.amount
            )
            
            order_items.append(order_item)
            
        return order_items
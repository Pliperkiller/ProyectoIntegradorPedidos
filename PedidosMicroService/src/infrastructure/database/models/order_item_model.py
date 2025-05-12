from src.infrastructure.database.models.table_base_model import DbModel, db


class OrderItemModel(DbModel):
    __tablename__ = 'order_items'
    
    id = db.Column(db.Integer, primary_key=True)
    order_id = db.Column(db.Integer, db.ForeignKey('orders.id'), nullable=False)
    product_id = db.Column(db.Integer, db.ForeignKey('products.id'), nullable=False)
    amount = db.Column(db.Integer, nullable=False)
    subtotal = db.Column(db.Float, nullable=False)
    
    product = db.relationship('ProductModel')
    
    def __repr__(self):
        return f"<OrderItem {self.id} - Product: {self.product_id}, Amount: {self.amount}>"
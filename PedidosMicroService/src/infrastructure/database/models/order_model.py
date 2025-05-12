from src.infrastructure.database.models.table_base_model import DbModel, db

class OrderModel(DbModel):
    __tablename__ = 'orders'
    
    id = db.Column(db.Integer, primary_key=True)
    client_id = db.Column(db.Integer, nullable=False)
    status = db.Column(db.String(20), nullable=False)
    total_value = db.Column(db.Float, nullable=False)
    items = db.relationship('OrderItemModel',
                             backref='order', 
                             lazy=True, 
                             cascade="all, delete-orphan")
    
    def __repr__(self):
        return f"<Order {self.id} - Client: {self.client_id}, Status: {self.status}>"
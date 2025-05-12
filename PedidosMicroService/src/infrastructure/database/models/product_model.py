from src.infrastructure.database.models.table_base_model import DbModel, db

class ProductModel(DbModel):
    __tablename__ = 'products'
    __table_args__ = {'extend_existing': True}

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    price = db.Column(db.Float, nullable=False)
    
    def __repr__(self):
        return f"<Product {self.id}: {self.name}>"
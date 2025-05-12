from src.domain.entities.product import Product
from src.domain.ports.output.product_repository import ProductRepository
from src.infrastructure.database.models.product_model import ProductModel
from typing_extensions import override

class PgProductRepository(ProductRepository):
    @override
    def get_by_id(self, product_id):
        product_model = ProductModel.query.get(product_id)
        if product_model:
            return Product(
                id=product_model.id,
                name=product_model.name,
                price=product_model.price
            )
        return None

    @override
    def get_all(self):
        product_models = ProductModel.query.all()
        return [
            Product(
                id=product_model.id,
                name=product_model.name,
                price=product_model.price
            ) for product_model in product_models
        ]
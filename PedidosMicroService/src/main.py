from flask import Flask
from src.config import Config
from src.infrastructure.database.models import *
from src.infrastructure.adapters.repositories.posgre import *
from src.infrastructure.database.models.table_base_model import db
from src.application.services.order_service_impl import OrderServiceImpl
from src.infrastructure.entrypoints.rest.flask.order_endpoints import OrderEndpoints
from src.infrastructure.entrypoints.rest.controllers.order_controller import OrderController


def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)
    
    db.init_app(app)
    
    order_repository = PgOrderRepository()
    product_repository = PgProductRepository()
    order_item_repository = PgOrderItemRepository()

    order_service = OrderServiceImpl(order_repository = order_repository,
                                     product_repository = product_repository, 
                                     order_item_repository = order_item_repository)
    order_controller = OrderController(order_service)
    order_endpoints = OrderEndpoints(order_controller)

    app.register_blueprint(order_endpoints.blueprint, url_prefix='/api/v1')

    with app.app_context():
        db.create_all()
        if app.config['DEBUG']:
            from seed import seed_products
            # Eliminar registros de todas las tablas
            db.drop_all()
            db.create_all()
            # Insertar datos de prueba
            product_seed = seed_products.products
            for product in product_seed:
                db.session.add(product)
                db.session.commit()

        
    return app


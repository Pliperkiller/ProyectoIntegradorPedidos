import threading
import time

from flask import Flask
from src.config import Config
from src.infrastructure.database.models import *
from src.infrastructure.adapters.repositories.posgre import *
from src.infrastructure.database.models.table_base_model import db
from src.application.services.order_service_impl import OrderServiceImpl
from src.infrastructure.entrypoints.rest.flask.order_endpoints import OrderEndpoints
from src.infrastructure.entrypoints.rest.controllers.order_controller import OrderController
from src.infrastructure.adapters.message_broker.rabbitmq_message_broker import RabbitMQMessageBroker
from src.infrastructure.adapters.message_listener.rabbitmq_message_listener import RabbitMQMessageListener
from src.infrastructure.entrypoints.rest.swagger_config import swagger_ui_blueprint, register_swagger_routes


def start_message_listener(message_listener: RabbitMQMessageListener, queue_name: str):
    def run_listener():
        try:
            print(f"Starting message listener for queue: {queue_name}")
            message_listener.listen(queue_name)
        except Exception as e:
            print(f"Error en el hilo del listener: {e}")
    listener_thread = threading.Thread(target=run_listener)
    listener_thread.daemon = True
    listener_thread.start()
    # Dar tiempo para que el hilo se inicie pero no bloquear la aplicación
    time.sleep(1)
    
def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)
    
    db.init_app(app)
    
    order_repository = PgOrderRepository()
    product_repository = PgProductRepository()
    order_item_repository = PgOrderItemRepository()
    message_broker = RabbitMQMessageBroker(app.config['RABBITMQ_URL'])
    

    order_service = OrderServiceImpl(order_repository = order_repository,
                                     product_repository = product_repository, 
                                     order_item_repository = order_item_repository,
                                     message_broker=message_broker)
    
    message_listener = RabbitMQMessageListener(app.config['RABBITMQ_URL'], order_service, app)
    start_message_listener(message_listener, "order_confirmation_queue")

    order_controller = OrderController(order_service)
    order_endpoints = OrderEndpoints(order_controller)

    # Registrar blueprints
    app.register_blueprint(order_endpoints.blueprint, url_prefix='/api/v1')
    app.register_blueprint(swagger_ui_blueprint)
    
    # Registrar rutas de Swagger
    register_swagger_routes(app)

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


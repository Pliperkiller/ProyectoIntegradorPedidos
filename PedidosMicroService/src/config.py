import os
class Config:
    SQLALCHEMY_DATABASE_URI = os.getenv('DATABASE_URL', 'postgresql://postgres:postgres@localhost:5432/orders_db')
    RABBITMQ_URL = os.getenv('RABBITMQ_URL', 'amqp://guest:guest@localhost:5672/')
    RABBIT_PUBLISH_QUEUE = os.getenv('RABBIT_PUBLISH_QUEUE', 'order_items_queue')
    RABBIT_LISTEN_QUEUE = os.getenv('RABBIT_LISTEN_QUEUE', 'order_confirmation_queue')
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    DEBUG = True
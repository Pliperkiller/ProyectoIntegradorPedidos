import pika
import json
from typing import Dict
from src.domain.ports.output.message_broker import MessageBroker

class RabbitMQMessageBroker(MessageBroker):
    def __init__(self, rabbitmq_url: str):
        self.rabbitmq_url = rabbitmq_url

    def publish(self, queue_name: str, message: Dict):
        connection = pika.BlockingConnection(pika.URLParameters(self.rabbitmq_url))
        channel = connection.channel()

        channel.queue_declare(queue=queue_name, durable=True)

        # Publica el mensaje
        channel.basic_publish(
            exchange='',
            routing_key=queue_name,
            body=json.dumps(message),
            properties=pika.BasicProperties(delivery_mode=2)
        )

        connection.close()
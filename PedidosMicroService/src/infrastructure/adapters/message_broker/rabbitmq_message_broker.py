import pika
import json
import time
from typing import Dict
from src.domain.ports.output.message_broker import MessageBroker

class RabbitMQMessageBroker(MessageBroker):
    def __init__(self, rabbitmq_url: str):
        self.rabbitmq_url = rabbitmq_url

    def publish(self, queue_name: str, message: Dict):
        # Intentar conectar con reintentos
        max_retries = 5
        retry_delay = 2
        
        for attempt in range(max_retries):
            try:
                # Configurar parámetros de conexión con timeout
                parameters = pika.URLParameters(self.rabbitmq_url)
                parameters.socket_timeout = 5
                parameters.connection_attempts = 3
                
                connection = pika.BlockingConnection(parameters)
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
                return
            except pika.exceptions.AMQPConnectionError as e:
                if attempt < max_retries - 1:
                    print(f"Error de conexión a RabbitMQ: {e}. Reintentando en {retry_delay} segundos...")
                    time.sleep(retry_delay)
                else:
                    print(f"No se pudo conectar a RabbitMQ después de {max_retries} intentos: {e}")
                    # En un entorno de producción, podrías querer registrar este error
                    # pero permitir que la operación continúe sin RabbitMQ
                    return
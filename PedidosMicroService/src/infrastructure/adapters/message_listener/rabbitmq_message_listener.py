import time
import pika
import json

from src.domain.value_objects.order_status import OrderStatus
from src.domain.ports.input.message_listener import MessageListener
from src.application.services.order_service import OrderService
from src.domain.value_objects.order_status import map_order_status_from_int

class RabbitMQMessageListener(MessageListener):
    def __init__(self, rabbitmq_url: str, order_service: OrderService, app):
        self.rabbitmq_url = rabbitmq_url
        self.order_service = order_service
        self.app = app

    def listen(self, queue_name: str):
        while True:  # Intentar reconectar indefinidamente
            try:
                print(f"Conectando a RabbitMQ con URL: {self.rabbitmq_url}")
                
                # Configurar parámetros de conexión con timeout
                parameters = pika.URLParameters(self.rabbitmq_url)
                parameters.socket_timeout = 5
                parameters.connection_attempts = 3
                
                connection = pika.BlockingConnection(parameters)
                print("Conexión establecida.")
                channel = connection.channel()

                # Asegurarse de que la cola exista
                channel.queue_declare(queue=queue_name, durable=True)
                print(f"Suscrito a la cola: {queue_name}")

                # Callback para procesar mensajes
                def callback(ch, method, properties, body):
                    try:
                        print(f"Mensaje recibido: {body.decode('utf-8')}")
                        message = json.loads(body.decode('utf-8'))

                        # Usar el contexto de Flask para acceder a recursos
                        with self.app.app_context():
                            order_id = message.get("orderId")
                            status = OrderStatus(map_order_status_from_int(message.get("status")))

                            self.order_service.update_order_status(
                                order_id=order_id,
                                new_status=status.value
                            )
                    except Exception as e:
                        print(f"Error al procesar el mensaje: {e}")

                # Configurar el consumidor
                channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)

                print(f"Esperando mensajes en la cola '{queue_name}'...")
                channel.start_consuming()

            except pika.exceptions.AMQPConnectionError as e:
                print(f"Error de conexión con RabbitMQ: {e}. Reintentando en 5 segundos...")
                time.sleep(5)
            except Exception as e:
                print(f"Error inesperado en el listener: {e}. Reintentando en 5 segundos...")
                time.sleep(5)
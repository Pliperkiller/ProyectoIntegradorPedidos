import pika
import json
from src.domain.ports.input.message_listener import MessageListener
from src.application.services.order_service import OrderService
from src.domain.value_objects.order_status import OrderStatus

class RabbitMQMessageListener(MessageListener):
    def __init__(self, rabbitmq_url: str, order_service: OrderService):
        self.rabbitmq_url = rabbitmq_url
        self.order_service = order_service

    def listen(self, queue_name: str):
        # Conexión a RabbitMQ
        connection = pika.BlockingConnection(pika.URLParameters(self.rabbitmq_url))
        channel = connection.channel()

        # Asegurarse de que la cola exista
        channel.queue_declare(queue=queue_name, durable=True)

        # Callback para procesar mensajes
        def callback(ch, method, properties, body):
            try:
                print(f"Mensaje recibido de '{queue_name}': {body}")
                
                # Deserializar el mensaje JSON
                message = json.loads(body.decode('utf-8'))
                
                # Acceder a los campos del mensaje
                order_id = message.get("orderId")
                status = message.get("status")

                self.order_service.update_order_status(
                    order_id=order_id,
                    new_status= OrderStatus.CONFIRMED if status == 1 else OrderStatus.CANCELLED
                )
            except Exception as e:
                print(f"Error al procesar el mensaje: {e}")

        # Configurar el consumidor
        channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)

        print(f"Esperando mensajes en la cola '{queue_name}'...")
        channel.start_consuming()
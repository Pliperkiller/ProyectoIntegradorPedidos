import pika

rabbitmq_url = "amqp://guest:guest@localhost:5672/"
connection = pika.BlockingConnection(pika.URLParameters(rabbitmq_url))
channel = connection.channel()

channel.queue_declare(queue="order_confirmation_queue", durable=True)

def callback(ch, method, properties, body):
    print(f"Mensaje recibido: {body}")

channel.basic_consume(queue="order_confirmation_queue", on_message_callback=callback, auto_ack=True)
print("Esperando mensajes en la cola 'order_confirmation_queue'...")
channel.start_consuming()
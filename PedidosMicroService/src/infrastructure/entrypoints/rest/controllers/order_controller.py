from src.application.services.order_service import OrderService
from typing import List

class OrderController:
    def __init__(self, order_service : OrderService):
        self.order_service = order_service

    def create_order(self, data : dict):
        client_id : int = data.get('client_id')
        items : List[dict] = data.get('items', [])

        if not client_id or not items:
            return {'error': 'Se requiere client_id y e items'}, 400

        order = self.order_service.create_order(client_id, items)
        return order.to_dict(), 201

    def get_order(self, order_id : int):
        order = self.order_service.get_order(order_id)
        if not order:
            return {'error': 'Pedido no encontrado'}, 404
        return order.to_dict(), 200

    def update_order_status(self, order_id : int, data : dict):
        new_status : str = data.get('status')
        if not new_status:
            return {'error': 'Se requiere el estado nuevo'}, 400

        order = self.order_service.update_order_status(order_id, new_status)
        if not order:
            return {'error': 'Pedido no encontrado o estado inválido'}, 404
        return order.to_dict(), 200

    def get_all_orders(self):
        orders = self.order_service.get_all_orders()
        return [order.to_dict() for order in orders], 200

    def delete_order(self, order_id : int):
        success = self.order_service.delete_order(order_id)
        if not success:
            return {'error': 'Pedido no encontrado'}, 404
        return {'message': 'Pedido eliminado exitosamente'}, 200

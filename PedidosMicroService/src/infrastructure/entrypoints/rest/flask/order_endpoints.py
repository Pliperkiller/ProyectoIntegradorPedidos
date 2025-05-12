from flask import Blueprint, request, jsonify
from src.infrastructure.entrypoints.rest.controllers.order_controller import OrderController
from src.infrastructure.database.models.table_base_model import db
from sqlalchemy import text

class OrderEndpoints:
    def __init__(self, order_controller: OrderController):
        self.order_controller = order_controller
        self.blueprint = Blueprint('orders', __name__)
        self.register_routes()

    def register_routes(self):
        @self.blueprint.route('/orders', methods=['POST'])
        def create_order():
            data = request.get_json()
            response, status = self.order_controller.create_order(data)
            return jsonify(response), status

        @self.blueprint.route('/orders/<int:order_id>', methods=['GET'])
        def get_order(order_id):
            response, status = self.order_controller.get_order(order_id)
            return jsonify(response), status

        @self.blueprint.route('/orders/<int:order_id>/status', methods=['PUT'])
        def update_order_status(order_id):
            data = request.get_json()
            response, status = self.order_controller.update_order_status(order_id, data)
            return jsonify(response), status

        @self.blueprint.route('/orders', methods=['GET'])
        def get_all_orders():
            response, status = self.order_controller.get_all_orders()
            return jsonify(response), status

        @self.blueprint.route('/orders/<int:order_id>', methods=['DELETE'])
        def delete_order(order_id):
            response, status = self.order_controller.delete_order(order_id)
            return jsonify(response), status

        # @self.blueprint.route('/health', methods=['GET'])
        # def health_check():
        #     return jsonify({"status": "API is running"}), 200

        # @self.blueprint.route('/health/db', methods=['GET'])
        # def database_health_check():
        #     try:
        #         db.session.execute(text('SELECT 1'))
        #         return jsonify({"status": "Database connection is healthy"}), 200
        #     except Exception as e:
        #         return jsonify({"status": "Database connection failed", "error": str(e)}), 500
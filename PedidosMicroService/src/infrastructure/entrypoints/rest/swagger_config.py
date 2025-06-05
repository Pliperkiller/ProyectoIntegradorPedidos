from flask_swagger_ui import get_swaggerui_blueprint
from flask import jsonify

SWAGGER_URL = '/api/docs'
API_URL = '/api/swagger.json'

swagger_ui_blueprint = get_swaggerui_blueprint(
    SWAGGER_URL,
    API_URL,
    config={
        'app_name': "Pedidos Microservice API"
    }
)

def get_swagger_config():
    return {
        "swagger": "2.0",
        "info": {
            "title": "Pedidos Microservice API",
            "description": "API para la gestión de pedidos",
            "version": "1.0.0"
        },
        "basePath": "/api/v1",
        "schemes": [
            "http",
            "https"
        ],
        "consumes": [
            "application/json"
        ],
        "produces": [
            "application/json"
        ],
        "tags": [
            {
                "name": "orders",
                "description": "Operaciones relacionadas con pedidos"
            }
        ],
        "paths": {
            "/orders": {
                "get": {
                    "tags": ["orders"],
                    "summary": "Obtener todos los pedidos",
                    "description": "Retorna una lista de todos los pedidos",
                    "produces": ["application/json"],
                    "responses": {
                        "200": {
                            "description": "Lista de pedidos obtenida exitosamente",
                            "schema": {
                                "type": "array",
                                "items": {
                                    "$ref": "#/definitions/Order"
                                }
                            }
                        }
                    }
                },
                "post": {
                    "tags": ["orders"],
                    "summary": "Crear un nuevo pedido",
                    "description": "Crea un nuevo pedido con los items especificados",
                    "consumes": ["application/json"],
                    "produces": ["application/json"],
                    "parameters": [
                        {
                            "in": "body",
                            "name": "body",
                            "description": "Objeto de pedido que necesita ser creado",
                            "required": True,
                            "schema": {
                                "$ref": "#/definitions/OrderRequest"
                            }
                        }
                    ],
                    "responses": {
                        "201": {
                            "description": "Pedido creado exitosamente",
                            "schema": {
                                "$ref": "#/definitions/Order"
                            }
                        },
                        "400": {
                            "description": "Datos de pedido inválidos",
                            "schema": {
                                "$ref": "#/definitions/Error"
                            }
                        }
                    }
                }
            },
            "/orders/{orderId}": {
                "get": {
                    "tags": ["orders"],
                    "summary": "Encontrar pedido por ID",
                    "description": "Retorna un solo pedido",
                    "produces": ["application/json"],
                    "parameters": [
                        {
                            "name": "orderId",
                            "in": "path",
                            "description": "ID del pedido a retornar",
                            "required": True,
                            "type": "integer",
                            "format": "int64"
                        }
                    ],
                    "responses": {
                        "200": {
                            "description": "Operación exitosa",
                            "schema": {
                                "$ref": "#/definitions/Order"
                            }
                        },
                        "404": {
                            "description": "Pedido no encontrado",
                            "schema": {
                                "$ref": "#/definitions/Error"
                            }
                        }
                    }
                },
                "delete": {
                    "tags": ["orders"],
                    "summary": "Eliminar un pedido",
                    "description": "Elimina un pedido existente",
                    "produces": ["application/json"],
                    "parameters": [
                        {
                            "name": "orderId",
                            "in": "path",
                            "description": "ID del pedido a eliminar",
                            "required": True,
                            "type": "integer",
                            "format": "int64"
                        }
                    ],
                    "responses": {
                        "200": {
                            "description": "Pedido eliminado exitosamente",
                            "schema": {
                                "$ref": "#/definitions/SuccessMessage"
                            }
                        },
                        "404": {
                            "description": "Pedido no encontrado",
                            "schema": {
                                "$ref": "#/definitions/Error"
                            }
                        }
                    }
                }
            },
            "/orders/{orderId}/status": {
                "put": {
                    "tags": ["orders"],
                    "summary": "Actualizar el estado de un pedido",
                    "description": "Actualiza el estado de un pedido existente",
                    "consumes": ["application/json"],
                    "produces": ["application/json"],
                    "parameters": [
                        {
                            "name": "orderId",
                            "in": "path",
                            "description": "ID del pedido a actualizar",
                            "required": True,
                            "type": "integer",
                            "format": "int64"
                        },
                        {
                            "in": "body",
                            "name": "body",
                            "description": "Objeto con el nuevo estado del pedido",
                            "required": True,
                            "schema": {
                                "$ref": "#/definitions/OrderStatusUpdate"
                            }
                        }
                    ],
                    "responses": {
                        "200": {
                            "description": "Estado del pedido actualizado exitosamente",
                            "schema": {
                                "$ref": "#/definitions/Order"
                            }
                        },
                        "400": {
                            "description": "Estado inválido",
                            "schema": {
                                "$ref": "#/definitions/Error"
                            }
                        },
                        "404": {
                            "description": "Pedido no encontrado",
                            "schema": {
                                "$ref": "#/definitions/Error"
                            }
                        }
                    }
                }
            }
        },
        "definitions": {
            "Product": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "name": {
                        "type": "string"
                    },
                    "price": {
                        "type": "number",
                        "format": "float"
                    }
                }
            },
            "OrderItem": {
                "type": "object",
                "properties": {
                    "product": {
                        "$ref": "#/definitions/Product"
                    },
                    "amount": {
                        "type": "integer",
                        "format": "int32"
                    },
                    "subtotal": {
                        "type": "number",
                        "format": "float"
                    }
                }
            },
            "Order": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "client_id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "status": {
                        "type": "string",
                        "enum": ["PENDING", "PROCESSING", "CONFIRMED", "COMPLETED", "CANCELLED"]
                    },
                    "items": {
                        "type": "array",
                        "items": {
                            "$ref": "#/definitions/OrderItem"
                        }
                    },
                    "total_value": {
                        "type": "number",
                        "format": "float"
                    }
                }
            },
            "OrderRequest": {
                "type": "object",
                "required": ["client_id", "items"],
                "properties": {
                    "client_id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "items": {
                        "type": "array",
                        "items": {
                            "type": "object",
                            "properties": {
                                "product_id": {
                                    "type": "integer",
                                    "format": "int64"
                                },
                                "amount": {
                                    "type": "integer",
                                    "format": "int32",
                                    "minimum": 1
                                }
                            }
                        }
                    }
                }
            },
            "OrderStatusUpdate": {
                "type": "object",
                "required": ["status"],
                "properties": {
                    "status": {
                        "type": "string",
                        "enum": ["PENDING", "PROCESSING", "CONFIRMED", "COMPLETED", "CANCELLED"]
                    }
                }
            },
            "Error": {
                "type": "object",
                "properties": {
                    "error": {
                        "type": "string"
                    }
                }
            },
            "SuccessMessage": {
                "type": "object",
                "properties": {
                    "message": {
                        "type": "string"
                    }
                }
            }
        }
    }

def register_swagger_routes(app):
    @app.route('/api/swagger.json')
    def swagger_json():
        return jsonify(get_swagger_config())
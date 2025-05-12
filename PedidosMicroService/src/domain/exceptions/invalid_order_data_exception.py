# Crea excepcion para cuando se intenta crear una orden con datos invalidos
class InvalidOrderDataException(Exception):
    def __init__(self, message="Invalid order data provided."):
        self.message = message
        super().__init__(self.message)

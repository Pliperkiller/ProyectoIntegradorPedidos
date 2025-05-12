from src.domain.entities.product import Product

class OrderItem:
    def __init__(self, 
                 id : int = None,
                 product : Product = None, 
                 amount : int = 0):
        self.id = id
        self.product = product
        self.amount = amount
        self.subtotal = self.calculate_subtotal()

    def calculate_subtotal(self):
        if self.product and self.amount:
            return self.product.price * self.amount
        return 0

    def to_dict(self):
        return {
            "product": self.product.to_dict() if self.product else None,
            "amount": self.amount,
            "subtotal": self.subtotal
        }
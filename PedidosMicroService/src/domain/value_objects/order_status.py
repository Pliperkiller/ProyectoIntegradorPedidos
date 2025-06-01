from enum import Enum

class OrderStatus(Enum):

    PENDING = "PENDING"
    PROCESSING = "PROCESSING"
    CONFIRMED = "CONFIRMED"
    COMPLETED = "COMPLETED"
    CANCELLED = "CANCELLED"

def get_order_status_from_str(status_str: str) -> OrderStatus:
    try:
        return OrderStatus[status_str.upper()]
    except KeyError:
        raise ValueError(f"Invalid OrderStatus: {status_str}")
    
def map_order_status_from_int(status: int) -> OrderStatus:
    status_mapping = {
        4: OrderStatus.PENDING,
        2: OrderStatus.PROCESSING,
        3: OrderStatus.COMPLETED,
        1: OrderStatus.CONFIRMED,
        0: OrderStatus.CANCELLED
    }
    
    return status_mapping.get(status, OrderStatus.PENDING)
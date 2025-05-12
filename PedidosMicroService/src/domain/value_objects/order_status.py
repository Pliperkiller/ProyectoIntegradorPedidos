from enum import Enum

class OrderStatus(Enum):

    PENDING = "PENDING"
    PROCESSING = "PROCESSING"
    COMPLETED = "COMPLETED"
    CANCELLED = "CANCELLED"

def get_order_status_from_str(status_str: str) -> OrderStatus:
    try:
        return OrderStatus[status_str.upper()]
    except KeyError:
        raise ValueError(f"Invalid OrderStatus: {status_str}")

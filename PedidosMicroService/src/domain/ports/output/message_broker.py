from abc import ABC, abstractmethod
from typing import Dict

class MessageBroker(ABC):
    @abstractmethod
    def publish(self, queue_name: str, message: Dict):
        """Publica un mensaje en la cola especificada."""
        pass
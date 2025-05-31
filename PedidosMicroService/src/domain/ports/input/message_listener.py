from abc import ABC, abstractmethod
from typing import Dict

class MessageListener(ABC):
    @abstractmethod
    def listen(self, queue_name: str):
        """Recibe un mensaje en la cola especificada."""
        pass
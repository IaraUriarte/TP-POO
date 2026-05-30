class Turno:
    
    def __init__(self, nombre_cliente, hora):
        self.__nombre_cliente = nombre_cliente
        self.__hora = hora 

    def get_nombre_cliente(self):
        return self.__nombre_cliente
    
    def get_hora(self):
        return self.__hora

    def __str__(self):
        return f"Cliente: {self.__nombre_cliente} | Hora: {self.__hora}:00 hs"
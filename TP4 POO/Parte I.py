# CÓDIGO GENERADO POR IA
class Motor:
    def __init__(self, potencia):
        self.potencia = potencia

    def encender(self):
        print("Motor encendido")


class Auto(Motor):
    def __init__(self, marca, potencia):
        super().__init__(potencia)
        self.marca = marca

    def mostrar_datos(self):
        print(f"Marca: {self.marca}")
        print(f"Potencia: {self.potencia} HP")


# Programa principal
auto = Auto("Toyota", 150)

auto.encender()
auto.mostrar_datos()

# Alternativa
class Identificador:
    def __init__(self, id):
        self.id = id

class Cliente:
    def __init__(self, id, nombre):
        self.identificador = Identificador(id)
        self.nombre = nombre

class Producto:
    def __init__(self, id, descripcion):
        self.identificador = Identificador(id)
        self.descripcion = descripcion

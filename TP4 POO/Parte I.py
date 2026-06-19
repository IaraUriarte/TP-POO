# CÓDIGO GENERADO POR IA
class Motor:
    def __init__(self, cilindros: int, combustible: str, caballos_fuerza: int):
        self.cilindros = cilindros
        self.combustible = combustible
        self.caballos_fuerza = caballos_fuerza
        self.encendido = False

    def encender(self):
        self.encendido = True
        print(f"Motor encendido ({self.caballos_fuerza} HP, {self.cilindros} cilindros)")

    def apagar(self):
        self.encendido = False
        print("Motor apagado.")

    def estado_motor(self):
        estado = "encendido" if self.encendido else "apagado"
        print(f"Motor {estado} | Combustible: {self.combustible} | "
              f"Cilindros: {self.cilindros} | HP: {self.caballos_fuerza}")


# Clase derivada
class Auto(Motor):
    def __init__(self, marca: str, modelo: str, año: int, color: str,
                 cilindros: int, combustible: str, caballos_fuerza: int):
        super().__init__(cilindros, combustible, caballos_fuerza)  # Hereda Motor
        self.marca = marca
        self.modelo = modelo
        self.año = año
        self.color = color
        self.velocidad = 0

    def acelerar(self, incremento: int):
        if not self.encendido:
            print("No puedes acelerar, el motor está apagado.")
            return
        self.velocidad += incremento
        print(f"Acelerando... Velocidad actual: {self.velocidad} km/h")

    def frenar(self, decremento: int):
        self.velocidad = max(0, self.velocidad - decremento)
        print(f"Frenando... Velocidad actual: {self.velocidad} km/h")

    def descripcion(self):
        print(f"{self.año} {self.marca} {self.modelo} ({self.color})")
        self.estado_motor()  # Método heredado de Motor


# --- Uso ---
mi_auto = Auto(
    marca="Toyota", modelo="Corolla", año=2023, color="Rojo",
    cilindros=4, combustible="Gasolina", caballos_fuerza=132
)

mi_auto.descripcion()
mi_auto.encender()       # Método heredado
mi_auto.acelerar(60)
mi_auto.acelerar(40)
mi_auto.frenar(30)
mi_auto.apagar()         # Método heredado
mi_auto.acelerar(10)     # Intento fallido

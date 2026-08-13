from abc import ABC, abstractmethod

class ImpactoEcologico(ABC):

    @abstractmethod
    def obtener_impacto_ecologico(self):
        pass

class Edificio(ImpactoEcologico):

    def __init__(self, nombre, consumo_electrico, factor_emision):
        self.nombre = nombre
        self.consumo_electrico = consumo_electrico
        self.factor_emision = factor_emision

    def obtener_impacto_ecologico(self):
        return self.consumo_electrico * self.factor_emision

class Auto(ImpactoEcologico):

    def __init__(self, modelo, combustible_consumido):
        self.modelo = modelo
        self.combustible_consumido = combustible_consumido

    def obtener_impacto_ecologico(self):
        return self.combustible_consumido * 2.31

class Bicicleta(ImpactoEcologico):

    def __init__(self, marca):
        self.marca = marca

    def obtener_impacto_ecologico(self):
        return 0

edificio = Edificio("Facultad", 1500, 0.4)
auto = Auto("Toyota Corolla", 50)
bicicleta = Bicicleta("Venzo")

objetos_impacto = [
    edificio,
    auto,
    bicicleta
]

for objeto in objetos_impacto:

    print("Tipo:", type(objeto).__name__)

    print(
        "Impacto ecológico:",
        objeto.obtener_impacto_ecologico(),
        "kg de CO2"
    )

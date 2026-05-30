class Continente:
    def __init__(self, nombre):
        self.__nombre = nombre
        self.__paises = []

    def get_nombre(self):
        return self.__nombre

    def getNombre(self):
        return self.get_nombre()

    def get_paises(self):
        return self.__paises

    def getPaises(self):
        return self.get_paises()

    def agregar_pais(self, pais):
        for pais_actual in self.__paises:
            if pais_actual.get_nombre().lower() == pais.get_nombre().lower():
                return False
        self.__paises.append(pais)
        pais.set_continente(self)
        return True

    def agregarPais(self, pais):
        return self.agregar_pais(pais)

    def __str__(self) -> str:
        return f"Continente: {self.__nombre} (Cantidad de Países: {len(self.__paises)})"

    def toString(self) -> str:
        return self.__str__()
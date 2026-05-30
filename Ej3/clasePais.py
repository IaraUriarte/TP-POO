class Pais:
    def __init__(self, nombre, capital, superficie):
        self.__nombre = nombre
        self.__capital = capital
        self.__superficie = superficie
        self.__provincias = []
        self.__Paiseslimitrofes = []
        self.__continente = None

    def get_nombre(self) -> str:
        return self.__nombre

    def getNombre(self) -> str:
        return self.get_nombre()

    def get_capital(self) -> str:
        return self.__capital

    def getCapital(self) -> str:
        return self.get_capital()

    def get_superficie(self) -> float:
        return self.__superficie

    def getSuperficie(self) -> float:
        return self.get_superficie()

    def get_continente(self):
        return self.__continente

    def getContinente(self):
        return self.get_continente()

    def set_continente(self, continente):
        self.__continente = continente

    def setContinente(self, continente):
        self.set_continente(continente)

    def get_provincias(self) -> list:
        return self.__provincias

    def getProvincias(self) -> list:
        return self.get_provincias()

    def get_limitrofes(self) -> list:
        return self.__Paiseslimitrofes

    def getLimitrofes(self) -> list:
        return self.get_limitrofes()

    def agregar_provincia(self, provincia) -> bool:
        for p in self.__provincias:
            if p.get_nombre().lower() == provincia.get_nombre().lower():
                return False
        self.__provincias.append(provincia)
        return True

    def agregarProvincia(self, provincia) -> bool:
        return self.agregar_provincia(provincia)

    def agregar_limitrofe(self, pais) -> bool:
        if pais == self:
            return False
        if pais not in self.__Paiseslimitrofes:
            self.__Paiseslimitrofes.append(pais)
            pais.agregar_limitrofe(self)
            return True
        return False

    def agregarLimitrofe(self, pais) -> bool:
        return self.agregar_limitrofe(pais)

    def __str__(self) -> str:
        continente_nombre = self.__continente.get_nombre() if self.__continente else "Sin Continente"
        return f"País: {self.__nombre} | Capital: {self.__capital} | Superficie: {self.__superficie} km² | Continente: {continente_nombre}"

    def toString(self) -> str:
        return self.__str__()

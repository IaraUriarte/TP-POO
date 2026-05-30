class Provincia:
    def __init__(self, nombre):
        self.__nombre = nombre

    def get_nombre(self):
        return self.__nombre

    def set_nombre(self, nombre):
        self.__nombre = nombre

    def __str__(self) -> str:
        return f"Provincia: {self.__nombre}"

    def toString(self) -> str:
        return self.__str__()


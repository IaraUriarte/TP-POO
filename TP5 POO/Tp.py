import math
from abc import ABC, abstractmethod

class Punto:
    """
    Representa un punto en un plano cartesiano de dos dimensiones (X, Y).
    """
    def __init__(self, x: float, y: float):
        self._x = x
        self._y = y

    def getX(self) -> float:
        return self._x

    def setX(self, value: float):
        self._x = value

    def getY(self) -> float:
        return self._y

    def setY(self, value: float):
        self._y = value

    def __str__(self) -> str:
        return f"({self.getX()}, {self.getY()})"

    def __repr__(self) -> str:
        return self.__str__()


class ElementoGrafico(ABC):
    """
    Superclase abstracta que representa cualquier objeto visual en la pantalla.
    """
    def __init__(self, color_hex: str, posicion_centro: Punto, nombre_capa: str):
        self._color_hex = color_hex
        self._posicion_centro = posicion_centro
        self._nombre_capa = nombre_capa

    def getColorHex(self) -> str:
        return self._color_hex

    def setColorHex(self, color: str):
        self._color_hex = color

    def getPosicionCentro(self) -> Punto:
        return self._posicion_centro

    def setPosicionCentro(self, punto: Punto):
        self._posicion_centro = punto

    def getNombreCapa(self) -> str:
        return self._nombre_capa

    def setNombreCapa(self, nombre: str):
        self._nombre_capa = nombre

    def moverA(self, nuevo_destino: Punto):
        """
        Actualiza las coordenadas del centro del elemento gráfico.
        """
        self.setPosicionCentro(nuevo_destino)

    @abstractmethod
    def calcularArea(self) -> float:
        """
        Calcula el área del elemento gráfico. Debe ser implementado por subclases.
        """
        pass

    @abstractmethod
    def calcularPerimetro(self) -> float:
        """
        Calcula el perímetro del elemento gráfico. Debe ser implementado por subclases.
        """
        pass

    def toString(self) -> str:
        """
        Devuelve un resumen descriptivo del elemento gráfico.
        """
        return f"Capa:  '{self.getNombreCapa()}'\nColor: {self.getColorHex()}\nCentro: {self.getPosicionCentro()}"

    def __str__(self) -> str:
        return self.toString()


class Rectangulo(ElementoGrafico):
    def __init__(self, color_hex: str, posicion_centro: Punto, nombre_capa: str, lado_menor: float, lado_mayor: float):
        super().__init__(color_hex, posicion_centro, nombre_capa)
        if lado_menor <= 0 or lado_mayor <= 0:
            raise ValueError("Las dimensiones del rectángulo deben ser mayores que cero.")
        self._lado_menor = lado_menor
        self._lado_mayor = lado_mayor

    def getLadoMenor(self) -> float:
        return self._lado_menor

    def setLadoMenor(self, value: float):
        if value <= 0:
            raise ValueError("El lado menor debe ser mayor que cero.")
        self._lado_menor = value

    def getLadoMayor(self) -> float:
        return self._lado_mayor

    def setLadoMayor(self, value: float):
        if value <= 0:
            raise ValueError("El lado mayor debe ser mayor que cero.")
        self._lado_mayor = value

    def calcularArea(self) -> float:
        return self.getLadoMenor() * self.getLadoMayor()

    def calcularPerimetro(self) -> float:
        return 2 * (self.getLadoMenor() + self.getLadoMayor())

    def escalar(self, factor: float):
        if factor <= 0:
            raise ValueError(f"El factor de escala debe ser estrictamente mayor que cero. Recibido: {factor}")
        self.setLadoMenor(self.getLadoMenor() * factor)
        self.setLadoMayor(self.getLadoMayor() * factor)

    def toString(self) -> str:
        return f"{super().toString()}\nTipo: Rectángulo (Lados: {self.getLadoMenor()} x {self.getLadoMayor()})"


class Elipse(ElementoGrafico):
    def __init__(self, color_hex: str, posicion_centro: Punto, nombre_capa: str, radio_mayor: float, radio_menor: float):
        super().__init__(color_hex, posicion_centro, nombre_capa)
        if radio_mayor <= 0 or radio_menor <= 0:
            raise ValueError("Los radios de la elipse deben ser mayores que cero.")
        self._radio_mayor = radio_mayor
        self._radio_menor = radio_menor

    def getRadioMayor(self) -> float:
        return self._radio_mayor

    def setRadioMayor(self, value: float):
        if value <= 0:
            raise ValueError("El radio mayor debe ser mayor que cero.")
        self._radio_mayor = value

    def getRadioMenor(self) -> float:
        return self._radio_menor

    def setRadioMenor(self, value: float):
        if value <= 0:
            raise ValueError("El radio menor debe ser mayor que cero.")
        self._radio_menor = value

    def calcularArea(self) -> float:
        return math.pi * self.getRadioMayor() * self.getRadioMenor()

    def calcularPerimetro(self) -> float:
        a = self.getRadioMayor()
        b = self.getRadioMenor()
        return math.pi * (3 * (a + b) - math.sqrt((3 * a + b) * (a + 3 * b)))

    def escalar(self, factor: float):
        # Si el factor es menor o igual a cero, la figura perdería sentido geometrico, por eso no se permite escalar con esos valores.
        if factor <= 0:
            raise ValueError(f"El factor de escala debe ser estrictamente mayor que cero. Recibido: {factor}")
        self.setRadioMayor(self.getRadioMayor() * factor)
        self.setRadioMenor(self.getRadioMenor() * factor)

    def toString(self) -> str:
        return f"{super().toString()}\nTipo: Elipse (Radios: {self.getRadioMayor()} R, {self.getRadioMenor()} r)"


class Cuadrado(Rectangulo):
    def __init__(self, color_hex: str, posicion_centro: Punto, nombre_capa: str, lado: float):
        super().__init__(color_hex, posicion_centro, nombre_capa, lado, lado)

    def setLadoMenor(self, value: float):

        if value <= 0:
            raise ValueError("El lado del cuadrado debe ser mayor que cero.")
        self._lado_menor = value
        self._lado_mayor = value

    def setLadoMayor(self, value: float):
        if value <= 0:
            raise ValueError("El lado del cuadrado debe ser mayor que cero.")
        self._lado_menor = value
        self._lado_mayor = value

    def toString(self) -> str:
        return f"{ElementoGrafico.toString(self)}\nTipo: Cuadrado (Lado: {self.getLadoMenor()})"


class Circulo(Elipse):
    def __init__(self, color_hex: str, posicion_centro: Punto, nombre_capa: str, radio: float):
        super().__init__(color_hex, posicion_centro, nombre_capa, radio, radio)

    def setRadioMayor(self, value: float):
        if value <= 0:
            raise ValueError("El radio del círculo debe ser mayor que cero.")
        self._radio_mayor = value
        self._radio_menor = value

    def setRadioMenor(self, value: float):
        if value <= 0:
            raise ValueError("El radio del círculo debe ser mayor que cero.")
        self._radio_mayor = value
        self._radio_menor = value

    def toString(self) -> str:
        return f"{ElementoGrafico.toString(self)}\nTipo: Círculo (Radio: {self.getRadioMayor()})"


class Lienzo:
    def __init__(self):
        self._elementos = []

    def agregarElemento(self, elemento: ElementoGrafico):
        self._elementos.append(elemento)

    def getElementos(self) -> list:
        return self._elementos

    def mostrarElementos(self):
        for idx, elem in enumerate(self._elementos, start=1):
            print(f"{idx}. {elem}")


def main():
    print("Inicialización del lienzo")
    lienzo = Lienzo()

    # Instanciamos figuras
    rect = Rectangulo("#FF0000", Punto(5, 5), "Capa Rectángulo", 4.0, 6.0)
    elip = Elipse("#00FF00", Punto(10, 10), "Capa Elipse", 5.0, 3.0)
    cuad = Cuadrado("#0000FF", Punto(-2, 3), "Capa Cuadrado", 4.0)
    circ = Circulo("#FFFF00", Punto(0, -5), "Capa Círculo", 3.0)

    # Agregamos elementos al lienzo
    lienzo.agregarElemento(rect)
    lienzo.agregarElemento(elip)
    lienzo.agregarElemento(cuad)
    lienzo.agregarElemento(circ)

    print()
    print("Elementos iniciales en el lienzo:")
    lienzo.mostrarElementos()

    # Demostración de escalado
    print()
    print("Escalando elementos")
    print("Escalando Rectángulo por 2.0 y Círculo por 1.5...")
    rect.escalar(2.0)
    circ.escalar(1.5)
    lienzo.mostrarElementos()

    # Demostración del comportamiento de Cuadrado/Círculo ante mutaciones
    print()
    print("Comporbación de integridad en cuadrado y círculo")
    print(f"Cuadrado original: LadoMenor = {cuad.getLadoMenor()}, LadoMayor = {cuad.getLadoMayor()}")
    print("Modificando cuad.setLadoMayor(10.0)...")
    cuad.setLadoMayor(10.0)
    print(f"Cuadrado modificado: LadoMenor = {cuad.getLadoMenor()}, LadoMayor = {cuad.getLadoMayor()} (Integridad mantenida: {cuad.getLadoMenor() == cuad.getLadoMayor()})")

    print()
    print(f"Círculo original: RadioMayor = {circ.getRadioMayor()}, RadioMenor = {circ.getRadioMenor()}")
    print("Modificando circ.setRadioMenor(8.0)...")
    circ.setRadioMenor(8.0)
    print(f"Círculo modificado: RadioMayor = {circ.getRadioMayor()}, RadioMenor = {circ.getRadioMenor()} (Integridad mantenida: {circ.getRadioMayor() == circ.getRadioMenor()})")

    print()
    print("Ejecución del bucle del motor")
    area_total = 0.0
    for elemento in lienzo.getElementos():
        elemento.setColorHex("#808080")
        elemento.moverA(Punto(0, 0))
        area_total += elemento.calcularArea()

    print()
    print("Elementos en el lienzo después del procesamiento del motor:")
    lienzo.mostrarElementos()
    print()
    print(f"Área total acumulada por todos los elementos: {area_total:.4f} unidades cuadradas")

if __name__ == "__main__":
    main()

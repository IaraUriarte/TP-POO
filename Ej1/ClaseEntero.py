class Entero:
    def __init__(self, numero: int):
        self.__numero = numero

    def get_numero(self) -> int:
        return self.__numero

    def set_numero(self, numero: int):
        self.__numero = numero

    def cuadrado(self) -> int:
        return self.__numero * self.__numero
	
    def esPar(self) -> bool:
        return self.__numero % 2 == 0

    def esImpar(self) -> bool:
       return self.__numero % 2 != 0

    def factorial(self) -> int:
        if self.__numero < 0:	
            raise ValueError("No existe facortial para números negativos")
        resultado = 1
        for i in range(1, self.__numero + 1):   
            resultado *= i
        return resultado 

    def esPrimo(self) -> bool:
        if self.__numero < 2:
            return False 
        for i in range(2, int(self.__numero ** 0.5) + 1):
            if self.__numero % i == 0:
                return False 
        return True 

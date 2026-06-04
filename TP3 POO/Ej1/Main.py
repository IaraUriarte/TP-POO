from ClaseEntero import Entero


def main():
	Num1 = Entero(6)
	Num2 = Entero(4)

	print('Resultado: clase Entero')
	print(f'Número inicial (Num1): {Num1.get_numero()}')

	Num1.set_numero(5)
	print(f'Número modificado (Num1): {Num1.get_numero()}')

	print(f'Cuadrado de {Num1.get_numero()}: {Num1.cuadrado()}')
	print(f'¿{Num1.get_numero()} es par? {'Sí' if Num1.esPar() else 'No'}')
	print(f'¿{Num1.get_numero()} es impar? {'Sí' if Num1.esImpar() else 'No'}')

	print('\nOperaciones sobre Num2')
	print(f'Número (Num2): {Num2.get_numero()}')
	print(f'Factorial de {Num2.get_numero()}: {Num2.factorial()}')
	print(f'¿{Num2.get_numero()} es primo? {'Sí' if Num2.esPrimo() else 'No'}')


if __name__ == '__main__':
	main()
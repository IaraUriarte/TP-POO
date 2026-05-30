from ClaseEntero import Entero  # Aca importe la clase

Num1 = Entero(6)
Num2 = Entero(4)

# Get/Set
print(Num1.get_numero())       
Num1.set_numero(5)
print(Num1.get_numero())       

# cuadrado
print(Num1.cuadrado())         

# Par/Impar
print(Num1.esPar())             
print(Num1.esImpar())          

# Factorial
print(Num2.factorial())        

# Primo
print(Num2.esPrimo())          
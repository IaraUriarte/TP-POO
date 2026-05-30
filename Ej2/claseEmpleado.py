class Empleado:
    salario_min_vital_movil = 363000.0
    def __init__(self, nombre, dni, sueldo):
        self.__nombre = nombre 
        self.__dni = dni
        self.__sueldo = self.__validar_sueldo (sueldo)
    
    def __validar_sueldo(self, sueldo):
        if sueldo < Empleado.salario_min_vital_movil:
            return Empleado.salario_min_vital_movil
        return sueldo    

    def get_nombre(self):
        return self.__nombre
    
    def get_dni(self):
        return self.__dni
    
    def get_sueldo(self):
        return self.__sueldo       
        
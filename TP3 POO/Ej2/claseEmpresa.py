class Empresa:
    def __init__(self):
        self.__empleados = []

    def registrar_empleado(self, empleado):
        for emp in self.__empleados:
            if emp.get_dni() == empleado.get_dni():
                return False  

        self.__empleados.append(empleado)
        return True

    def obtener_empleado_mas_gana(self):
        if not self.__empleados:
            return None
        
        empleado_max = self.__empleados[0]
        for emp in self.__empleados:
            if emp.get_sueldo() > empleado_max.get_sueldo():
                empleado_max = emp
                
        return empleado_max

    def obtener_sueldo_promedio(self):
        if not self.__empleados:
            return 0.0
            
        suma_sueldos = 0.0
        for emp in self.__empleados:
            suma_sueldos += emp.get_sueldo()
            
        return float(suma_sueldos / len(self.__empleados))
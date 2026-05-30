from claseEmpleado import Empleado
from claseEmpresa import Empresa

class Main:
    def __init__(self):
        self.__empresa = Empresa()

    def ejecutar(self):
        opcion = ""
        while opcion != "4":
            self.__mostrar_menu()
            opcion = input("Seleccione una opción: ")

            if opcion == "1":
                self.__interfaz_registrar_empleado()
            elif opcion == "2":
                self.__interfaz_empleado_mas_gana()
            elif opcion == "3":
                self.__interfaz_sueldo_promedio()
            elif opcion == "4":
                print()
                print("Saliendo del sistema...")
            else:
                print("Opción no válida. Por favor, intente nuevamente.")

    def __mostrar_menu(self):
        print("="*20)
        print("GESTIÓN DE EMPLEADOS")
        print("="*20)
        print("1. Registrar nuevo empleado")
        print("2. Ver empleado que más gana")
        print("3. Ver sueldo promedio")
        print("4. Salir")
        print("="*24)

    def __interfaz_registrar_empleado(self):
        print("REGISTRAR EMPLEADO")
        print()
        nombre = input("Ingrese el nombre del empleado: ")
        dni = input("Ingrese el DNI del empleado: ")
        sueldo_str = input("Ingrese el sueldo del empleado: ")

        if sueldo_str.replace('.', '', 1).isdigit() or (sueldo_str.startswith('-') and sueldo_str[1:].replace('.', '', 1).isdigit()):
            sueldo = float(sueldo_str)
        else:
            print("El sueldo ingresado no es numérico. Se aplicará validación por defecto del sistema.")
            sueldo = 0.0

        nuevo_empleado = Empleado(nombre, dni, sueldo)
        exito = self.__empresa.registrar_empleado(nuevo_empleado)
        
        if exito:
            print("Empleado registrado exitosamente")
            print(f"Sueldo final asignado: ${nuevo_empleado.get_sueldo():.2f}")
            print()
        else:
            print("Ya existe un empleado con el DNI", dni)
            print()

    def __interfaz_empleado_mas_gana(self):
        print("EMPLEADO QUE MÁS GANA")
        empleado_max = self.__empresa.obtener_empleado_mas_gana()
        print()
        if empleado_max is not None:
            print(f"Nombre: {empleado_max.get_nombre()}")
            print(f"DNI: {empleado_max.get_dni()}")
            print(f"Sueldo: ${empleado_max.get_sueldo():.2f}")
            print()
        else:
            print("Aún no hay empleados registrados en la empresa.")
            print()
    def __interfaz_sueldo_promedio(self):
        print("SUELDO PROMEDIO")
        print()
        promedio = self.__empresa.obtener_sueldo_promedio()
        print(f"El sueldo promedio de la empresa es: ${promedio:.2f}")
        print()


# BLOQUE PRINCIPAL
if __name__ == "__main__":
    app = Main()
    app.ejecutar()

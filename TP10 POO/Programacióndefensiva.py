
# TP10 - Programacion Defensiva

# PARTE A: Excepciones Personalizadas

class StringException(Exception):
    pass

class EnteroPositivoException(Exception):
    pass

class CollectionVaciaException(Exception):
    pass

class NoEncontradoException(Exception):
    pass

# PARTE B: Clase Empleado (Fail-Fast)

class Empleado:
    def __init__(self, nombre: str, sueldo: int):
        self.legajo = None
        self.set_nombre(nombre)
        self.set_sueldo(sueldo)

    def set_nombre(self, nombre: str):
        if not isinstance(nombre, str) or len(nombre.strip()) < 3:
            raise StringException(
                "El nombre del empleado debe tener 3 o mas caracteres."
            )
        self.nombre = nombre.strip()

    def set_sueldo(self, sueldo: int):
        if sueldo is None or type(sueldo) is not int or sueldo <= 0:
            raise EnteroPositivoException(
                "El sueldo del empleado debe ser mayor a 0."
            )
        self.sueldo = sueldo

    def set_legajo(self, legajo: int):
        if legajo is None or type(legajo) is not int or legajo <= 0:
            raise EnteroPositivoException(
                "El legajo del empleado debe ser mayor a 0."
            )
        self.legajo = legajo

    def __repr__(self):
        return (
            f"Empleado(legajo={self.legajo}, "
            f"nombre=`{self.nombre}`, sueldo={self.sueldo})"
        )

# PARTE C: Clase EmpleadoService

class EmpleadoService:
    empleados = []
    proximo_legajo = 1

    @classmethod
    def agregar(cls, empleado: Empleado) -> bool:
        empleado.set_legajo(cls.proximo_legajo)
        cls.proximo_legajo += 1
        cls.empleados.append(empleado)
        return True

    @classmethod
    def mayor_sueldo(cls) -> Empleado:
        if not cls.empleados:
            raise CollectionVaciaException("No hay empleados.")
        return max(cls.empleados, key=lambda e: e.sueldo)

    @classmethod
    def sueldo_promedio(cls) -> float:
        if not cls.empleados:
            raise CollectionVaciaException("No hay empleados.")
        total = sum(e.sueldo for e in cls.empleados)
        return total / len(cls.empleados)

    @classmethod
    def eliminar(cls, nombre: str):
        if not cls.empleados:
            raise CollectionVaciaException("No hay empleados.")

        for empleado in cls.empleados:
            if empleado.nombre == nombre:
                cls.empleados.remove(empleado)
                return

        raise NoEncontradoException("No se encontro el empleado.")

# PARTE D: Pruebas

if __name__ == "__main__":

    # 1. Probar datos invalidos
    print("Prueba 1: Datos invalidos")

    try:
        Empleado("Jo", 1000)
    except StringException as e:
        print(f"OK - Excepcion capturada: {e}")

    try:
        Empleado("Juan", -500)
    except EnteroPositivoException as e:
        print(f"OK - Excepcion capturada: {e}")

    try:
        Empleado(None, 1000)
    except StringException as e:
        print(f"OK - Excepcion capturada: {e}")

    # 2. Preparar empleados para las siguientes pruebas
    print()
    print("Prueba 2: Agregar empleados")

    EmpleadoService.empleados = []
    EmpleadoService.proximo_legajo = 1

    EmpleadoService.agregar(Empleado("María López", 50000))
    EmpleadoService.agregar(Empleado("Luis Torres", 80000))

    print("Empleados:")
    for empleado in EmpleadoService.empleados:
        print(f"• {empleado}")
    print("Mayor sueldo:", EmpleadoService.mayor_sueldo())
    print("Sueldo promedio:", EmpleadoService.sueldo_promedio())

    # 3. Intentar eliminar un empleado inexistente
    print()
    print("Prueba 3: Empleado inexistente")

    try:
        EmpleadoService.eliminar("Pedro")
    except NoEncontradoException as e:
        print(f"OK - Excepcion capturada: {e}")

    # 4. Comprobar eliminacion exitosa
    print()
    print("Prueba 4: Eliminar empleado existente")

    try:
        EmpleadoService.eliminar("María López")
        print("Empleado eliminado correctamente.")
        print("Empleados restantes:", EmpleadoService.empleados)
    except NoEncontradoException as e:
        print(e)

    # 5. Probar sueldo promedio con coleccion vacia
    print()
    print("Prueba 5: Coleccion vacia")

    EmpleadoService.empleados = []

    try:
        EmpleadoService.sueldo_promedio()
    except CollectionVaciaException as e:
        print(f"OK - Excepcion capturada: {e}")

    # Comprobar tambien mayor_sueldo con coleccion vacia
    try:
        EmpleadoService.mayor_sueldo()
    except CollectionVaciaException as e:
        print(f"OK - Excepcion capturada: {e}")

    # 6. Probar eliminacion con coleccion vacia
    print()
    print("Prueba 6: Eliminar de coleccion vacia")

    try:
        EmpleadoService.eliminar("Pedro")
    except CollectionVaciaException as e:
        print(f"OK - Excepcion capturada: {e}")

    # 7. Comprobar asignacion automatica de legajo
    print()
    print("Prueba 7: Asignacion de legajo")

    try:
        emp = Empleado("Carlos", 60000)
        EmpleadoService.agregar(emp)
        print(f"Empleado agregado con legajo {emp.legajo}")
    except EnteroPositivoException as e:
        print(f"Excepcion propagada: {e}")

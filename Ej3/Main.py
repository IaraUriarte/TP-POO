import sys
from seeder import inicializar_datos

class Main:
    def __init__(self):
        self.__continentes = inicializar_datos()
        self.__todos_los_paises = []
        for c in self.__continentes:
            self.__todos_los_paises.extend(c.get_paises())

    def ejecutar(self):
        opcion = ""
        while opcion != "6":
            self.__mostrar_menu()
            opcion = input("Seleccione una opción (1-6): ").strip()

            if opcion == "1":
                self.__listar_paises_de_continente()
            elif opcion == "2":
                self.__listar_provincias_de_pais()
            elif opcion == "3":
                self.__listar_limitrofes_de_pais()
            elif opcion == "4":
                self.__listar_paises_por_superficie()
            elif opcion == "5":
                self.__comparar_superficie_paises()
            elif opcion == "6":
                print()
                print("Saliendo de la aplicación del Mapa Mundial...")
                print("Hasta luego")
                print()
            else:
                print()
                print("Error: opción no válida. Intente nuevamente.")

    def __mostrar_menu(self):
        print()
        print("="*24)
        print("MAPA MUNDIAL INTERACTIVO")
        print("="*24)
        print("1. Buscar países por continente")
        print("2. Listar provincias de un país")
        print("3. Listar países limítrofes de un país")
        print("4. Listar todos los países por superficie (mayor a menor)")
        print("5. Comparar superficie de 2 países")
        print("6. Salir")
        print()
    def __buscar_continente(self, nombre: str):
        nombre_normalizado = nombre.strip().lower()
        for c in self.__continentes:
            c_nombre = c.get_nombre().lower().replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
            n_normalizado = nombre_normalizado.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
            if c_nombre == n_normalizado:
                return c
        return None

    def __buscar_pais(self, nombre: str):
        nombre_normalizado = nombre.strip().lower()
        for p in self.__todos_los_paises:
            p_nombre = p.get_nombre().lower().replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
            n_normalizado = nombre_normalizado.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
            if p_nombre == n_normalizado:
                return p
        return None

    def __listar_paises_de_continente(self):
        print()
        print("Buscar países por continente")
        nombre_cont = input("Ingrese el nombre del continente (América / Europa): ").strip()
        continente = self.__buscar_continente(nombre_cont)

        if continente:
            print()
            print(f"{continente.get_nombre()} (Cantidad de países: {len(continente.get_paises())})")
            print()
            for idx, pais in enumerate(continente.get_paises(), 1):
                prefix = f"    {idx}. "
                indent = " " * len(prefix)
                print(f"{prefix}{pais.get_nombre()}")
                print(f"{indent}Capital: {pais.get_capital()}")
                print(f"{indent}Superficie: {pais.get_superficie()} km²")
        else:
            print()
            print(f"Error: no se encontró el continente '{nombre_cont}'.")

    def __listar_provincias_de_pais(self):
        print()
        print("Listar provincias de un país")
        nombre_pais = input("Ingrese el nombre del país: ").strip()
        pais = self.__buscar_pais(nombre_pais)

        if pais:
            provincias = pais.get_provincias()
            print()
            continente_nombre = pais.get_continente().get_nombre() if pais.get_continente() else "Sin Continente"
            print(f"País: {pais.get_nombre()}")
            print(f"Capital: {pais.get_capital()}")
            print(f"Superficie: {pais.get_superficie()} km²")
            print(f"Continente: {continente_nombre}")
            print()
            print("Provincias/Estados/Subdivisiones registradas:")
            if not provincias:
                print("No hay provincias registradas para este país")
            else:
                for idx, prov in enumerate(provincias, 1):
                    print(f"{idx}. {prov.get_nombre()}")
        else:
            print()
            print(f"Error: no se encontró el país '{nombre_pais}'.")

    def __listar_limitrofes_de_pais(self):
        print()
        print("Listar países limitrofes de un país")
        nombre_pais = input("Ingrese el nombre del país: ").strip()
        pais = self.__buscar_pais(nombre_pais)

        if pais:
            limitrofes = pais.get_limitrofes()
            print()
            continente_nombre = pais.get_continente().get_nombre() if pais.get_continente() else "Sin Continente"
            print(f"País: {pais.get_nombre()}")
            print(f"Capital: {pais.get_capital()}")
            print(f"Superficie: {pais.get_superficie()} km²")
            print(f"Continente: {continente_nombre}")
            print()
            print(f"Límites geográficos registrados ({len(limitrofes)}):")
            if not limitrofes:
                print("Este país no tiene límites terrestres registrados en la base de datos")
            else:
                for idx, lim in enumerate(limitrofes, 1):
                    print(f"{idx}. {lim.get_nombre()} (Capital: {lim.get_capital()})")
        else:
            print()
            print(f"Error: no se encontró el país '{nombre_pais}'.")

    def __listar_paises_por_superficie(self):
        print()
        print("Países ordenados por superficie (de mayor a menor):")
        paises_ordenados = sorted(self.__todos_los_paises, key=lambda x: x.get_superficie(), reverse=True)

        for idx, p in enumerate(paises_ordenados, 1):
            superficie_formateada = f"{p.get_superficie():,.1f}".replace(",", "X").replace(".", ",").replace("X", ".")
            cont_nombre = p.get_continente().get_nombre() if p.get_continente() else "N/A"
            
            prefix_num = f"{idx}. "
            indent = " " * len(prefix_num)
            print(f"{prefix_num}País: {p.get_nombre()}")
            print(f"{indent}Capital: {p.get_capital()}")
            print(f"{indent}Superficie: {superficie_formateada} km²")
            print(f"{indent}Continente: {cont_nombre}")

    def __comparar_superficie_paises(self):
        print()
        print("Comparar superficie de dos países")
        nombre1 = input("Ingrese el nombre del primer país: ").strip()
        pais1 = self.__buscar_pais(nombre1)
        if not pais1:
            print(f"Error: no se encontró el país '{nombre1}'.")
            return

        nombre2 = input("Ingrese el nombre del segundo país: ").strip()
        pais2 = self.__buscar_pais(nombre2)
        if not pais2:
            print(f"Error: no se encontró el país '{nombre2}'.")
            return

        if pais1 == pais2:
            print()
            print("Error: ha ingresado el mismo país dos veces.")
            return

        sup1 = pais1.get_superficie()
        sup2 = pais2.get_superficie()

        sup1_form = f"{sup1:,.1f}".replace(",", "X").replace(".", ",").replace("X", ".")
        sup2_form = f"{sup2:,.1f}".replace(",", "X").replace(".", ",").replace("X", ".")

        print()
        print("Resultados de la Comparación:")
        print("-"*50)
        print(f"1. {pais1.get_nombre()}: {sup1_form} km²")
        print(f"2. {pais2.get_nombre()}: {sup2_form} km²")
        print("-" * 50)

        if sup1 > sup2:
            diferencia = sup1 - sup2
            dif_form = f"{diferencia:,.1f}".replace(",", "X").replace(".", ",").replace("X", ".")
            print(f"{pais1.get_nombre()} tiene mayor superficie que {pais2.get_nombre()}")
            print(f"Diferencia: es {dif_form} km² más grande.")
        elif sup2 > sup1:
            diferencia = sup2 - sup1
            dif_form = f"{diferencia:,.1f}".replace(",", "X").replace(".", ",").replace("X", ".")
            print(f"¡{pais2.get_nombre()} tiene mayor superficie que {pais1.get_nombre()}!")
            print(f"Diferencia: es {dif_form} km² más grande.")
        else:
            print("Ambos países tienen exactamente la misma superficie")

if __name__ == "__main__":
    app = Main()
    app.ejecutar()

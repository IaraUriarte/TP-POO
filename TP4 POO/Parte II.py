# CÓDIGO GENERADO POR IA
# Superclase
class Archivo:
    def __init__(self, nombre, pesoEnMB):
        print("Creando Archivo genérico...")
        self.nombre = nombre
        self.pesoEnMB = pesoEnMB


# Subclase
class ArchivoVideo(Archivo):
    def __init__(self, nombre, pesoEnMB):
        super().__init__(nombre, pesoEnMB)
        print("Creando Archivo de Video...")


# Programa principal
video = ArchivoVideo("pelicula.mp4", 850)

print("\nDatos del archivo:")
print("Nombre:", video.nombre)
print("Peso:", video.pesoEnMB, "MB")
# CÓDIGO GENERADO POR IA
class Archivo:
    def __init__(self, nombre, pesoEnMB):
        self.nombre = nombre
        self.pesoEnMB = pesoEnMB
        print("Creando Archivo genérico...")

class ArchivoVideo(Archivo):
    def __init__(self, nombre, pesoEnMB, duracionSegundos=0):
        super().__init__(nombre, pesoEnMB)
        self.duracionSegundos = duracionSegundos
        print("Creando Archivo de Video...")

if __name__ == "__main__":
    archivo1 = Archivo("documento.txt", 2)
    print(f"-> {archivo1.nombre} ({archivo1.pesoEnMB} MB)\n")

    video1 = ArchivoVideo("pelicula.mp4", 1500, 7200)
    print(f"-> {video1.nombre} ({video1.pesoEnMB} MB, {video1.duracionSegundos} seg)")
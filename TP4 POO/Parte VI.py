# CODIGO GENERADO POR LA IA
class Persona:
   def __init__(self, nombre):
       self.nombre = nombre

class Alumno(Persona):
   def __init__(self, nombre, legajo):
       super().__init__(nombre)
       self.legajo = legajo

class Docente(Persona):
   def __init__(self, nombre, materia):
       super().__init__(nombre)
       self.materia = materia

# “Solución forzada”
class AlumnoDocente(Alumno, Docente):
   def __init__(self, nombre, legajo, materia):
       Alumno.__init__(self, nombre, legajo)
       Docente.__init__(self, nombre, materia)

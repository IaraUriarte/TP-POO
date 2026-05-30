class Cancha:
    def __init__(self, numero):
        self.__numero = numero
        self.__turnos = []  

    def get_numero(self):
        return self.__numero

    def get_turnos(self):
        return self.__turnos

    def reservar_turno(self, turno):
        hora = turno.get_hora()
        
        if hora < 14 or hora > 23:
            return False
            
        for t in self.__turnos:
            if t.get_hora() == hora:
                return False  
                
        self.__turnos.append(turno)
        return True

    def cancelar_reserva(self, hora):
        for t in self.__turnos:
            if t.get_hora() == hora:
                self.__turnos.remove(t)
                return True
        return False

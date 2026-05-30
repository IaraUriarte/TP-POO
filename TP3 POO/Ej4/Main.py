from claseTurno import Turno
from claseCancha import Cancha

def mostrar_estado_canchas(canchas):
    print()
    print("=" *28)
    print("ESTADO ACTUAL DE LAS CANCHAS")
    print("=" *28)
    
    for cancha in canchas:
        print()
        print(f"Cancha {cancha.get_numero()}:")
        turnos = cancha.get_turnos()
        
        if not turnos:
            print("Sin reservas para hoy (Libre)")
        else:
            for t in sorted(turnos, key=lambda x: x.get_hora()):
                print(f"  - {t.get_hora()}:00hs: Reservado por {t.get_nombre_cliente()}")

def main():
    canchas = [Cancha(1), Cancha(2), Cancha(3)]
    
    opcion = ""
    while opcion != "4":
        print()
        print("COMPLEJO DEPORTIVO: MENÚ DE RESERVAS")
        print()
        print("1. Ver estado actual de las 3 canchas")
        print("2. Registrar una nueva reserva")
        print("3. Cancelar una reserva")
        print("4. Salir del sistema")
        print()
        
        opcion = input("Seleccione una opción (1-4): ").strip()
        
        if opcion == "1":
            mostrar_estado_canchas(canchas)
            
        elif opcion == "2":
            print()
            print("REGISTRAR NUEVA RESERVA")
            
            nro_cancha_str = input("Ingrese número de cancha (1, 2 o 3): ").strip()
            if not nro_cancha_str.isdigit():
                print("Error: el número de cancha debe ser un número entero.")
                continue
            nro_cancha = int(nro_cancha_str)
            if nro_cancha not in [1, 2, 3]:
                print("Error: cancha inválida. Ingrese 1, 2 o 3.")
                continue
            
            hora_str = input("Ingrese hora del turno (14 a 23 hs en punto): ").strip()
            if not hora_str.isdigit():
                print("Error: la hora debe ser un número entero.")
                continue
            hora = int(hora_str)
            if hora < 14 or hora > 23:
                print("Error: horario fuera de rango (abierto de 14 a 23hs).")
                continue
                
            nombre = input("Ingrese nombre de la persona que reserva: ").strip()
            if not nombre:
                print("Error: el nombre no puede estar vacío.")
                continue
            
            cancha_seleccionada = canchas[nro_cancha - 1]
            nuevo_turno = Turno(nombre, hora)
            
            exito = cancha_seleccionada.reservar_turno(nuevo_turno)
            
            if exito:
                print("RESERVA EXITOSA")
                print()
            else:
                print("Error: turno ocupado o fuera de rango.")
                print()
                
        elif opcion == "3":
            print()
            print("CANCELAR RESERVA")
            
            nro_cancha_str = input("Ingrese número de cancha (1, 2 o 3): ").strip()
            if not nro_cancha_str.isdigit():
                print("Error: debe ingresar un número entero.")
                continue
            nro_cancha = int(nro_cancha_str)
            if nro_cancha not in [1, 2, 3]:
                print("Error: cancha no válida.")
                continue
                
            hora_str = input("Ingrese hora del turno a cancelar (14 a 23 hs): ").strip()
            if not hora_str.isdigit():
                print("Error: debe ingresar un número de hora válido.")
                continue
            hora = int(hora_str)
            
            cancha_seleccionada = canchas[nro_cancha - 1]
            exito = cancha_seleccionada.cancelar_reserva(hora)
            
            if exito:
                print()
                print("RESERVA CANCELADA CON ÉXITO")
            else:
                print()
                print("Error: no existía reserva a esa hora en la cancha seleccionada.")
                
        elif opcion == "4":
            print()
            print("Saliendo...")
            
        else:
            print("Opción incorrecta. Por favor intente de nuevo.")

if __name__ == "__main__":
    main()

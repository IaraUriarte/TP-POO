from claseContinente import Continente
from clasePais import Pais
from claseProvincia import Provincia

def inicializar_datos() -> list:
    america = Continente("América")
    europa = Continente("Europa")

    #AMÉRICA
    
    # Argentina
    argentina = Pais("Argentina", "Buenos Aires", 2780400.0)
    for prov in ["Buenos Aires", "Córdoba", "Santa Fe", "Mendoza", "Tucumán"]:
        argentina.agregarProvincia(Provincia(prov))

    # Brasil
    brasil = Pais("Brasil", "Brasilia", 8515767.0)
    for prov in ["São Paulo", "Rio de Janeiro", "Minas Gerais", "Bahia", "Rio Grande do Sul"]:
        brasil.agregarProvincia(Provincia(prov))

    # Uruguay
    uruguay = Pais("Uruguay", "Montevideo", 176215.0)
    for prov in ["Montevideo", "Canelones", "Maldonado", "Colonia"]:
        uruguay.agregarProvincia(Provincia(prov))

    # Chile
    chile = Pais("Chile", "Santiago", 756102.0)
    for prov in ["Santiago", "Valparaíso", "Biobío", "Antofagasta"]:
        chile.agregarProvincia(Provincia(prov))

    # Bolivia
    bolivia = Pais("Bolivia", "Sucre", 1098581.0)
    for prov in ["La Paz", "Santa Cruz", "Cochabamba", "Oruro"]:
        bolivia.agregarProvincia(Provincia(prov))

    # Paraguay
    paraguay = Pais("Paraguay", "Asunción", 406752.0)
    for prov in ["Central", "Alto Paraná", "Itapúa", "Caaguazú"]:
        paraguay.agregarProvincia(Provincia(prov))

    # Perú
    peru = Pais("Perú", "Lima", 1285216.0)
    for prov in ["Lima", "Arequipa", "Cusco", "La Libertad"]:
        peru.agregarProvincia(Provincia(prov))

    # Colombia
    colombia = Pais("Colombia", "Bogotá", 1141748.0)
    for prov in ["Cundinamarca", "Antioquia", "Valle del Cauca", "Atlántico"]:
        colombia.agregarProvincia(Provincia(prov))

    # Venezuela
    venezuela = Pais("Venezuela", "Caracas", 916445.0)
    for prov in ["Miranda", "Zulia", "Carabobo", "Distrito Capital"]:
        venezuela.agregarProvincia(Provincia(prov))

    # Estados Unidos
    eeuu = Pais("Estados Unidos", "Washington D.C.", 9833517.0)
    for prov in ["California", "Texas", "New York", "Florida", "Illinois"]:
        eeuu.agregarProvincia(Provincia(prov))

    # Canadá
    canada = Pais("Canadá", "Ottawa", 9984670.0)
    for prov in ["Ontario", "Quebec", "British Columbia", "Alberta"]:
        canada.agregarProvincia(Provincia(prov))

    # México
    mexico = Pais("México", "Ciudad de México", 1964375.0)
    for prov in ["Jalisco", "Nuevo León", "Estado de México", "Puebla"]:
        mexico.agregarProvincia(Provincia(prov))

    paises_america = [argentina, brasil, uruguay, chile, bolivia, paraguay, peru, colombia, venezuela, eeuu, canada, mexico]
    for p in paises_america:
        america.agregarPais(p)

    argentina.agregarLimitrofe(chile)
    argentina.agregarLimitrofe(bolivia)
    argentina.agregarLimitrofe(paraguay)
    argentina.agregarLimitrofe(brasil)
    argentina.agregarLimitrofe(uruguay)

    brasil.agregarLimitrofe(uruguay)
    brasil.agregarLimitrofe(paraguay)
    brasil.agregarLimitrofe(bolivia)
    brasil.agregarLimitrofe(peru)
    brasil.agregarLimitrofe(colombia)
    brasil.agregarLimitrofe(venezuela)

    chile.agregarLimitrofe(peru)
    chile.agregarLimitrofe(bolivia)

    bolivia.agregarLimitrofe(paraguay)
    bolivia.agregarLimitrofe(peru)

    peru.agregarLimitrofe(colombia)

    colombia.agregarLimitrofe(venezuela)

    eeuu.agregarLimitrofe(canada)
    eeuu.agregarLimitrofe(mexico)


    # EUROPA

    # España
    espana = Pais("España", "Madrid", 505990.0)
    for prov in ["Madrid", "Cataluña", "Andalucía", "Comunidad Valenciana"]:
        espana.agregarProvincia(Provincia(prov))

    # Portugal
    portugal = Pais("Portugal", "Lisboa", 92212.0)
    for prov in ["Lisboa", "Oporto", "Braga", "Faro"]:
        portugal.agregarProvincia(Provincia(prov))

    # Francia
    francia = Pais("Francia", "París", 551695.0)
    for prov in ["Île-de-France", "Provenza-Alpes-Costa Azul", "Nueva Aquitania", "Auvernia-Ródano-Alpes"]:
        francia.agregarProvincia(Provincia(prov))

    # Italia
    italia = Pais("Italia", "Roma", 301340.0)
    for prov in ["Lacio", "Lombardía", "Toscana", "Véneto"]:
        italia.agregarProvincia(Provincia(prov))

    # Alemania
    alemania = Pais("Alemania", "Berlín", 357022.0)
    for prov in ["Baviera", "Berlín", "Hamburgo", "Renania del Norte-Westfalia"]:
        alemania.agregarProvincia(Provincia(prov))

    # Polonia
    polonia = Pais("Polonia", "Varsovia", 312696.0)
    for prov in ["Mazovia", "Pequeña Polonia", "Gran Polonia"]:
        polonia.agregarProvincia(Provincia(prov))

    # Suiza
    suiza = Pais("Suiza", "Berna", 41285.0)
    for prov in ["Zúrich", "Ginebra", "Berna", "Vaud"]:
        suiza.agregarProvincia(Provincia(prov))

    # Austria
    austria = Pais("Austria", "Viena", 83871.0)
    for prov in ["Viena", "Salzburgo", "Tirol"]:
        austria.agregarProvincia(Provincia(prov))

    paises_europa = [espana, portugal, francia, italia, alemania, polonia, suiza, austria]
    for p in paises_europa:
        europa.agregarPais(p)

    espana.agregarLimitrofe(portugal)
    espana.agregarLimitrofe(francia)

    francia.agregarLimitrofe(italia)
    francia.agregarLimitrofe(alemania)
    francia.agregarLimitrofe(suiza)

    italia.agregarLimitrofe(suiza)
    italia.agregarLimitrofe(austria)

    alemania.agregarLimitrofe(polonia)
    alemania.agregarLimitrofe(suiza)
    alemania.agregarLimitrofe(austria)

    suiza.agregarLimitrofe(austria)

    return [america, europa]

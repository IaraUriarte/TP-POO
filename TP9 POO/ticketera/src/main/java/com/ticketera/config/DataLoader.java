package com.ticketera.config;

import com.ticketera.model.*;
import com.ticketera.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DATOS DE PRUEBA TEMPORALES.
 * Se ejecuta al arrancar la app y crea un cliente y "Rock Fest" con sectores, precios y entradas.
 * Cuando el alta de eventos genere las entradas automáticamente, este archivo se borra.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final ClienteRepository clienteRepository;
    private final LugarRepository lugarRepository;
    private final SectorRepository sectorRepository;
    private final EventoRepository eventoRepository;
    private final PrecioSectorRepository precioSectorRepository;
    private final EntradaRepository entradaRepository;

    public DataLoader(ClienteRepository clienteRepository, LugarRepository lugarRepository,
                      SectorRepository sectorRepository, EventoRepository eventoRepository,
                      PrecioSectorRepository precioSectorRepository, EntradaRepository entradaRepository) {
        this.clienteRepository = clienteRepository;
        this.lugarRepository = lugarRepository;
        this.sectorRepository = sectorRepository;
        this.eventoRepository = eventoRepository;
        this.precioSectorRepository = precioSectorRepository;
        this.entradaRepository = entradaRepository;
    }

    @Override
    public void run(String... args) {
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente");
        cliente.setApellido("Demo");
        cliente.setEmail("demo@mail.com");
        cliente.setDni("00000000");
        cliente = clienteRepository.save(cliente);

        Lugar lugar = new Lugar();
        lugar.setNombre("Estadio Demo");
        lugar.setDireccion("Calle Falsa 123");
        lugar = lugarRepository.save(lugar);

        Sector platea = crearSector("Platea", 10, lugar);
        Sector vip = crearSector("VIP", 3, lugar);

        Evento evento = new Evento();
        evento.setNombre("Rock Fest");
        evento.setFechaHora(LocalDateTime.now().plusMonths(2));
        evento.setLugar(lugar);
        evento = eventoRepository.save(evento);

        crearPrecio(evento, platea, "12000.00");
        crearPrecio(evento, vip, "30000.00");

        generarEntradas(evento, platea, 10, 0); // 10 disponibles
        generarEntradas(evento, vip, 3, 2);     // 3 entradas, 2 ya emitidas -> queda 1 disponible

        log.info("Datos de prueba -> clienteId={}, eventoId={}, Platea sectorId={}, VIP sectorId={}",
                cliente.getId(), evento.getId(), platea.getId(), vip.getId());
    }

    private Sector crearSector(String nombre, int capacidad, Lugar lugar) {
        Sector sector = new Sector();
        sector.setNombre(nombre);
        sector.setCapacidadMaxima(capacidad);
        sector.setLugar(lugar);
        return sectorRepository.save(sector);
    }

    private void crearPrecio(Evento evento, Sector sector, String precio) {
        PrecioSector ps = new PrecioSector();
        ps.setEvento(evento);
        ps.setSector(sector);
        ps.setPrecio(new BigDecimal(precio));
        precioSectorRepository.save(ps);
    }

    private void generarEntradas(Evento evento, Sector sector, int total, int emitidas) {
        List<Entrada> entradas = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Entrada e = new Entrada();
            e.setEvento(evento);
            e.setSector(sector);
            e.setCodigoQr(UUID.randomUUID().toString());
            e.setEstado(i < emitidas ? EstadoEntrada.EMITIDA : EstadoEntrada.DISPONIBLE);
            entradas.add(e);
        }
        entradaRepository.saveAll(entradas);
    }
}

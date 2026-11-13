package com.ticketera.service;

import com.ticketera.dto.EventoRequest;
import com.ticketera.dto.EventoResponse;
import com.ticketera.dto.PrecioSectorRequest;
import com.ticketera.model.Entrada;
import com.ticketera.model.EstadoEntrada;
import com.ticketera.model.Evento;
import com.ticketera.model.Lugar;
import com.ticketera.model.PrecioSector;
import com.ticketera.model.Sector;
import com.ticketera.repository.EntradaRepository;
import com.ticketera.repository.EventoRepository;
import com.ticketera.repository.LugarRepository;
import com.ticketera.repository.SectorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventoService {

    private final EventoRepository eventoRepository;
    private final LugarRepository lugarRepository;
    private final SectorRepository sectorRepository;
    private final EntradaRepository entradaRepository;

    public EventoService(EventoRepository eventoRepository,
            LugarRepository lugarRepository,
            SectorRepository sectorRepository,
            EntradaRepository entradaRepository) {
        this.eventoRepository = eventoRepository;
        this.lugarRepository = lugarRepository;
        this.sectorRepository = sectorRepository;
        this.entradaRepository = entradaRepository;
    }

    /**
     * Crea el evento, fija el precio de cada sector del lugar y genera
     * las entradas (una por cada plaza de la capacidad máxima del sector).
     * Todo en una sola transacción: si algo falla, no queda nada a medias.
     */
    @Transactional
    public EventoResponse crear(EventoRequest request) {
        Lugar lugar = lugarRepository.findById(request.lugarId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No existe un lugar con id " + request.lugarId()));

        Map<Long, Sector> sectoresDelLugar = sectoresDe(lugar);
        validarPrecios(request.precios(), sectoresDelLugar);

        Evento evento = new Evento();
        evento.setNombre(request.nombre());
        evento.setFechaHora(request.fechaHora());
        evento.setLugar(lugar);

        for (PrecioSectorRequest p : request.precios()) {
            PrecioSector precioSector = new PrecioSector();
            precioSector.setEvento(evento);
            precioSector.setSector(sectoresDelLugar.get(p.sectorId()));
            precioSector.setPrecio(p.precio());
            evento.getPrecios().add(precioSector); // se guarda por cascade
        }
        evento = eventoRepository.save(evento);

        for (Sector sector : sectoresDelLugar.values()) {
            entradaRepository.saveAll(generarEntradas(evento, sector));
        }
        return EventoResponse.from(evento);
    }

    public List<EventoResponse> listar() {
        return eventoRepository.findAll().stream().map(EventoResponse::from).toList();
    }

    public EventoResponse buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .map(EventoResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un evento con id " + id));
    }

    // ---------- privados ----------

    private Map<Long, Sector> sectoresDe(Lugar lugar) {
        Map<Long, Sector> sectores = sectorRepository.findAll().stream()
                .filter(s -> s.getLugar() != null && lugar.getId().equals(s.getLugar().getId()))
                .collect(Collectors.toMap(Sector::getId, Function.identity()));
        if (sectores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El lugar " + lugar.getId() + " no tiene sectores cargados");
        }
        return sectores;
    }

    /**
     * Cada sector del lugar debe tener exactamente un precio, y no pueden venir
     * sectores ajenos.
     */
    private void validarPrecios(List<PrecioSectorRequest> precios, Map<Long, Sector> sectoresDelLugar) {
        Set<Long> vistos = new HashSet<>();
        for (PrecioSectorRequest p : precios) {
            if (!sectoresDelLugar.containsKey(p.sectorId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El sector " + p.sectorId() + " no pertenece al lugar del evento");
            }
            if (!vistos.add(p.sectorId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El sector " + p.sectorId() + " tiene más de un precio");
            }
        }
        for (Sector sector : sectoresDelLugar.values()) {
            if (!vistos.contains(sector.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Falta definir el precio del sector " + sector.getId() + " (" + sector.getNombre() + ")");
            }
        }
    }

    private List<Entrada> generarEntradas(Evento evento, Sector sector) {
        List<Entrada> entradas = new ArrayList<>();
        for (int i = 0; i < sector.getCapacidadMaxima(); i++) {
            Entrada entrada = new Entrada();
            entrada.setEvento(evento);
            entrada.setSector(sector);
            entrada.setEstado(EstadoEntrada.DISPONIBLE);
            entrada.setCodigoQr(UUID.randomUUID().toString());
            entradas.add(entrada);
        }
        return entradas;
    }
}
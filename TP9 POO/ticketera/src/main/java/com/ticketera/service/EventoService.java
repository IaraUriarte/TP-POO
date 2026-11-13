package com.ticketera.service;

import com.ticketera.dto.EventoRequest;
import com.ticketera.dto.EventoResponse;
import com.ticketera.model.Evento;
import com.ticketera.model.Lugar;
import com.ticketera.repository.EventoRepository;
import com.ticketera.repository.LugarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventoService {

    private final EventoRepository eventoRepository;
    private final LugarRepository lugarRepository;

    public EventoService(EventoRepository eventoRepository, LugarRepository lugarRepository) {
        this.eventoRepository = eventoRepository;
        this.lugarRepository = lugarRepository;
    }

    @Transactional
    public EventoResponse crear(EventoRequest request) {
        Lugar lugar = lugarRepository.findById(request.lugarId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No existe un lugar con id " + request.lugarId()));

        Evento evento = new Evento();
        evento.setNombre(request.nombre());
        evento.setFechaHora(request.fechaHora());
        evento.setLugar(lugar);
        return EventoResponse.from(eventoRepository.save(evento));
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
}

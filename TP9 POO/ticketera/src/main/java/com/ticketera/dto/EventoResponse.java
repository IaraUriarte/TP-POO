package com.ticketera.dto;

import com.ticketera.model.Evento;

import java.time.LocalDateTime;

public record EventoResponse(Long id, String nombre, LocalDateTime fechaHora, Long lugarId, String lugarNombre) {

    public static EventoResponse from(Evento e) {
        return new EventoResponse(e.getId(), e.getNombre(), e.getFechaHora(),
                e.getLugar().getId(), e.getLugar().getNombre());
    }
}

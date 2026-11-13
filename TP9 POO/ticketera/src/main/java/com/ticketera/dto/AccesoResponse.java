package com.ticketera.dto;

import com.ticketera.model.Entrada;

import java.time.LocalDateTime;

public record AccesoResponse(
        String mensaje,
        Long entradaId,
        String evento,
        String sector,
        LocalDateTime horaIngreso) {

    public static AccesoResponse permitido(Entrada entrada) {
        return new AccesoResponse("Acceso Permitido", entrada.getId(),
                entrada.getEvento().getNombre(), entrada.getSector().getNombre(),
                entrada.getHoraIngreso());
    }
}

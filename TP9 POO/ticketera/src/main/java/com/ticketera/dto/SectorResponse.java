package com.ticketera.dto;

import com.ticketera.model.Sector;

public record SectorResponse(Long id, String nombre, Integer capacidadMaxima, Long lugarId) {

    public static SectorResponse from(Sector s) {
        return new SectorResponse(s.getId(), s.getNombre(), s.getCapacidadMaxima(), s.getLugar().getId());
    }
}

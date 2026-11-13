package com.ticketera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SectorRequest(
        @NotBlank String nombre,
        @NotNull @Positive Integer capacidadMaxima,
        @NotNull Long lugarId) {
}

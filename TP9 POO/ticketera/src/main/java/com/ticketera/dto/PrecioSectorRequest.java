package com.ticketera.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/** Precio que la productora define para un sector en un evento. */
public record PrecioSectorRequest(
        @NotNull Long sectorId,
        @NotNull @Positive BigDecimal precio) {
}
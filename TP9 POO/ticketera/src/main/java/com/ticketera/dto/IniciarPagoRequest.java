package com.ticketera.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IniciarPagoRequest(
        @NotNull Long clienteId,
        @NotNull Long eventoId,
        @NotNull Long sectorId,
        @NotNull @Positive Integer cantidad,
        @NotNull @Valid DatosTarjeta tarjeta) {
}

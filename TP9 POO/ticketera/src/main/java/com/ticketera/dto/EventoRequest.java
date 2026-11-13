package com.ticketera.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record EventoRequest(
                @NotBlank String nombre,
                @NotNull @Future LocalDateTime fechaHora,
                @NotNull Long lugarId,
                @NotEmpty @Valid List<PrecioSectorRequest> precios) {
}
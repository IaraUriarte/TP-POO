package com.ticketera.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventoRequest(
        @NotBlank String nombre,
        @NotNull @Future LocalDateTime fechaHora,
        @NotNull Long lugarId) {
}

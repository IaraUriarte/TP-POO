package com.ticketera.dto;

import jakarta.validation.constraints.NotNull;

public record CotizacionRequest(
        @NotNull Long eventoId,
        @NotNull Long sectorId,
        Integer cantidad) {   // la validación de cantidad la hace el servicio ('Cantidad inválida')
}

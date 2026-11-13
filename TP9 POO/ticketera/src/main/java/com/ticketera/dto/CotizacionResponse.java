package com.ticketera.dto;

import java.math.BigDecimal;

public record CotizacionResponse(
        Long eventoId,
        String eventoNombre,
        Long sectorId,
        String sectorNombre,
        int cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal,
        BigDecimal total) {
}

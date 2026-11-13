package com.ticketera.dto;

import com.ticketera.model.EstadoEntrada;
import com.ticketera.model.EstadoVenta;
import com.ticketera.model.Venta;

import java.math.BigDecimal;
import java.util.List;

public record PagoResponse(Long ventaId, EstadoVenta estado, BigDecimal total, List<EntradaEmitida> entradas) {

    public record EntradaEmitida(Long id, String sector, String codigoQr, EstadoEntrada estado) {
    }

    public static PagoResponse from(Venta venta) {
        List<EntradaEmitida> emitidas = venta.getEntradas().stream()
                .map(e -> new EntradaEmitida(e.getId(), e.getSector().getNombre(), e.getCodigoQr(), e.getEstado()))
                .toList();
        return new PagoResponse(venta.getId(), venta.getEstado(), venta.getTotal(), emitidas);
    }
}

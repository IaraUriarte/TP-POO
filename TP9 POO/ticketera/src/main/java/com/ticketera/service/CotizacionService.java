package com.ticketera.service;

import com.ticketera.dto.CotizacionRequest;
import com.ticketera.dto.CotizacionResponse;
import com.ticketera.model.EstadoEntrada;
import com.ticketera.model.PrecioSector;
import com.ticketera.repository.EntradaRepository;
import com.ticketera.repository.PrecioSectorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class CotizacionService {

    private final PrecioSectorRepository precioSectorRepository;
    private final EntradaRepository entradaRepository;

    // Inyección de dependencias por constructor: Spring entrega los repositorios ya creados
    public CotizacionService(PrecioSectorRepository precioSectorRepository,
                             EntradaRepository entradaRepository) {
        this.precioSectorRepository = precioSectorRepository;
        this.entradaRepository = entradaRepository;
    }

    public CotizacionResponse cotizar(CotizacionRequest request) {
        // Escenario 3: se rechaza antes de tocar la base de datos
        int cantidad = validarCantidad(request.cantidad());

        PrecioSector precioSector = precioSectorRepository
                .findByEventoIdAndSectorId(request.eventoId(), request.sectorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No hay un precio definido para ese sector en ese evento"));

        // Escenario 2: validación de stock físico
        validarDisponibilidad(request, cantidad);

        // Escenario 1: el cálculo lo hace el objeto que conoce el precio
        BigDecimal subtotal = precioSector.calcularSubtotal(cantidad);

        return new CotizacionResponse(
                request.eventoId(),
                precioSector.getEvento().getNombre(),
                request.sectorId(),
                precioSector.getSector().getNombre(),
                cantidad,
                precioSector.getPrecio(),
                subtotal,
                subtotal);
    }

    private int validarCantidad(Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad inválida");
        }
        return cantidad;
    }

    private void validarDisponibilidad(CotizacionRequest request, int cantidad) {
        long disponibles = entradaRepository.countByEventoIdAndSectorIdAndEstado(
                request.eventoId(), request.sectorId(), EstadoEntrada.DISPONIBLE);
        if (disponibles < cantidad) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Capacidad insuficiente");
        }
    }
}

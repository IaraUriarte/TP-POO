package com.ticketera.service;

import com.ticketera.dto.DatosTarjeta;
import com.ticketera.dto.IniciarPagoRequest;
import com.ticketera.dto.PagoResponse;
import com.ticketera.model.Cliente;
import com.ticketera.model.Entrada;
import com.ticketera.model.EstadoEntrada;
import com.ticketera.model.PrecioSector;
import com.ticketera.model.Venta;
import com.ticketera.pasarela.PasarelaPago;
import com.ticketera.pasarela.ResultadoPago;
import com.ticketera.repository.ClienteRepository;
import com.ticketera.repository.EntradaRepository;
import com.ticketera.repository.PrecioSectorRepository;
import com.ticketera.repository.VentaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PagoService {

    private final ClienteRepository clienteRepository;
    private final PrecioSectorRepository precioSectorRepository;
    private final EntradaRepository entradaRepository;
    private final VentaRepository ventaRepository;
    private final PasarelaPago pasarelaPago;   // interfaz: hoy es la simulada, mañana puede ser una real

    // Inyección por constructor: Spring entrega todas las dependencias ya construidas
    public PagoService(ClienteRepository clienteRepository,
                       PrecioSectorRepository precioSectorRepository,
                       EntradaRepository entradaRepository,
                       VentaRepository ventaRepository,
                       PasarelaPago pasarelaPago) {
        this.clienteRepository = clienteRepository;
        this.precioSectorRepository = precioSectorRepository;
        this.entradaRepository = entradaRepository;
        this.ventaRepository = ventaRepository;
        this.pasarelaPago = pasarelaPago;
    }

    /**
     * Todo ocurre en UNA transacción: si algo lanza una excepción (sin disponibilidad, pago denegado),
     * se deshace todo y la base queda exactamente como estaba.
     */
    @Transactional
    public PagoResponse iniciarPago(IniciarPagoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un cliente con id " + request.clienteId()));

        PrecioSector precioSector = precioSectorRepository
                .findByEventoIdAndSectorId(request.eventoId(), request.sectorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No hay un precio definido para ese sector en ese evento"));

        // Escenario 3: si ya no hay lugares, se corta ANTES de llamar a la pasarela
        List<Entrada> entradas = reservarDisponibles(request);

        // El total se recalcula en el servidor; nunca se confía en un monto enviado por el cliente
        BigDecimal total = precioSector.calcularSubtotal(request.cantidad());

        // Escenario 2: si la pasarela rechaza, se lanza la excepción y no se guardó nada
        cobrar(total, request.tarjeta());

        // Escenario 1: venta PAGADA + entradas EMITIDAS
        Venta venta = Venta.pagada(cliente, total);
        venta.emitirEntradas(entradas);
        ventaRepository.save(venta);

        return PagoResponse.from(venta);
    }

    /**
     * Busca N entradas disponibles y las bloquea en la base (SELECT ... FOR UPDATE).
     * Si dos clientes compran a la vez, el segundo espera a que termine el primero y,
     * al leer, ya las ve EMITIDAS.
     */
    private List<Entrada> reservarDisponibles(IniciarPagoRequest request) {
        List<Long> ids = entradaRepository.buscarIdsPorEstado(
                request.eventoId(), request.sectorId(), EstadoEntrada.DISPONIBLE,
                PageRequest.of(0, request.cantidad()));
        if (ids.size() < request.cantidad()) {
            throw sinDisponibilidad();
        }

        List<Entrada> entradas = entradaRepository.buscarYBloquear(ids);
        if (entradas.stream().anyMatch(e -> !e.estaDisponible())) {
            throw sinDisponibilidad();
        }
        return entradas;
    }

    private void cobrar(BigDecimal total, DatosTarjeta tarjeta) {
        if (pasarelaPago.procesar(total, tarjeta) != ResultadoPago.APROBADO) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "Pago denegado");
        }
    }

    private ResponseStatusException sinDisponibilidad() {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Los lugares seleccionados ya no se encuentran disponibles");
    }
}

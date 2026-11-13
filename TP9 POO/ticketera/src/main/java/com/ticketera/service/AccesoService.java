package com.ticketera.service;

import com.ticketera.dto.AccesoResponse;
import com.ticketera.dto.EscaneoRequest;
import com.ticketera.model.Entrada;
import com.ticketera.repository.EntradaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class AccesoService {

    private final EntradaRepository entradaRepository;
    private final Clock clock;

    // Inyección por constructor: Spring entrega el repositorio y el reloj
    public AccesoService(EntradaRepository entradaRepository, Clock clock) {
        this.entradaRepository = entradaRepository;
        this.clock = clock;
    }

    @Transactional
    public AccesoResponse escanear(EscaneoRequest request) {
        // Escenario 3: el código no existe en la base
        Entrada entrada = entradaRepository.findByCodigoQr(request.codigoQr().trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ticket Inválido o Inexistente"));

        // Escenarios 2 y 4: se rechaza sin modificar nada
        validarQueSePuedeIngresar(entrada);

        // Escenario 1: la propia entrada cambia de estado y guarda la hora
        entrada.registrarIngreso(LocalDateTime.now(clock));

        return AccesoResponse.permitido(entrada);
    }

    private void validarQueSePuedeIngresar(Entrada entrada) {
        if (entrada.estaUtilizada()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Entrada ya utilizada");
        }
        if (!entrada.estaEmitida()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket no emitido / Falta de pago");
        }
    }
}

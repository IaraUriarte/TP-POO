package com.ticketera.controller;

import com.ticketera.dto.IniciarPagoRequest;
import com.ticketera.dto.PagoResponse;
import com.ticketera.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Pagar entradas con tarjeta y emitirlas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago aprobado: venta PAGADA y entradas EMITIDAS"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "402", description = "Pago denegado por la pasarela", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente o precio inexistente", content = @Content),
            @ApiResponse(responseCode = "409", description = "Los lugares ya no están disponibles", content = @Content)
    })
    @PostMapping("/iniciar-pago")
    @ResponseStatus(HttpStatus.CREATED)
    public PagoResponse iniciarPago(@Valid @RequestBody IniciarPagoRequest request) {
        return pagoService.iniciarPago(request);
    }

    @Operation(summary = "Webhook del proveedor de pagos (pendiente)")
    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody Map<String, Object> payload) {
        // TODO: implementar
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}

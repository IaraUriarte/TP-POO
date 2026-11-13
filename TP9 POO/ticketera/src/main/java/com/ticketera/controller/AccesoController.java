package com.ticketera.controller;

import com.ticketera.dto.AccesoResponse;
import com.ticketera.dto.EscaneoRequest;
import com.ticketera.service.AccesoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accesos")
@Tag(name = "Accesos")
public class AccesoController {

    private final AccesoService accesoService;

    public AccesoController(AccesoService accesoService) {
        this.accesoService = accesoService;
    }

    @Operation(summary = "Escanear el QR de una entrada en el ingreso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acceso permitido"),
            @ApiResponse(responseCode = "400", description = "Entrada no emitida / falta de pago", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ticket inválido o inexistente", content = @Content),
            @ApiResponse(responseCode = "409", description = "Entrada ya utilizada", content = @Content)
    })
    @PostMapping("/escanear-acceso")
    public AccesoResponse escanearAcceso(@Valid @RequestBody EscaneoRequest request) {
        return accesoService.escanear(request);
    }
}

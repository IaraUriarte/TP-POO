package com.ticketera.controller;

import com.ticketera.dto.CotizacionRequest;
import com.ticketera.dto.CotizacionResponse;
import com.ticketera.service.CotizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Ventas")
public class CotizacionController {

    private final CotizacionService cotizacionService;

    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    @Operation(summary = "Cotizar entradas de un evento y sector")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cotización calculada"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida o capacidad insuficiente", content = @Content),
            @ApiResponse(responseCode = "404", description = "El sector no tiene precio para ese evento", content = @Content)
    })
    @PostMapping("/cotizar")
    public CotizacionResponse cotizar(@Valid @RequestBody CotizacionRequest request) {
        return cotizacionService.cotizar(request);
    }
}

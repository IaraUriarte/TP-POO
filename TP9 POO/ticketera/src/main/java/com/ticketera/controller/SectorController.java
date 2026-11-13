package com.ticketera.controller;

import com.ticketera.dto.SectorRequest;
import com.ticketera.dto.SectorResponse;
import com.ticketera.service.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sectores")
@Tag(name = "Sectores", description = "Sectores de un lugar; definen la capacidad máxima física")
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @Operation(summary = "Crear un sector dentro de un lugar")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sector creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o lugar inexistente", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SectorResponse> crear(@Valid @RequestBody SectorRequest request) {
        SectorResponse creado = sectorService.crear(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(creado.id()).toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @Operation(summary = "Listar todos los sectores")
    @GetMapping
    public List<SectorResponse> listar() {
        return sectorService.listar();
    }

    @Operation(summary = "Buscar un sector por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sector encontrado"),
            @ApiResponse(responseCode = "404", description = "Sector inexistente", content = @Content)
    })
    @GetMapping("/{id}")
    public SectorResponse buscarPorId(@PathVariable Long id) {
        return sectorService.buscarPorId(id);
    }
}

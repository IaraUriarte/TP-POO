package com.ticketera.controller;

import com.ticketera.dto.EventoRequest;
import com.ticketera.dto.EventoResponse;
import com.ticketera.service.EventoService;
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
@RequestMapping("/api/eventos")
@Tag(name = "Eventos", description = "Recitales, partidos y otros eventos que se realizan en un lugar")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @Operation(summary = "Crear un evento en un lugar")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o lugar inexistente", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventoResponse> crear(@Valid @RequestBody EventoRequest request) {
        EventoResponse creado = eventoService.crear(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(creado.id()).toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @Operation(summary = "Listar todos los eventos")
    @GetMapping
    public List<EventoResponse> listar() {
        return eventoService.listar();
    }

    @Operation(summary = "Buscar un evento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "Evento inexistente", content = @Content)
    })
    @GetMapping("/{id}")
    public EventoResponse buscarPorId(@PathVariable Long id) {
        return eventoService.buscarPorId(id);
    }
}

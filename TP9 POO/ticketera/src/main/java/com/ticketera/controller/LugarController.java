package com.ticketera.controller;

import com.ticketera.model.Lugar;
import com.ticketera.repository.LugarRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/lugares")
@Tag(name = "Lugares", description = "Gestión de lugares (estadios, teatros, etc.)")
public class LugarController {

    private final LugarRepository lugarRepository;

    // Inyección por constructor (no hace falta @Autowired con un único constructor)
    public LugarController(LugarRepository lugarRepository) {
        this.lugarRepository = lugarRepository;
    }

    @Operation(summary = "Crear un lugar")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lugar creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @PostMapping
    public ResponseEntity<Lugar> crear(@Valid @RequestBody Lugar lugar) {
        lugar.setId(null); // evita que el cliente fuerce un id y pise un registro existente
        Lugar guardado = lugarRepository.save(lugar);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(guardado.getId())
                .toUri();
        return ResponseEntity.created(location).body(guardado);
    }

    @Operation(summary = "Listar todos los lugares")
    @GetMapping
    public List<Lugar> listar() {
        return lugarRepository.findAll();
    }

    @Operation(summary = "Buscar un lugar por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lugar encontrado"),
            @ApiResponse(responseCode = "404", description = "Lugar inexistente", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @GetMapping("/{id}")
    public Lugar buscarPorId(@PathVariable Long id) {
        return lugarRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un lugar con id " + id));
    }
}

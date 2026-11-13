package com.ticketera.dto;

import com.ticketera.model.Cliente;

public record ClienteResponse(Long id, String nombre, String apellido, String email, String dni) {

    public static ClienteResponse from(Cliente c) {
        return new ClienteResponse(c.getId(), c.getNombre(), c.getApellido(), c.getEmail(), c.getDni());
    }
}

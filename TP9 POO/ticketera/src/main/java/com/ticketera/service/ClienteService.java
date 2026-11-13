package com.ticketera.service;

import com.ticketera.dto.ClienteRequest;
import com.ticketera.dto.ClienteResponse;
import com.ticketera.model.Cliente;
import com.ticketera.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        if (clienteRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un cliente con el email " + request.email());
        }
        if (clienteRepository.existsByDni(request.dni())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un cliente con el DNI " + request.dni());
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(request.nombre());
        cliente.setApellido(request.apellido());
        cliente.setEmail(request.email());
        cliente.setDni(request.dni());
        return ClienteResponse.from(clienteRepository.save(cliente));
    }

    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream().map(ClienteResponse::from).toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un cliente con id " + id));
    }
}

package com.ticketera.service;

import com.ticketera.dto.SectorRequest;
import com.ticketera.dto.SectorResponse;
import com.ticketera.model.Lugar;
import com.ticketera.model.Sector;
import com.ticketera.repository.LugarRepository;
import com.ticketera.repository.SectorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SectorService {

    private final SectorRepository sectorRepository;
    private final LugarRepository lugarRepository;

    public SectorService(SectorRepository sectorRepository, LugarRepository lugarRepository) {
        this.sectorRepository = sectorRepository;
        this.lugarRepository = lugarRepository;
    }

    @Transactional
    public SectorResponse crear(SectorRequest request) {
        Lugar lugar = lugarRepository.findById(request.lugarId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No existe un lugar con id " + request.lugarId()));

        Sector sector = new Sector();
        sector.setNombre(request.nombre());
        sector.setCapacidadMaxima(request.capacidadMaxima());
        sector.setLugar(lugar);
        return SectorResponse.from(sectorRepository.save(sector));
    }

    public List<SectorResponse> listar() {
        return sectorRepository.findAll().stream().map(SectorResponse::from).toList();
    }

    public SectorResponse buscarPorId(Long id) {
        return sectorRepository.findById(id)
                .map(SectorResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un sector con id " + id));
    }
}

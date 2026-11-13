package com.ticketera.repository;

import com.ticketera.model.PrecioSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecioSectorRepository extends JpaRepository<PrecioSector, Long> {

    Optional<PrecioSector> findByEventoIdAndSectorId(Long eventoId, Long sectorId);
}

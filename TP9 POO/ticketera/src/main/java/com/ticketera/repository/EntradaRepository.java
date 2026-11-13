package com.ticketera.repository;

import com.ticketera.model.Entrada;
import com.ticketera.model.EstadoEntrada;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    long countByEventoIdAndSectorIdAndEstado(Long eventoId, Long sectorId, EstadoEntrada estado);

    /** Ids de las primeras N entradas en el estado pedido (N = tamaño del Pageable). */
    @Query("select e.id from Entrada e "
            + "where e.evento.id = :eventoId and e.sector.id = :sectorId and e.estado = :estado "
            + "order by e.id")
    List<Long> buscarIdsPorEstado(@Param("eventoId") Long eventoId,
                                  @Param("sectorId") Long sectorId,
                                  @Param("estado") EstadoEntrada estado,
                                  Pageable pageable);

    /** Trae las entradas y las bloquea (SELECT ... FOR UPDATE) hasta que termine la transacción. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Entrada e where e.id in :ids")
    List<Entrada> buscarYBloquear(@Param("ids") List<Long> ids);

    /**
     * Busca por código QR bloqueando la fila: si dos acomodadores escanean el mismo QR a la vez,
     * el segundo espera y luego la ve como UTILIZADA.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Entrada> findByCodigoQr(String codigoQr);
}

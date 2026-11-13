package com.ticketera.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Precio de un sector para un evento concreto.
 * Es el "experto" en su propio precio, por eso sabe calcular subtotales.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"evento_id", "sector_id"}))
@Getter @Setter @NoArgsConstructor
public class PrecioSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    private BigDecimal precio;

    public BigDecimal calcularSubtotal(int cantidad) {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }
}

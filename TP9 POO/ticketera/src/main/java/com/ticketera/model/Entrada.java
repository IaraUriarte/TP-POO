package com.ticketera.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    // Null mientras la entrada está DISPONIBLE
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @Enumerated(EnumType.STRING)
    private EstadoEntrada estado = EstadoEntrada.DISPONIBLE;

    @Column(unique = true)
    private String codigoQr;

    // Se completa al escanear el acceso
    private LocalDateTime horaIngreso;

    public boolean estaDisponible() {
        return estado == EstadoEntrada.DISPONIBLE;
    }

    public boolean estaEmitida() {
        return estado == EstadoEntrada.EMITIDA;
    }

    public boolean estaUtilizada() {
        return estado == EstadoEntrada.UTILIZADA;
    }

    /** Pasa la entrada a EMITIDA asociándola a la venta. Sólo es válido desde DISPONIBLE. */
    public void emitir(Venta venta) {
        if (!estaDisponible()) {
            throw new IllegalStateException("La entrada " + id + " no está disponible");
        }
        this.venta = venta;
        this.estado = EstadoEntrada.EMITIDA;
    }

    /** Registra el ingreso al estadio. Sólo es válido desde EMITIDA, así nunca pisa una hora anterior. */
    public void registrarIngreso(LocalDateTime hora) {
        if (!estaEmitida()) {
            throw new IllegalStateException("La entrada " + id + " no está emitida");
        }
        this.estado = EstadoEntrada.UTILIZADA;
        this.horaIngreso = hora;
    }
}

package com.ticketera.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private EstadoVenta estado = EstadoVenta.PENDIENTE;

    private LocalDateTime fechaCreacion;
    private BigDecimal total;

    @OneToMany(mappedBy = "venta")
    private List<Entrada> entradas = new ArrayList<>();

    /** Crea una venta ya cobrada. */
    public static Venta pagada(Cliente cliente, BigDecimal total) {
        Venta venta = new Venta();
        venta.cliente = cliente;
        venta.total = total;
        venta.estado = EstadoVenta.PAGADA;
        venta.fechaCreacion = LocalDateTime.now();
        return venta;
    }

    /** Agrupa las entradas en la venta y las pasa a EMITIDA. */
    public void emitirEntradas(List<Entrada> aEmitir) {
        for (Entrada entrada : aEmitir) {
            entrada.emitir(this);
            entradas.add(entrada);
        }
    }
}

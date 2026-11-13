package com.ticketera.pasarela;

import com.ticketera.dto.DatosTarjeta;

import java.math.BigDecimal;

/** Contrato de cualquier pasarela de pagos. El servicio depende de esta interfaz, no de una implementación. */
public interface PasarelaPago {

    ResultadoPago procesar(BigDecimal monto, DatosTarjeta tarjeta);
}

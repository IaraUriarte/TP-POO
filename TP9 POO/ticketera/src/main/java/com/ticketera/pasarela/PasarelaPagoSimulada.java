package com.ticketera.pasarela;

import com.ticketera.dto.DatosTarjeta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Simulación para desarrollo: las tarjetas cuyo número termina en "0000" son rechazadas
 * (ej. fondos insuficientes). Cualquier otra se aprueba.
 * Para usar una pasarela real se crea otra clase que implemente PasarelaPago.
 */
@Component
public class PasarelaPagoSimulada implements PasarelaPago {

    @Override
    public ResultadoPago procesar(BigDecimal monto, DatosTarjeta tarjeta) {
        return tarjeta.numero().endsWith("0000") ? ResultadoPago.RECHAZADO : ResultadoPago.APROBADO;
    }
}

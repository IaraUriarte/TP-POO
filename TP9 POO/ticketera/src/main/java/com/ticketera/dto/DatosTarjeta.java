package com.ticketera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosTarjeta(
        @NotBlank @Pattern(regexp = "\\d{16}", message = "debe tener 16 dígitos") String numero,
        @NotBlank String titular,
        @NotBlank @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "formato MM/AA") String vencimiento,
        @NotBlank @Pattern(regexp = "\\d{3,4}", message = "debe tener 3 o 4 dígitos") String cvv) {
}

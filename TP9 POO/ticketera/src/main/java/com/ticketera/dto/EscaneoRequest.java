package com.ticketera.dto;

import jakarta.validation.constraints.NotBlank;

public record EscaneoRequest(@NotBlank String codigoQr) {
}

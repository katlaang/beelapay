package com.beelapay.identity.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateAttachedRequest(
        @NotNull UUID masterUserId,
        @NotBlank String phoneNumber,
        @NotBlank String fullName,
        @NotNull @DecimalMin("0.00") BigDecimal dailyLimit,
        @NotNull @DecimalMin("0.00") BigDecimal perTxLimit
) {}

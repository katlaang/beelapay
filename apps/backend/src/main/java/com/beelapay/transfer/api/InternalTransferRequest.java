package com.beelapay.transfer.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record InternalTransferRequest(
        @NotNull UUID sourceWalletId,
        @NotNull UUID targetWalletId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank String reference,
        @NotBlank String channel
) {}

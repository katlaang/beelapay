package com.beelapay.wallet.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateWalletRequest(
        @NotNull UUID userId,
        @NotBlank String currencyCode
) {}

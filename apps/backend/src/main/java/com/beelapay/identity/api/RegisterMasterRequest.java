package com.beelapay.identity.api;

import jakarta.validation.constraints.NotBlank;

public record RegisterMasterRequest(
        @NotBlank String phoneNumber,
        @NotBlank String fullName,
        @NotBlank String countryResidency,
        @NotBlank String otpCode
) {}

package com.beelapay.identity.api;

import com.beelapay.identity.domain.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendOtpRequest(
        @NotBlank String phoneNumber,
        @NotNull OtpPurpose purpose
) {}

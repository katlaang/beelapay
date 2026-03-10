package com.beelapay.identity.api;

import com.beelapay.identity.domain.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyOtpRequest(
        @NotBlank String phoneNumber,
        @NotNull OtpPurpose purpose,
        @NotBlank String otpCode
) {}

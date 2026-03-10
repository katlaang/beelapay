package com.beelapay.identity.api;

import com.beelapay.identity.domain.UserAccount;
import com.beelapay.identity.service.IdentityService;
import com.beelapay.identity.service.OtpService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {

    private final OtpService otpService;
    private final IdentityService identityService;

    public IdentityController(OtpService otpService, IdentityService identityService) {
        this.otpService = otpService;
        this.identityService = identityService;
    }

    @PostMapping("/otp/send")
    public Map<String, String> sendOtp(@Valid @RequestBody SendOtpRequest request) {
        otpService.sendOtp(request.phoneNumber(), request.purpose());
        return Map.of("status", "OTP_SENT");
    }

    @PostMapping("/otp/verify")
    public Map<String, Object> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        boolean verified = otpService.verifyOtp(request.phoneNumber(), request.purpose(), request.otpCode());
        return Map.of("verified", verified);
    }

    @PostMapping("/register-master")
    public UserAccount registerMaster(@Valid @RequestBody RegisterMasterRequest request) {
        return identityService.registerMaster(request);
    }

    @PostMapping("/attached")
    public UserAccount createAttached(@Valid @RequestBody CreateAttachedRequest request) {
        return identityService.createAttachedAccount(request);
    }
}

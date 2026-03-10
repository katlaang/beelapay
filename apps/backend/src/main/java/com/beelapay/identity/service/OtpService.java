package com.beelapay.identity.service;

import com.beelapay.audit.service.AuditService;
import com.beelapay.common.ApiException;
import com.beelapay.identity.domain.OtpCode;
import com.beelapay.identity.domain.OtpPurpose;
import com.beelapay.identity.repo.OtpCodeRepository;
import com.beelapay.notification.service.NotificationService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private final OtpCodeRepository otpCodeRepository;
    private final NotificationService notificationService;
    private final AuditService auditService;
    private final Random random = new Random();

    public OtpService(OtpCodeRepository otpCodeRepository,
                      NotificationService notificationService,
                      AuditService auditService) {
        this.otpCodeRepository = otpCodeRepository;
        this.notificationService = notificationService;
        this.auditService = auditService;
    }

    public void sendOtp(String phoneNumber, OtpPurpose purpose) {
        String otp = String.format("%06d", random.nextInt(1_000_000));

        OtpCode code = new OtpCode();
        code.setId(UUID.randomUUID());
        code.setPhoneNumber(phoneNumber);
        code.setPurpose(purpose);
        code.setOtpHash(hash(otp));
        code.setAttemptCount(0);
        code.setMaxAttempts(5);
        code.setCreatedAt(Instant.now());
        code.setExpiresAt(Instant.now().plusSeconds(300));
        otpCodeRepository.save(code);

        notificationService.sendOtp(phoneNumber, otp);
        auditService.record("OTP_SENT", null, phoneNumber, purpose.name());
    }

    public boolean verifyOtp(String phoneNumber, OtpPurpose purpose, String otpCode) {
        OtpCode code = otpCodeRepository.findTopByPhoneNumberAndPurposeOrderByCreatedAtDesc(phoneNumber, purpose)
                .orElseThrow(() -> new ApiException("OTP not found"));

        if (code.getVerifiedAt() != null) {
            return true;
        }

        if (Instant.now().isAfter(code.getExpiresAt())) {
            throw new ApiException("OTP expired");
        }

        if (code.getAttemptCount() >= code.getMaxAttempts()) {
            throw new ApiException("OTP attempts exceeded");
        }

        if (!code.getOtpHash().equals(hash(otpCode))) {
            code.setAttemptCount(code.getAttemptCount() + 1);
            otpCodeRepository.save(code);
            throw new ApiException("Invalid OTP");
        }

        code.setVerifiedAt(Instant.now());
        otpCodeRepository.save(code);
        auditService.record("OTP_VERIFIED", null, phoneNumber, purpose.name());
        return true;
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }
}

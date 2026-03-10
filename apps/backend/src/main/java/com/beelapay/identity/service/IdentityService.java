package com.beelapay.identity.service;

import com.beelapay.audit.service.AuditService;
import com.beelapay.common.ApiException;
import com.beelapay.identity.api.CreateAttachedRequest;
import com.beelapay.identity.api.RegisterMasterRequest;
import com.beelapay.identity.domain.OtpPurpose;
import com.beelapay.identity.domain.UserAccount;
import com.beelapay.identity.domain.UserRole;
import com.beelapay.identity.domain.UserStatus;
import com.beelapay.identity.repo.UserAccountRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IdentityService {

    private final UserAccountRepository userAccountRepository;
    private final OtpService otpService;
    private final AuditService auditService;

    public IdentityService(UserAccountRepository userAccountRepository, OtpService otpService, AuditService auditService) {
        this.userAccountRepository = userAccountRepository;
        this.otpService = otpService;
        this.auditService = auditService;
    }

    @Transactional
    public UserAccount registerMaster(RegisterMasterRequest request) {
        otpService.verifyOtp(request.phoneNumber(), OtpPurpose.REGISTRATION, request.otpCode());

        userAccountRepository.findByPhoneNumber(request.phoneNumber()).ifPresent(existing -> {
            throw new ApiException("Phone number already registered");
        });

        UserAccount user = new UserAccount();
        user.setId(UUID.randomUUID());
        user.setPhoneNumber(request.phoneNumber());
        user.setFullName(request.fullName());
        user.setCountryResidency(request.countryResidency().toUpperCase());
        user.setRole(UserRole.MASTER);
        user.setStatus(UserStatus.PENDING_KYC);
        user.setDailyLimit(BigDecimal.ZERO);
        user.setPerTxLimit(BigDecimal.ZERO);
        user.setCreatedAt(Instant.now());

        UserAccount saved = userAccountRepository.save(user);
        auditService.record("MASTER_REGISTERED", saved.getId(), saved.getPhoneNumber(), saved.getCountryResidency());
        return saved;
    }

    @Transactional
    public UserAccount createAttachedAccount(CreateAttachedRequest request) {
        UserAccount master = userAccountRepository.findById(request.masterUserId())
                .orElseThrow(() -> new ApiException("Master account not found"));

        if (master.getRole() != UserRole.MASTER) {
            throw new ApiException("Provided account is not a master account");
        }

        UserAccount attached = new UserAccount();
        attached.setId(UUID.randomUUID());
        attached.setPhoneNumber(request.phoneNumber());
        attached.setFullName(request.fullName());
        attached.setRole(UserRole.ATTACHED);
        attached.setStatus(UserStatus.PENDING_KYC);
        attached.setCountryResidency(master.getCountryResidency());
        attached.setParentUserId(master.getId());
        attached.setDailyLimit(request.dailyLimit());
        attached.setPerTxLimit(request.perTxLimit());
        attached.setCreatedAt(Instant.now());

        UserAccount saved = userAccountRepository.save(attached);
        auditService.record("ATTACHED_CREATED", master.getId(), saved.getId().toString(), "limits configured");
        return saved;
    }
}

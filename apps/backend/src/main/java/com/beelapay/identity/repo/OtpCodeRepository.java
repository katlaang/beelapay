package com.beelapay.identity.repo;

import com.beelapay.identity.domain.OtpCode;
import com.beelapay.identity.domain.OtpPurpose;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {
    Optional<OtpCode> findTopByPhoneNumberAndPurposeOrderByCreatedAtDesc(String phoneNumber, OtpPurpose purpose);
}

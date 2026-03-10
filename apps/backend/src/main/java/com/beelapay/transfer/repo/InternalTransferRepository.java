package com.beelapay.transfer.repo;

import com.beelapay.transfer.domain.InternalTransfer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalTransferRepository extends JpaRepository<InternalTransfer, UUID> {
    Optional<InternalTransfer> findByReference(String reference);
}

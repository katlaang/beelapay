package com.beelapay.transfer.service;

import com.beelapay.audit.service.AuditService;
import com.beelapay.common.ApiException;
import com.beelapay.identity.domain.UserAccount;
import com.beelapay.identity.domain.UserRole;
import com.beelapay.identity.repo.UserAccountRepository;
import com.beelapay.ledger.domain.LedgerEntryType;
import com.beelapay.ledger.service.LedgerService;
import com.beelapay.notification.service.NotificationService;
import com.beelapay.transfer.api.InternalTransferRequest;
import com.beelapay.transfer.domain.InternalTransfer;
import com.beelapay.transfer.domain.TransferStatus;
import com.beelapay.transfer.repo.InternalTransferRepository;
import com.beelapay.wallet.domain.Wallet;
import com.beelapay.wallet.service.WalletService;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final WalletService walletService;
    private final LedgerService ledgerService;
    private final InternalTransferRepository internalTransferRepository;
    private final UserAccountRepository userAccountRepository;
    private final NotificationService notificationService;
    private final AuditService auditService;

    public TransferService(WalletService walletService,
                           LedgerService ledgerService,
                           InternalTransferRepository internalTransferRepository,
                           UserAccountRepository userAccountRepository,
                           NotificationService notificationService,
                           AuditService auditService) {
        this.walletService = walletService;
        this.ledgerService = ledgerService;
        this.internalTransferRepository = internalTransferRepository;
        this.userAccountRepository = userAccountRepository;
        this.notificationService = notificationService;
        this.auditService = auditService;
    }

    @Transactional
    public InternalTransfer internalTransfer(InternalTransferRequest request) {
        InternalTransfer existing = internalTransferRepository.findByReference(request.reference()).orElse(null);
        if (existing != null) {
            return existing;
        }

        Wallet source = walletService.lockWallet(request.sourceWalletId());
        Wallet target = walletService.lockWallet(request.targetWalletId());

        if (!source.getCurrencyCode().equals(target.getCurrencyCode())) {
            throw new ApiException("Cross-currency transfers are not supported in this endpoint");
        }

        if (source.getBalance().compareTo(request.amount()) < 0) {
            throw new ApiException("Insufficient balance");
        }

        enforceAttachedLimits(source.getUserId(), request.amount());

        source.setBalance(source.getBalance().subtract(request.amount()));
        target.setBalance(target.getBalance().add(request.amount()));
        walletService.save(source);
        walletService.save(target);

        ledgerService.post(source.getId(), LedgerEntryType.DEBIT, request.amount(), request.reference(), request.channel());
        ledgerService.post(target.getId(), LedgerEntryType.CREDIT, request.amount(), request.reference(), request.channel());

        InternalTransfer transfer = new InternalTransfer();
        transfer.setId(UUID.randomUUID());
        transfer.setSourceWalletId(source.getId());
        transfer.setTargetWalletId(target.getId());
        transfer.setAmount(request.amount());
        transfer.setReference(request.reference());
        transfer.setChannel(request.channel());
        transfer.setStatus(TransferStatus.COMPLETED);
        transfer.setCreatedAt(Instant.now());
        transfer.setUpdatedAt(Instant.now());
        InternalTransfer saved = internalTransferRepository.save(transfer);

        userAccountRepository.findById(source.getUserId()).ifPresent(user ->
                notificationService.sendTransferConfirmation(user.getPhoneNumber(),
                        "Transfer " + request.reference() + " completed: " + request.amount()));

        auditService.record("INTERNAL_TRANSFER_COMPLETED", source.getUserId(), request.reference(), request.channel());
        return saved;
    }

    private void enforceAttachedLimits(UUID sourceUserId, BigDecimal amount) {
        UserAccount user = userAccountRepository.findById(sourceUserId)
                .orElseThrow(() -> new ApiException("User not found for source wallet"));

        if (user.getRole() != UserRole.ATTACHED) {
            return;
        }

        if (user.getPerTxLimit() != null && user.getPerTxLimit().compareTo(BigDecimal.ZERO) > 0
                && amount.compareTo(user.getPerTxLimit()) > 0) {
            throw new ApiException("Per-transaction limit exceeded");
        }
    }
}

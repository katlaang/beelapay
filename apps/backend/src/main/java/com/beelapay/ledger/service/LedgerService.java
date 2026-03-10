package com.beelapay.ledger.service;

import com.beelapay.ledger.domain.LedgerEntry;
import com.beelapay.ledger.domain.LedgerEntryType;
import com.beelapay.ledger.repo.LedgerEntryRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerService(LedgerEntryRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    public void post(UUID walletId, LedgerEntryType type, BigDecimal amount, String reference, String channel) {
        LedgerEntry entry = new LedgerEntry();
        entry.setId(UUID.randomUUID());
        entry.setWalletId(walletId);
        entry.setEntryType(type);
        entry.setAmount(amount);
        entry.setReference(reference);
        entry.setChannel(channel);
        entry.setCreatedAt(Instant.now());
        ledgerEntryRepository.save(entry);
    }
}

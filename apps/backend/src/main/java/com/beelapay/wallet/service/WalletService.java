package com.beelapay.wallet.service;

import com.beelapay.common.ApiException;
import com.beelapay.wallet.domain.Wallet;
import com.beelapay.wallet.domain.WalletStatus;
import com.beelapay.wallet.repo.WalletRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet createWallet(UUID userId, String currencyCode) {
        return walletRepository.findByUserIdAndCurrencyCode(userId, currencyCode.toUpperCase())
                .orElseGet(() -> {
                    Wallet wallet = new Wallet();
                    wallet.setId(UUID.randomUUID());
                    wallet.setUserId(userId);
                    wallet.setCurrencyCode(currencyCode.toUpperCase());
                    wallet.setBalance(BigDecimal.ZERO);
                    wallet.setStatus(WalletStatus.ACTIVE);
                    wallet.setCreatedAt(Instant.now());
                    return walletRepository.save(wallet);
                });
    }

    public List<Wallet> listWallets(UUID userId) {
        return walletRepository.findByUserId(userId);
    }

    @Transactional
    public Wallet lockWallet(UUID walletId) {
        return walletRepository.findByIdForUpdate(walletId)
                .orElseThrow(() -> new ApiException("Wallet not found: " + walletId));
    }

    @Transactional
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}

package com.beelapay.identity.repo;

import com.beelapay.identity.domain.UserAccount;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByPhoneNumber(String phoneNumber);
    List<UserAccount> findByParentUserId(UUID parentUserId);
}

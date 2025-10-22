package com.dev.shack.banking.api.repository;

import com.dev.shack.banking.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {


}

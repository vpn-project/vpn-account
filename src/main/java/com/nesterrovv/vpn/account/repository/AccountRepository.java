package com.nesterrovv.vpn.account.repository;

import com.nesterrovv.vpn.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}


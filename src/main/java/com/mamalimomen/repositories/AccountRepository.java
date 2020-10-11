package com.mamalimomen.repositories;

import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.domains.Account;
import com.mamalimomen.services.dtos.AccountDTO;

import java.util.Optional;

public interface AccountRepository extends BaseRepository<Long, Account, AccountDTO> {

    Optional<Account> findOneActiveAccountByUsername(String username);

    Optional<Account> findOneAccountByUsername(String username);
}

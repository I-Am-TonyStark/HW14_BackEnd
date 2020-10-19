package com.mamalimomen.repositories;

import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.domains.Account;

import java.util.Optional;

public interface AccountRepository extends BaseRepository<Long, Account> {

    Optional<Account> findOneAccountByUsername(String username);

    Optional<Account> findOneActiveAccountByUsername(String username);
}

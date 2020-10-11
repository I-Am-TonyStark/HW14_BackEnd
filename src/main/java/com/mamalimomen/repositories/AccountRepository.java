package com.mamalimomen.repositories;

import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.domains.Account;
import com.mamalimomen.dtos.AccountSearchDTO;

import java.util.Optional;

public interface AccountRepository extends BaseRepository<Long, Account, AccountSearchDTO> {

    Optional<Account> findOneActiveAccountByUsername(String username);

    Optional<Account> findOneAccountByUsername(String username);
}

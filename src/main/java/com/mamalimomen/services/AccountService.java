package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Account;
import com.mamalimomen.dtos.AccountDTO;
import com.mamalimomen.dtos.AccountSearchDTO;

import java.util.List;
import java.util.Optional;

public interface AccountService extends BaseService<Long, Account, AccountSearchDTO> {
    boolean createNewAccount(AccountDTO dto);

    Optional<AccountDTO> retrieveExistActiveAccount(AccountDTO dto);

    boolean updateExistActiveAccount(AccountDTO dto);

    boolean deleteExistActiveAccount(AccountDTO dto);

    List<AccountDTO> accountAdvancedSearch();
}

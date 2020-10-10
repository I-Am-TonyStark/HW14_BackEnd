package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Account;

import java.util.Optional;

public interface AccountService extends BaseService<Long, Account> {
    Optional<Account> createNewAccount();

    Optional<Account> retrieveExistAccount();

    Optional<Account> retrieveExistActiveAccount();

    String updateExistActiveAccountPassword();

    String updateExistActiveAccountInformation();

    String deleteExistActiveAccount();
}

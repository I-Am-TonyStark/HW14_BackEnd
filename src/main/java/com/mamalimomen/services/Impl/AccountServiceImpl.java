package com.mamalimomen.services.Impl;

import com.mamalimomen.base.services.impl.BaseServiceImpl;
import com.mamalimomen.domains.Account;
import com.mamalimomen.repositories.AccountRepository;
import com.mamalimomen.repositories.impl.AccountRepositoryImpl;
import com.mamalimomen.services.AccountService;

import javax.persistence.EntityManager;
import java.util.Optional;

public class AccountServiceImpl extends BaseServiceImpl<Long, Account, AccountRepository> implements AccountService {
    public AccountServiceImpl(EntityManager em) {
        super(new AccountRepositoryImpl(em));
    }

    @Override
    public Optional<Account> createNewAccount() {
        return Optional.empty();
    }

    @Override
    public Optional<Account> retrieveExistAccount() {
        return Optional.empty();
    }

    @Override
    public Optional<Account> retrieveExistActiveAccount() {
        return Optional.empty();
    }

    @Override
    public String updateExistActiveAccountPassword() {
        return null;
    }

    @Override
    public String updateExistActiveAccountInformation() {
        return null;
    }

    @Override
    public String deleteExistActiveAccount() {
        return null;
    }
}

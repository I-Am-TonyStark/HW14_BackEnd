package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Account;
import com.mamalimomen.repositories.AccountRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class AccountRepositoryImpl extends BaseRepositoryImpl<Long, Account> implements AccountRepository {
    public AccountRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<Account> findOneAccountByUsername(String username) {
        return findOneByNamedQuery("Account.findOneByUsername", Account.class, username);
    }

    @Override
    public Optional<Account> findOneActiveAccountByUsername(String username) {
        return findOneByNamedQuery("Account.findOneActiveByUsername", Account.class, username);
    }
}

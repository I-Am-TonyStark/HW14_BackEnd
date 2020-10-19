package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Account;

import java.util.Optional;

public interface AccountService extends BaseService<Long, Account> {
    Optional<Account> createNewAccount();

    Optional<Account> retrieveExistActiveAccount();

    String updateExistActiveAccountPassword(Account account);

    String updateExistActiveAccountInformation(Account account);

    String addExistActiveAccountAPost(Account account);

    String removeExistActiveAccountAPost(Account account);

    String addExistActiveAccountAFollowing(Account followerAccount, Account followingAccount);

    String removeExistActiveAccountAFollowing(Account followerAccount);

    String deleteExistActiveAccount(Account account);
}

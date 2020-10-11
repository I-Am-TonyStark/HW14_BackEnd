package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.Impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Account;
import com.mamalimomen.repositories.AccountRepository;
import com.mamalimomen.services.dtos.AccountDTO;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl extends BaseRepositoryImpl<Long, Account, AccountDTO> implements AccountRepository {
    public AccountRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    protected void setClausesForAdvancedSearch(AccountDTO dto, List<Predicate> predicates, CriteriaBuilder cb, Root<Account> root) {
        setUsername(dto.getUsername(), predicates, cb, root);
        setOwnerFirstName(dto.getOwnerFirstName(), predicates, cb, root);
        setOwnerLastName(dto.getOwnerLastName(), predicates, cb, root);
    }

    private void setOwnerFirstName(String ownerFirstName, List<Predicate> predicates, CriteriaBuilder cb, Root<Account> root) {
        if (ownerFirstName != null && !ownerFirstName.isEmpty()) {
            predicates.add(
                    cb.like(root.get("user.firstName"), "%" + ownerFirstName.trim() + "%"));
        }
    }

    private void setUsername(String username, List<Predicate> predicates, CriteriaBuilder cb, Root<Account> root) {
        if (username != null && !username.isEmpty()) {
            predicates.add(
                    cb.like(root.get("user.username"), "%" + username.trim() + "%"));
        }
    }

    private void setOwnerLastName(String ownerLastName, List<Predicate> predicates, CriteriaBuilder cb, Root<Account> root) {
        if (ownerLastName != null && !ownerLastName.isEmpty()) {
            predicates.add(
                    cb.like(root.get("user.lastName"), "%" + ownerLastName.trim() + "%"));
        }
    }

    @Override
    public Optional<Account> findOneActiveAccountByUsername(String username) {
        return findOneByNamedQuery("Account.findOneActiveByUsername", Account.class, username);
    }

    @Override
    public Optional<Account> findOneAccountByUsername(String username) {
        return findOneByNamedQuery("Account.findOneByUsername", Account.class, username);
    }
}

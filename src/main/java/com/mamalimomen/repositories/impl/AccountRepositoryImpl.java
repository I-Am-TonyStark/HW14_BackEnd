package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.User;
import com.mamalimomen.dtos.AccountSearchDTO;
import com.mamalimomen.repositories.AccountRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl extends BaseRepositoryImpl<Long, Account, AccountSearchDTO> implements AccountRepository {
    public AccountRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    protected void setClausesForAdvancedSearch(AccountSearchDTO dto, List<Predicate> predicates, CriteriaBuilder cb, Root<Account> root) {
        setUsername(dto.getUsername(), predicates, cb, root);
        setOwnerFirstName(dto.getOwnerFirstName(), predicates, cb, root);
        setOwnerLastName(dto.getOwnerLastName(), predicates, cb, root);
    }

    private void setOwnerFirstName(List<Predicate> predicates, CriteriaBuilder cb, Root<Account> root, String ownerFirstName) {
        if (ownerFirstName != null && !ownerFirstName.isEmpty()) {

            Subquery<String> sq = cb.createQuery(String.class).subquery(String.class);
            Root<User> userRoot = sq.from(User.class);

            predicates.add(
                    cb.in(root.get("user")).value(
                            sq.select(userRoot.get("firstName"))
                                    .where(cb.equal(userRoot.get("firstName"), ownerFirstName))));
        }
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

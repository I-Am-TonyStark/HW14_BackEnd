package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.Impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Post;
import com.mamalimomen.repositories.PostRepository;
import com.mamalimomen.services.dtos.AccountDTO;
import com.mamalimomen.services.dtos.PostDTO;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class PostRepositoryImpl extends BaseRepositoryImpl<Long, Post, PostDTO> implements PostRepository {

    public PostRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    protected void setClausesForAdvancedSearch(PostDTO dto, List<Predicate> predicates, CriteriaBuilder cb, Root<Post> root) {
        setText(dto.getText(), predicates, cb, root);
        setFromDate(dto.getFromDate(), predicates, cb, root);
        setTillDate(dto.getTillDate(), predicates, cb, root);
        setOwnerAccountUsername(dto.getAccountUsername(), predicates, cb, root);
    }

    private void setText(String text, List<Predicate> predicates, CriteriaBuilder cb, Root<Post> root) {
        if (text != null && !text.isEmpty()) {
            predicates.add(
                    cb.like(root.get("text"), "%" + text.trim() + "%"));
        }
    }

    private void setFromDate(Date fromDate, List<Predicate> predicates, CriteriaBuilder cb, Root<Post> root) {
        if (fromDate != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("insertDate"), fromDate));
        }
    }

    private void setTillDate(Date tillDate, List<Predicate> predicates, CriteriaBuilder cb, Root<Post> root) {
        if (tillDate != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("insertDate"), tillDate));
        }
    }

    private void setOwnerAccountUsername(String ownerAccountUsername, List<Predicate> predicates, CriteriaBuilder cb, Root<Post> root) {
        if (ownerAccountUsername != null && !ownerAccountUsername.isEmpty()) {
            predicates.add(
                    cb.like(root.get("account.user.username"), "%" + ownerAccountUsername.trim() + "%"));
        }
    }

    @Override
    public List<Post> findAllPosts() {
        return findAllByNamedQuery("Post.findAll", Post.class);
    }

    @Override
    public List<Post> findManyPostsByAccountUsername(String username) {
        return findManyByNamedQuery("Post.findManyByAccountUsername", Post.class, username);
    }
}

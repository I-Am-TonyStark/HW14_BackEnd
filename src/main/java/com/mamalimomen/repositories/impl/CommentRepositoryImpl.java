package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Comment;
import com.mamalimomen.repositories.CommentRepository;

import javax.persistence.EntityManager;

public class CommentRepositoryImpl extends BaseRepositoryImpl<Long, Comment> implements CommentRepository {
    public CommentRepositoryImpl(EntityManager em) {
        super(em);
    }
}

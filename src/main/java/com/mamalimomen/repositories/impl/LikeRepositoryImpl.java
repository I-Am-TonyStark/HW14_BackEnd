package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Like;
import com.mamalimomen.repositories.LikeRepository;

import javax.persistence.EntityManager;

public class LikeRepositoryImpl extends BaseRepositoryImpl<Long, Like> implements LikeRepository {
    public LikeRepositoryImpl(EntityManager em) {
        super(em);
    }
}

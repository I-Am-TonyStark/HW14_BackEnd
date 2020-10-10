package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.Impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Post;
import com.mamalimomen.repositories.PostRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class PostRepositoryImpl extends BaseRepositoryImpl<Long, Post> implements PostRepository {

    public PostRepositoryImpl(EntityManager em) {
        super(em);
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

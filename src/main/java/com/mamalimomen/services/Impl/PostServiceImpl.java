package com.mamalimomen.services.Impl;

import com.mamalimomen.base.services.impl.BaseServiceImpl;
import com.mamalimomen.domains.Post;
import com.mamalimomen.repositories.PostRepository;
import com.mamalimomen.repositories.impl.PostRepositoryImpl;
import com.mamalimomen.services.PostService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PostServiceImpl extends BaseServiceImpl<Long, Post, PostRepository> implements PostService {
    public PostServiceImpl(EntityManager em) {
        super(new PostRepositoryImpl(em));
    }

    @Override
    public Optional<Post> createNewPost() {
        return Optional.empty();
    }

    @Override
    public Optional<Post> retrieveExistPost() {
        return Optional.empty();
    }

    @Override
    public List<Post> retrieveManyExistPosts() {
        return null;
    }

    @Override
    public List<Post> retrieveAllExistPosts() {
        return null;
    }

    @Override
    public String updateExistPost() {
        return null;
    }

    @Override
    public String deleteExistPost() {
        return null;
    }
}

package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Post;

import java.util.List;
import java.util.Optional;

public interface PostService extends BaseService<Long, Post> {
    Optional<Post> createNewPost();

    Optional<Post> retrieveExistPost();

    List<Post> retrieveManyExistPosts();

    List<Post> retrieveAllExistPosts();

    String updateExistPost();

    String deleteExistPost();
}

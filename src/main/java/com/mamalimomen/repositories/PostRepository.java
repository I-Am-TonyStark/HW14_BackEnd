package com.mamalimomen.repositories;

import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.domains.Post;

import java.util.List;

public interface PostRepository extends BaseRepository<Long, Post> {
    List<Post> findAllPosts();
}

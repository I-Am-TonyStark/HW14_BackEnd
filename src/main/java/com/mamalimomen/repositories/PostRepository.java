package com.mamalimomen.repositories;

import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.domains.Post;
import com.mamalimomen.services.dtos.PostDTO;

import java.util.List;

public interface PostRepository extends BaseRepository<Long, Post, PostDTO> {
    List<Post> findAllPosts();

    List<Post> findManyPostsByAccountUsername(String username);
}

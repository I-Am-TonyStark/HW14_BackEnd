package com.mamalimomen.repositories;

import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.domains.Post;
import com.mamalimomen.dtos.PostDTO;
import com.mamalimomen.dtos.PostSearchDTO;

import java.util.List;
import java.util.function.Function;

public interface PostRepository extends BaseRepository<Long, Post, PostSearchDTO> {
    List<Post> findAllPosts();

    List<PostDTO> findAllPosts(Function<Post, PostDTO> f);

    List<Post> findManyPostsByAccountUsername(String username);

    List<PostDTO> findManyPostsByAccountUsername(String username, Function<Post, PostDTO> f);
}

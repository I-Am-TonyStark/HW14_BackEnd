package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Post;
import com.mamalimomen.dtos.PostDTO;
import com.mamalimomen.dtos.PostSearchDTO;

import java.util.List;

public interface PostService extends BaseService<Long, Post, PostSearchDTO> {
    boolean createNewPost(PostDTO dto);

    List<PostDTO> retrieveManyExistPosts(PostDTO dto);

    List<PostDTO> retrieveAllExistPosts();

    boolean updateExistPost(PostDTO dto);

    boolean deleteExistPost(PostDTO dto);

    List<PostDTO> postAdvancedSearch();
}

package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Post;

import java.util.List;
import java.util.Optional;

public interface PostService extends BaseService<Long, Post> {
    Optional<Post> createNewPost();

    List<Post> retrieveAllExistPostsOrderByLike();

    String updateExistPost(Post post);

    String addExistPostALike(Post liked, Account liker);

    String addExistPostAComment(Post commented, Account commenter);
}

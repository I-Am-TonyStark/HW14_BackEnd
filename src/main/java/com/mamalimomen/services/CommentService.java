package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Comment;
import com.mamalimomen.domains.Post;

import java.util.Optional;

public interface CommentService extends BaseService<Long, Comment> {
    Optional<Comment> createNewComment(Account writer);
}

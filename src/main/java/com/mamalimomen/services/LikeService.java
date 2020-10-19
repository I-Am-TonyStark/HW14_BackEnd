package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Like;
import com.mamalimomen.domains.Post;

import java.util.Optional;

public interface LikeService extends BaseService<Long, Like> {

    Optional<Like> createNewLike(Account liker, Post liked);
}

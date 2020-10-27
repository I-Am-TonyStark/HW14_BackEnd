package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.impl.BaseRepositoryImpl;
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
        return findAllByNativeQuery("select p.* " +
                "from hw14_one.tbl_post p " +
                "left join hw14_one.tbl_like l on p.id = l.fk_post " +
                "where p.is_deleted = false " +
                "group by l.fk_post " +
                "order by count(l.fk_post) desc", Post.class);
    }
}

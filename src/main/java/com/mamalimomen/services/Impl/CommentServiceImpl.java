package com.mamalimomen.services.Impl;

import com.mamalimomen.base.controllers.guis.DialogProvider;
import com.mamalimomen.base.controllers.utilities.SingletonScanner;
import com.mamalimomen.base.services.impl.BaseServiceImpl;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Comment;
import com.mamalimomen.repositories.CommentRepository;
import com.mamalimomen.repositories.impl.CommentRepositoryImpl;
import com.mamalimomen.services.CommentService;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

public class CommentServiceImpl extends BaseServiceImpl<Long, Comment, CommentRepository> implements CommentService {
    public CommentServiceImpl(EntityManager em) {
        super(new CommentRepositoryImpl(em));
    }

    @Override
    public Optional<Comment> createNewComment(Account writer) {
        Comment comment = new Comment();
        
        DialogProvider.createAndShowTerminalMessage("%s", "Message: ");
        String message = SingletonScanner.readParagraph();
        if (message.equalsIgnoreCase("esc")) {
            return Optional.empty();
        }
        comment.setMessage(message);

        comment.setCreateDate(new Date(System.currentTimeMillis()));

        comment.setWriter(writer);

        return Optional.of(comment);
    }
}

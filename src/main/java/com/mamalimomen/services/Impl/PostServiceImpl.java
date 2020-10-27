package com.mamalimomen.services.Impl;

import com.mamalimomen.base.controllers.guis.DialogProvider;
import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.base.controllers.utilities.SingletonScanner;
import com.mamalimomen.base.services.impl.BaseServiceImpl;
import com.mamalimomen.controllers.utilities.AppManager;
import com.mamalimomen.controllers.utilities.Services;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Comment;
import com.mamalimomen.domains.Like;
import com.mamalimomen.domains.Post;
import com.mamalimomen.repositories.PostRepository;
import com.mamalimomen.repositories.impl.PostRepositoryImpl;
import com.mamalimomen.services.CommentService;
import com.mamalimomen.services.LikeService;
import com.mamalimomen.services.PostService;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PostServiceImpl extends BaseServiceImpl<Long, Post, PostRepository> implements PostService {
    public PostServiceImpl(EntityManager em) {
        super(new PostRepositoryImpl(em));
    }

    @Override
    public Optional<Post> createNewPost() {
        Post post = new Post();
        while (true) {
            try {
                DialogProvider.createAndShowTerminalMessage("%s", "Text: ");
                String text = SingletonScanner.readParagraph();
                if (text.equalsIgnoreCase("esc")) {
                    break;
                }
                post.setText(text);

                DialogProvider.createAndShowTerminalMessage("%s", "Image Path: ");
                String imagePath = SingletonScanner.readLine();
                if (!imagePath.equalsIgnoreCase("pass")) {
                    post.setImagePath(imagePath);
                }

                post.setCreateDate(new Date(System.currentTimeMillis()));

                return Optional.of(post);
            } catch (InValidDataException e) {
                DialogProvider.createAndShowTerminalMessage("%s %s%s%n%n", "Wrong entered data format for", e.getMessage(), "!");
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Post> retrieveAllExistPostsOrderByLike() {
        return repository.findAllPosts();
    }

    @Override
    public String updateExistPost(Post post) {
        while (true) {
            try {
                DialogProvider.createAndShowTerminalMessage("%s (old = %s): ", "New Text", post.getText());
                String newText = SingletonScanner.readParagraph();
                if (newText.equalsIgnoreCase("esc")) {
                    break;
                }
                if (!newText.equalsIgnoreCase("pass")) {
                    post.setText(newText);
                }

                DialogProvider.createAndShowTerminalMessage("%s (old = %s): ", "New Image path", post.getImagePath());
                String newImagePath = SingletonScanner.readLine();
                if (!newImagePath.equalsIgnoreCase("pass")) {
                    post.setImagePath(newImagePath);
                }

                if (repository.updateOne(post)) {
                    return "update selected post successfully!";
                } else {
                    return "can not update selected post!";
                }
            } catch (InValidDataException e) {
                DialogProvider.createAndShowTerminalMessage("%s %s%s%n%n", "Wrong entered data format for", e.getMessage(), "!");
            }
        }
        return "You Cancelled this operation!";
    }

    @Override
    public String addExistPostALike(Post liked, Account liker) {
        LikeService likeService = AppManager.getService(Services.LIKE_SERVICE);
        Optional<Like> oLike = likeService.createNewLike(liker);
        if (oLike.isPresent()) {
            liked.addLike(oLike.get());
            if (repository.updateOne(liked)) {
                return "Like selected post successfully!";
            }
        }
        return "can not Like selected post!";
    }

    @Override
    public String addExistPostAComment(Post commented, Account commenter) {
        CommentService commentService = AppManager.getService(Services.COMMENT_SERVICE);
        Optional<Comment> oComment = commentService.createNewComment(commenter);
        if (oComment.isPresent()) {
            commented.addComment(oComment.get());
            if (repository.updateOne(commented)) {
                return "Comment selected post successfully!";
            }
        }
        return "can not Comment selected post!";
    }
}

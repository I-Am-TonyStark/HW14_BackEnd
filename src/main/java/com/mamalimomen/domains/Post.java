package com.mamalimomen.domains;

import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.base.domains.BaseEntity;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Entity
@SelectBeforeUpdate
@Table(name = "tbl_post", catalog = "HW14_One", schema = "HW14_One")
@NamedQueries({
        @NamedQuery(
                name = "Post.findAll",
                query = "SELECT p FROM Post p LEFT JOIN p.likes l WHERE p.deleted = FALSE GROUP BY p.likes.size ORDER BY COUNT(p.likes.size) DESC")
})
public class Post extends BaseEntity implements Comparable<Post> {

    @Transient
    private static final long serialVersionUID = 6446817660773091639L;

    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;

    @Column(name = "image_path")
    private String imagePath = "";

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date", updatable = false, nullable = false)
    private Date createDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_post", updatable = false, nullable = false)
    private Set<Comment> comments = new TreeSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_post", updatable = false, nullable = false)
    private Set<Like> likes = new TreeSet<>();

    public String getText() {
        return text;
    }

    public void setText(String text) throws InValidDataException {
        if (text.length() < 20) {
            throw new InValidDataException("Text");
        }
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public void addComment(Comment comment) {
        this.getComments().add(comment);
    }

    public void addLike(Like like) {
        this.getLikes().add(like);
    }

    public String printComments() {
        return getComments().stream().map(Comment::toString).collect(Collectors.joining("\n"));
    }

    public String printLikes() {
        return getLikes().stream().map(Like::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return String.format("%s%n%s%n%s%nLikes: %d\tComments: %d%n", getImagePath(), getText(), getCreateDate(), getLikes().size(), getComments().size());
    }

    @Override
    public int compareTo(Post p) {
        return this.getCreateDate().compareTo(p.getCreateDate());
    }
}

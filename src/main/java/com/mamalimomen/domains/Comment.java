package com.mamalimomen.domains;

import com.mamalimomen.base.domains.BaseEntity;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@SelectBeforeUpdate
@Table(name = "tbl_comment", catalog = "HW14_One", schema = "HW14_One")
public final class Comment extends BaseEntity implements Comparable<Comment> {

    @Transient
    private static final long serialVersionUID = 2085881896603590594L;

    @Column(name = "message", columnDefinition = "text", updatable = false, nullable = false)
    private String message;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date", updatable = false, nullable = false)
    private Date createDate;

    @OneToOne
    @JoinColumn(name = "fk_writer_account", updatable = false, nullable = false)
    private Account writer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Account getWriter() {
        return writer;
    }

    public void setWriter(Account writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return String.format("%s:%n%s%n%s%n", getWriter().getUser().getUsername(),getMessage(),getCreateDate());
    }

    @Override
    public int compareTo(Comment c) {
        return this.getCreateDate().compareTo(c.getCreateDate());
    }
}

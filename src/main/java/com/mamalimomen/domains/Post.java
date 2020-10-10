package com.mamalimomen.domains;

import com.mamalimomen.base.domains.BaseEntity;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@SelectBeforeUpdate
@NamedQueries({
        @NamedQuery(
                name = "Post.findAll",
                query = "SELECT p FROM Post p"),
        @NamedQuery(
                name = "Post.findManyByAccountUsername",
                query = "SELECT p FROM Post p JOIN p.account a WHERE a.user.username = ?1")
})
public class Post extends BaseEntity<Long> implements Comparable<Post> {

    @Transient
    private static final long serialVersionUID = 6446817660773091639L;

    @Transient
    private static long count = 1;

    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;

    @Temporal(TemporalType.DATE)
    @Column(name = "insert_date", updatable = false, nullable = false)
    private Date insertDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_account", nullable = false, updatable = false)
    private Account account;

    public Post() {
        this.setId(count);
        count++;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int compareTo(Post p) {
        return this.getInsertDate().compareTo(p.getInsertDate());
    }

    @Override
    public String toString() {
        return String.format("Account Number: %s%nOwner Customer: %s%nBalance amount: %,+011.2f Rials%n", );
    }

    public void printCompleteInformation() {
        System.out.printf("Account Number: %s%nIs Active: %b%nBalance amount: %,+011.2f Rials%nOwner: %s%nOpen Date: %s%nCredit Card: %s%n",
                );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Post post = (Post) obj;
        return this.hashCode() == post.hashCode();
    }
}

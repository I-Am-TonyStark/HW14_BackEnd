package com.mamalimomen.domains;

import com.mamalimomen.base.domains.BaseEntity;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@SelectBeforeUpdate
@Table(name = "tbl_like", catalog = "HW14_One", schema = "HW14_One")
public final class Like extends BaseEntity implements Comparable<Like> {

    @Transient
    private static final long serialVersionUID = 6471051167059497813L;

    @OneToOne(optional = false)
    @JoinColumn(name = "liker_account_id", updatable = false, nullable = false)
    private Account liker;

    public Account getLiker() {
        return liker;
    }

    public void setLiker(Account liker) {
        this.liker = liker;
    }

    @Override
    public String toString() {
        return String.format("%s%n", getLiker().getUser().getUsername());
    }

    @Override
    public int compareTo(Like l) {
        return this.getId().compareTo(l.getId());
    }
}

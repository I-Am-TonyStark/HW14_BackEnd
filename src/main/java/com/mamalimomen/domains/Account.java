package com.mamalimomen.domains;

import com.mamalimomen.base.domains.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "tbl_account", catalog = "HW13_One", schema = "HW13_One")
@NamedQueries({
        @NamedQuery(
                name = "Account.findOneByUsername",
                query = "SELECT a FROM Account a WHERE a.user.username = ?1"),
        @NamedQuery(
                name = "Account.findOneActiveByUsername",
                query = "SELECT a FROM Account a WHERE a.deleted = FALSE AND a.user.username = ?1")
})
public final class Account extends BaseEntity implements Comparable<Account> {

    @Transient
    private static final long serialVersionUID = 8296892016184394238L;

    @Embedded
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(Account a) {
        return this.getId().compareTo(a.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;
        return this.hashCode() == account.hashCode();
    }

    @Override
    public int hashCode() {
        return this.getId().intValue();
    }
}

package com.mamalimomen.domains;

import com.mamalimomen.base.domains.BaseEntity;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@SelectBeforeUpdate
@Table(name = "tbl_account", catalog = "HW13_One", schema = "HW13_One",
uniqueConstraints = {@UniqueConstraint(name = "unique_account_username",columnNames = {"id","username"})})
@NamedQueries({
        @NamedQuery(
                name = "Account.findByUsername",
                query = "SELECT a FROM Account a WHERE a.user.username = ?1"),
        @NamedQuery(
                name = "Account.findActiveByUsername",
                query = "SELECT a FROM Account a WHERE a.deleted = FALSE AND a.user.username = ?1")
})
public final class Account extends BaseEntity<Long> implements Comparable<Account> {

    @Transient
    private static final long serialVersionUID = 8296892016184394238L;

    @Transient
    private static long count = 1;

    @Embedded
    private User user;

    public Account() {
        this.setId(count);
        count++;
    }

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
        Account account = (Account) obj;
        return this.hashCode() == account.hashCode();
    }
}

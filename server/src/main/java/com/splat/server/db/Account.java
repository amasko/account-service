package com.splat.server.db;

import javax.persistence.*;

/**
 * Created by Alex on 04.09.2014.
 */

@Entity
@Table(name = "account")
public class Account {

    public Account() {
    }

    public Account(Integer id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    private boolean modified = false;

    @Id
    //@GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "AMOUNT", nullable = false)
    private Long amount;

    /**
     * Method will help optimize storing data into database.
     * If modified = false there's no need to store entity from cache to database
     * @param modified is account changed between cache and database synchronization
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean isModified() {
        return modified;
    }

    public Integer getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                ", modified: " + modified +
                '}';
    }
}

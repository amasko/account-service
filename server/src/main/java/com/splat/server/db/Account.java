package com.splat.server.db;

import javax.persistence.*;

/**
 * Created by Alex on 04.09.2014.
 */
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "AMOUNT", nullable = false)
    private String amount;

    public Long getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

package com.nesterrovv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "account_linked_account", schema = "public")
public class AccountLinkedAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "main_account_id")
    private Account mainAccountId;

    @ManyToOne
    @JoinColumn(name = "linked_account_id")
    private Account linkedAccountId;

    public AccountLinkedAccount() {}

    public AccountLinkedAccount(Long id, Account mainAccountId, Account linkedAccountId) {
        this.id = id;
        this.mainAccountId = mainAccountId;
        this.linkedAccountId = linkedAccountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getMainAccountId() {
        return mainAccountId;
    }

    public void setMainAccountId(Account mainAccountId) {
        this.mainAccountId = mainAccountId;
    }

    public Account getLinkedAccountId() {
        return linkedAccountId;
    }

    public void setLinkedAccountId(Account linkedAccountId) {
        this.linkedAccountId = linkedAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountLinkedAccount that = (AccountLinkedAccount) o;
        return Objects.equals(id, that.id)
            && Objects.equals(mainAccountId, that.mainAccountId)
            && Objects.equals(linkedAccountId, that.linkedAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mainAccountId, linkedAccountId);
    }

}

package com.nesterrovv.vpn.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "account", schema = "public")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@OneToOne
    //@JoinColumn(name = "user_id")
    //private User user; // TODO add after merging auth module
    private String username;
    private boolean isMainAccount;
    @ManyToMany
    @JoinTable(
        name = "account_linked_account",
        joinColumns = @JoinColumn(name = "main_account_id"),
        inverseJoinColumns = @JoinColumn(name = "linked_account_id")
    )
    private Set<Account> linkedAccounts;

    public Account() {}

    public Account(String username, boolean isMainAccount, Set<Account> linkedAccounts) {
        this.username = username;
        this.isMainAccount = isMainAccount;
        this.linkedAccounts = linkedAccounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMainAccount() {
        return isMainAccount;
    }

    public void setMainAccount(boolean mainAccount) {
        isMainAccount = mainAccount;
    }

    public Set<Account> getLinkedAccounts() {
        return linkedAccounts;
    }

    public void setLinkedAccounts(Set<Account> linkedAccounts) {
        this.linkedAccounts = linkedAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return isMainAccount == account.isMainAccount
            && Objects.equals(id, account.id)
            && Objects.equals(username, account.username)
            && Objects.equals(linkedAccounts, account.linkedAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, isMainAccount, linkedAccounts);
    }

}

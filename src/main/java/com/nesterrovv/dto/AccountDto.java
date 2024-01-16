package com.nesterrovv.dto;

import com.nesterrovv.entity.Account;
import java.util.Objects;
import java.util.Set;

public class AccountDto {

    private String username;
    private boolean isMainAccount;
    private Set<Account> linkedAccounts;

    public AccountDto() {}

    public AccountDto(String username, boolean isMainAccount, Set<Account> linkedAccounts) {
        this.username = username;
        this.isMainAccount = isMainAccount;
        this.linkedAccounts = linkedAccounts;
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
        AccountDto that = (AccountDto) o;
        return isMainAccount == that.isMainAccount
            && Objects.equals(username, that.username)
            && Objects.equals(linkedAccounts, that.linkedAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, isMainAccount, linkedAccounts);
    }

}

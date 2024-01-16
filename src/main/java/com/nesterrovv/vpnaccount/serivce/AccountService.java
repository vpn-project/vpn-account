package com.nesterrovv.vpnaccount.serivce;

import com.nesterrovv.vpnaccount.entity.Account;
import com.nesterrovv.vpnaccount.repository.AccountRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.repository = accountRepository;
    }

    public Account createAccount(String username, boolean isMainAccount, Set<Account> linkedAccounts) {
        Account account = new Account(username, isMainAccount, linkedAccounts);
        return save(account);
    }

    public Account findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String addLinkedAccount(Long id, Account linkedAccount) {
        Account account = this.findById(id);
        if (account != null) {
            // Initialize linkedAccounts if it's null
            if (account.getLinkedAccounts() == null) {
                account.setLinkedAccounts(new HashSet<>());
            }

            prepareLinkedAccount(linkedAccount);
            Set<Account> linkedAccounts = account.getLinkedAccounts();
            linkedAccounts.add(linkedAccount);
            save(linkedAccount);
            save(account);
            return "Account added to set of linked accounts.";
        } else {
            return "Cannot link the accounts. Main account not found.";
        }
    }

    public String removeLinkedAccount(Long id, Account linkedAccount) {
        Account account = this.findById(id);
        if (account != null) {
            Set<Account> linkedAccounts = account.getLinkedAccounts();
            if (linkedAccounts != null) { // Add null check here
                Iterator<Account> iterator = linkedAccounts.iterator();
                while (iterator.hasNext()) {
                    Account linked = iterator.next();
                    if (linked.getUsername().equals(linkedAccount.getUsername())) {
                        iterator.remove();
                        save(account);
                        save(linkedAccount);
                        return "Account removed from linked.";
                    }
                }
            }
            return "This account is not linked with given. Nothing removed.";
        } else {
            return "Main account not found. Impossible to unlink.";
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Account save(Account account) {
        return repository.save(account);
    }

    private void prepareLinkedAccount(Account account) {
        account.setMainAccount(false);
        account.setLinkedAccounts(Collections.emptySet());
    }

}

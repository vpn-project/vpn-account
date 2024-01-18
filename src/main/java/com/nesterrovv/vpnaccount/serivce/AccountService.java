package com.nesterrovv.vpnaccount.serivce;

import com.nesterrovv.vpnaccount.entity.Account;
import com.nesterrovv.vpnaccount.repository.AccountRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class AccountService {

    private final AccountRepository repository;

    private final Scheduler scheduler;

    @Autowired
    public AccountService(AccountRepository accountRepository, @Qualifier("jdbcScheduler") Scheduler scheduler) {
        this.repository = accountRepository;
        this.scheduler = scheduler;
    }

    public Mono<Account> createAccount(String username, boolean isMainAccount, Set<Account> linkedAccounts) {
        return async(() -> new Account(username, isMainAccount, linkedAccounts))
            .publishOn(Schedulers.boundedElastic())
            .mapNotNull(account -> save(account).block());
    }

    public Mono<Optional<Account>> findById(Long id) {
        return async(() -> repository.findById(id));
    }

    public Mono<String> addLinkedAccount(Long id, Account linkedAccount) {
        return this.findById(id).publishOn(Schedulers.boundedElastic()).map(accountOptional -> {
                if (accountOptional.isPresent()) {
                    // Initialize linkedAccounts if it's null
                    var account = accountOptional.get();
                    if (account.getLinkedAccounts() == null) {
                        account.setLinkedAccounts(new HashSet<>());
                    }

                    prepareLinkedAccount(linkedAccount);
                    Set<Account> linkedAccounts = account.getLinkedAccounts();
                    linkedAccounts.add(linkedAccount);
                    save(linkedAccount).block();
                    save(account).block();
                    return "Account added to set of linked accounts.";
                } else {
                    return "Cannot link the accounts. Main account not found.";
                }
            }
        );
    }

    public Mono<String> removeLinkedAccount(Long id, Account linkedAccount) {
        return this.findById(id).publishOn(Schedulers.boundedElastic()).map(accountOptional -> {
            if (accountOptional.isEmpty()) {
                return "Main account not found. Impossible to unlink.";
            }
            var account = accountOptional.get();
            Set<Account> linkedAccounts = account.getLinkedAccounts();
            if (linkedAccounts != null) { // Add null check here
                Iterator<Account> iterator = linkedAccounts.iterator();
                while (iterator.hasNext()) {
                    Account linked = iterator.next();
                    if (linked.getUsername().equals(linkedAccount.getUsername())) {
                        iterator.remove();
                        save(account).subscribe();
                        save(linkedAccount).subscribe();
                        return "Account removed from linked.";
                    }
                }
            }
            return "This account is not linked with given. Nothing removed.";
        });
    }

    public Mono<Void> delete(Long id) {
        return async(() -> {
            repository.deleteById(id);
            return null;
        });
    }

    public Mono<Account> save(Account account) {
        return async(() -> repository.save(account));
    }

    private void prepareLinkedAccount(Account account) {
        account.setMainAccount(false);
        account.setLinkedAccounts(Collections.emptySet());
    }

    private <T> Mono<T> async(Callable<T> callable) {
        return Mono.fromCallable(callable).publishOn(scheduler);
    }
}

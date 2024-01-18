package com.nesterrovv.vpnaccount.controller;

import com.nesterrovv.vpnaccount.entity.Account;
import com.nesterrovv.vpnaccount.serivce.AccountService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/subscription/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Mono<ResponseEntity<Account>> createAccount(
        @RequestParam String username,
        @RequestParam boolean isMainAccount) {
        return accountService.createAccount(username, isMainAccount, Collections.emptySet())
            .map(account -> new ResponseEntity<>(account, HttpStatus.CREATED));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Account>> getAccountById(@PathVariable Long id) {
        return accountService.findById(id).map(accountOptional -> {
            if (accountOptional.isPresent()) {
                return new ResponseEntity<>(accountOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
        });
    }

    @PostMapping("/{id}/add-linked-account")
    public Mono<ResponseEntity<String>> addLinkedAccount(@PathVariable Long id,
                                                         @RequestBody Account linkedAccount) {
        return accountService.addLinkedAccount(id, linkedAccount)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK));
    }

    @PostMapping("/{id}/remove-linked-account")
    public Mono<ResponseEntity<String>> removeLinkedAccount(@PathVariable Long id,
                                                            @RequestBody Account linkedAccount) {
        return accountService.removeLinkedAccount(id, linkedAccount)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable Long id) {
        return accountService.delete(id).then(Mono.fromCallable(() 
                                -> new ResponseEntity<>(HttpStatus.OK)));
    }

}

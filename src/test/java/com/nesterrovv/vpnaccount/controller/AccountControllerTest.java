package com.nesterrovv.vpnaccount.controller;

import com.nesterrovv.vpnaccount.entity.Account;
import com.nesterrovv.vpnaccount.serivce.AccountService;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(AccountController.class)
@ExtendWith(SpringExtension.class)
@Disabled    
class AccountControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        var testAccount = new Account("testUser", true, new HashSet<>());
        when(accountService.createAccount(anyString(), anyBoolean(), anySet())).thenReturn(Mono.just(testAccount));

        webTestClient.post()
            .uri("/subscription/accounts?username={username}&isMainAccount={isMainAccount}", testAccount.getUsername(), testAccount.isMainAccount())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.username").isEqualTo(testAccount.getUsername());
    }
}

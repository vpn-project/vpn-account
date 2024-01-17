package com.nesterrovv.vpnaccount.controller;

import com.nesterrovv.vpnaccount.entity.Account;
import com.nesterrovv.vpnaccount.serivce.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebFluxTest(AccountController.class)
@ExtendWith(SpringExtension.class)
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

package com.nesterrovv.vpnaccount.controller;

import com.nesterrovv.vpnaccount.entity.Account;
import com.nesterrovv.vpnaccount.serivce.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testCreateAccount() {
        when(accountService.createAccount(anyString(), anyBoolean(), anySet())).thenReturn(new Account());
        ResponseEntity<Account> responseEntity = accountController.createAccount("testUser", true);
        verify(accountService).createAccount(eq("testUser"), eq(true), eq(Collections.emptySet()));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testGetAccountById() {
        when(accountService.findById(1L)).thenReturn(new Account());
        ResponseEntity<Account> responseEntity = accountController.getAccountById(1L);
        verify(accountService).findById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testGetAccountByIdNotFound() {
        when(accountService.findById(1L)).thenReturn(null);
        ResponseEntity<Account> responseEntity = accountController.getAccountById(1L);
        verify(accountService).findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testAddLinkedAccount() {
        when(accountService.addLinkedAccount(anyLong(), any(Account.class))).thenReturn("Link added successfully");
        ResponseEntity<String> responseEntity = accountController.addLinkedAccount(1L, new Account());
        verify(accountService).addLinkedAccount(eq(1L), any(Account.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testRemoveLinkedAccount() {
        when(accountService.removeLinkedAccount(anyLong(), any(Account.class))).thenReturn("Link removed successfully");
        ResponseEntity<String> responseEntity = accountController.removeLinkedAccount(1L, new Account());
        verify(accountService).removeLinkedAccount(eq(1L), any(Account.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testDeleteAccount() {
        ResponseEntity<Void> responseEntity = accountController.deleteAccount(1L);
        verify(accountService).delete(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}

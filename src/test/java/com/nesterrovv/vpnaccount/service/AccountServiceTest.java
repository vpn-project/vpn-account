package com.nesterrovv.vpnaccount.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nesterrovv.vpnaccount.serivce.AccountService;
import com.nesterrovv.vpnaccount.entity.Account;
import com.nesterrovv.vpnaccount.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class AccountServiceTest {

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testCreateAccount() {
        String username = "testUser";
        boolean isMainAccount = true;
        Set<Account> linkedAccounts = new HashSet<>();
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Account createdAccount = accountService.createAccount(username, isMainAccount, linkedAccounts);
        assertNotNull(createdAccount);
        assertEquals(username, createdAccount.getUsername());
        assertEquals(isMainAccount, createdAccount.isMainAccount());
        assertEquals(linkedAccounts, createdAccount.getLinkedAccounts());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testFindById_ExistingAccount() {
        Long accountId = 1L;
        Account mockAccount = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        Account foundAccount = accountService.findById(accountId);
        assertNotNull(foundAccount);
        assertEquals(mockAccount, foundAccount);
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testFindById_NonExistingAccount() {
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        Account foundAccount = accountService.findById(accountId);
        assertNull(foundAccount);
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testAddLinkedAccount_Success() {
        Long mainAccountId = 1L;
        Account mainAccount = new Account("mainUser", true, null);
        when(accountRepository.findById(mainAccountId)).thenReturn(Optional.of(mainAccount));

        Long linkedAccountId = 2L;
        Account linkedAccount = new Account("linkedUser", false, null);

        // Mocking repository save method
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = accountService.addLinkedAccount(mainAccountId, linkedAccount);

        assertEquals("Account added to set of linked accounts.", result);
        assertTrue(mainAccount.getLinkedAccounts().contains(linkedAccount));
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    void testAddLinkedAccount_MainAccountNotFound() {
        Long mainAccountId = 1L;
        when(accountRepository.findById(mainAccountId)).thenReturn(Optional.empty());

        Long linkedAccountId = 2L;
        Account linkedAccount = new Account("linkedUser", false, null);

        String result = accountService.addLinkedAccount(mainAccountId, linkedAccount);

        assertEquals("Cannot link the accounts. Main account not found.", result);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testRemoveLinkedAccount_Success() {
        Long mainAccountId = 1L;
        Account mainAccount = new Account("mainUser", true, null);
        when(accountRepository.findById(mainAccountId)).thenReturn(Optional.of(mainAccount));

        Long linkedAccountId = 2L;
        Account linkedAccount = new Account("linkedUser", false, null);
        mainAccount.setLinkedAccounts(new HashSet<>(Collections.singletonList(linkedAccount)));

        // Mocking repository save method
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = accountService.removeLinkedAccount(mainAccountId, linkedAccount);

        assertEquals("Account removed from linked.", result);
        assertTrue(mainAccount.getLinkedAccounts().isEmpty());
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    void testRemoveLinkedAccount_NotLinkedAccount() {
        Long mainAccountId = 1L;
        Account mainAccount = new Account("mainUser", true, null);
        when(accountRepository.findById(mainAccountId)).thenReturn(Optional.of(mainAccount));

        Long linkedAccountId = 2L;
        Account linkedAccount = new Account("linkedUser", false, null);

        String result = accountService.removeLinkedAccount(mainAccountId, linkedAccount);

        assertEquals("This account is not linked with given. Nothing removed.", result);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testRemoveLinkedAccount_MainAccountNotFound() {
        Long mainAccountId = 1L;
        when(accountRepository.findById(mainAccountId)).thenReturn(Optional.empty());

        Long linkedAccountId = 2L;
        Account linkedAccount = new Account("linkedUser", false, null);

        String result = accountService.removeLinkedAccount(mainAccountId, linkedAccount);

        assertEquals("Main account not found. Impossible to unlink.", result);
        verify(accountRepository, never()).save(any(Account.class));
    }

    // Add test cases for delete and save methods as needed.

    @Test
    void testDelete() {
        Long accountId = 1L;

        // Mocking repository deleteById method
        doNothing().when(accountRepository).deleteById(accountId);

        accountService.delete(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void testSave() {
        Account accountToSave = new Account("user", true, null);
        when(accountRepository.save(accountToSave)).thenReturn(accountToSave);

        Account savedAccount = accountService.save(accountToSave);

        assertNotNull(savedAccount);
        verify(accountRepository, times(1)).save(accountToSave);
    }
}

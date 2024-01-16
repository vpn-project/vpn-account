package com.nesterrovv.vpnaccount.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountLinkedAccountTest {

    @Test
    void testDefaultConstructor() {
        AccountLinkedAccount accountLinkedAccount = new AccountLinkedAccount();
        assertNull(accountLinkedAccount.getId());
        assertNull(accountLinkedAccount.getMainAccountId());
        assertNull(accountLinkedAccount.getLinkedAccountId());
    }

    @Test
    void testParameterizedConstructor() {
        Account mainAccount = new Account("mainUser", true, null);
        Account linkedAccount = new Account("linkedUser", false, null);
        AccountLinkedAccount accountLinkedAccount = new AccountLinkedAccount(1L, mainAccount, linkedAccount);
        assertEquals(1L, accountLinkedAccount.getId());
        assertEquals(mainAccount, accountLinkedAccount.getMainAccountId());
        assertEquals(linkedAccount, accountLinkedAccount.getLinkedAccountId());
    }

    @Test
    void testGettersAndSetters() {
        AccountLinkedAccount accountLinkedAccount = new AccountLinkedAccount();
        Account mainAccount = new Account("mainUser", true, null);
        Account linkedAccount = new Account("linkedUser", false, null);

        accountLinkedAccount.setId(1L);
        accountLinkedAccount.setMainAccountId(mainAccount);
        accountLinkedAccount.setLinkedAccountId(linkedAccount);

        assertEquals(1L, accountLinkedAccount.getId());
        assertEquals(mainAccount, accountLinkedAccount.getMainAccountId());
        assertEquals(linkedAccount, accountLinkedAccount.getLinkedAccountId());
    }

    @Test
    void testEqualsAndHashCode() {
        Account mainAccount1 = new Account("mainUser1", true, null);
        Account linkedAccount1 = new Account("linkedUser1", false, null);

        Account mainAccount2 = new Account("mainUser2", true, null);
        Account linkedAccount2 = new Account("linkedUser2", false, null);

        AccountLinkedAccount accountLinkedAccount1 = new AccountLinkedAccount(1L, mainAccount1, linkedAccount1);
        AccountLinkedAccount accountLinkedAccount2 = new AccountLinkedAccount(1L, mainAccount1, linkedAccount1);
        AccountLinkedAccount accountLinkedAccount3 = new AccountLinkedAccount(2L, mainAccount1, linkedAccount1);
        AccountLinkedAccount accountLinkedAccount4 = new AccountLinkedAccount(1L, mainAccount2, linkedAccount1);
        AccountLinkedAccount accountLinkedAccount5 = new AccountLinkedAccount(1L, mainAccount1, linkedAccount2);

        assertEquals(accountLinkedAccount1, accountLinkedAccount2);
        assertNotEquals(accountLinkedAccount1, accountLinkedAccount3);
        assertNotEquals(accountLinkedAccount1, accountLinkedAccount4);
        assertNotEquals(accountLinkedAccount1, accountLinkedAccount5);

        assertEquals(accountLinkedAccount1.hashCode(), accountLinkedAccount2.hashCode());
    }
}

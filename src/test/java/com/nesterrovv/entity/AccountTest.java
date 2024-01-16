package com.nesterrovv.entity;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testDefaultConstructor() {
        Account account = new Account();
        assertNull(account.getId());
        assertNull(account.getUsername());
        assertFalse(account.isMainAccount());
        assertNull(account.getLinkedAccounts());
    }

    @Test
    void testParameterizedConstructor() {
        Set<Account> linkedAccounts = new HashSet<>();
        Account account = new Account("testUser", true, linkedAccounts);
        assertNull(account.getId()); // Id should be set by the database
        assertEquals("testUser", account.getUsername());
        assertTrue(account.isMainAccount());
        assertEquals(linkedAccounts, account.getLinkedAccounts());
    }

    @Test
    void testGettersAndSetters() {
        Account account = new Account();
        Set<Account> linkedAccounts = new HashSet<>();

        account.setId(1L);
        account.setUsername("testUser");
        account.setMainAccount(true);
        account.setLinkedAccounts(linkedAccounts);

        assertEquals(1L, account.getId());
        assertEquals("testUser", account.getUsername());
        assertTrue(account.isMainAccount());
        assertEquals(linkedAccounts, account.getLinkedAccounts());
    }

    @Test
    void testEqualsAndHashCode() {
        Set<Account> linkedAccounts1 = new HashSet<>();
        Set<Account> linkedAccounts2 = new HashSet<>();
        linkedAccounts1.add(new Account("linkedAccount1", false, Collections.emptySet()));
        linkedAccounts2.add(new Account("linkedAccount2", false, Collections.emptySet()));

        Account account1 = new Account("testUser", true, linkedAccounts1);
        Account account2 = new Account("testUser", true, linkedAccounts1);
        Account account3 = new Account("differentUser", true, linkedAccounts1);
        Account account4 = new Account("testUser", false, linkedAccounts1);
        Account account5 = new Account("testUser", true, linkedAccounts2);

        assertEquals(account1, account2);
        assertNotEquals(account1, account3);
        assertNotEquals(account1, account4);
        assertNotEquals(account1, account5);

        assertEquals(account1.hashCode(), account2.hashCode());
    }
}


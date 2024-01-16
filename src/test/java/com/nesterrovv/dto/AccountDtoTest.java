package com.nesterrovv.dto;

import com.nesterrovv.entity.Account;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountDtoTest {

    @Test
    void testDefaultConstructor() {
        AccountDto accountDto = new AccountDto();
        assertNull(accountDto.getUsername());
        assertFalse(accountDto.isMainAccount());
        assertNull(accountDto.getLinkedAccounts());
    }

    @Test
    void testParameterizedConstructor() {
        Set<Account> linkedAccounts = new HashSet<>();
        AccountDto accountDto = new AccountDto("testUser", true, linkedAccounts);
        assertEquals("testUser", accountDto.getUsername());
        assertTrue(accountDto.isMainAccount());
        assertEquals(linkedAccounts, accountDto.getLinkedAccounts());
    }

    @Test
    void testGettersAndSetters() {
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername("testUser");
        accountDto.setMainAccount(true);
        Set<Account> linkedAccounts = new HashSet<>();
        accountDto.setLinkedAccounts(linkedAccounts);

        assertEquals("testUser", accountDto.getUsername());
        assertTrue(accountDto.isMainAccount());
        assertEquals(linkedAccounts, accountDto.getLinkedAccounts());
    }

    @Test
    void testEqualsAndHashCode() {
        Set<Account> linkedAccounts1 = new HashSet<>();
        Set<Account> linkedAccounts2 = new HashSet<>();
        linkedAccounts1.add(new Account("linkedAccount1", false, Collections.emptySet()));
        linkedAccounts2.add(new Account("linkedAccount1", false, Collections.emptySet()));

        AccountDto accountDto1 = new AccountDto("testUser", true, linkedAccounts1);
        AccountDto accountDto2 = new AccountDto("testUser", true, linkedAccounts1);
        AccountDto accountDto3 = new AccountDto("differentUser", true, linkedAccounts1);
        AccountDto accountDto4 = new AccountDto("testUser", false, linkedAccounts1);
        AccountDto accountDto5 = new AccountDto("testUser", true, linkedAccounts2);

        assertEquals(accountDto1, accountDto2);
        assertNotEquals(accountDto1, accountDto3);
        assertNotEquals(accountDto1, accountDto4);
        assertEquals(accountDto1, accountDto5);

        assertEquals(accountDto1.hashCode(), accountDto2.hashCode());
    }
}

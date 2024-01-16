package com.nesterrovv.mapper;

import com.nesterrovv.dto.AccountDto;
import com.nesterrovv.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {

    @Test
    void testEntityToDto() {
        // Arrange
        Account account = new Account();
        account.setUsername("testAccount");
        AccountMapper accountMapper = new AccountMapper();
        // Act
        AccountDto accountDto = accountMapper.entityToDto(account);
        // Assert
        assertEquals(account.getUsername(), accountDto.getUsername());
    }

    @Test
    void testDtoToEntity() {
        // Arrange
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername("testAccount");
        AccountMapper accountMapper = new AccountMapper();
        // Act
        Account account = accountMapper.dtoToEntity(accountDto);
        // Assert
        assertEquals(account.getUsername(), accountDto.getUsername());
    }

}

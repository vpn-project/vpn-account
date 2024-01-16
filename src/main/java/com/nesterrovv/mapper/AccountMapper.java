package com.nesterrovv.mapper;

import com.nesterrovv.dto.AccountDto;
import com.nesterrovv.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDto entityToDto(Account account) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(account, AccountDto.class);
    }

    public Account dtoToEntity(AccountDto accountDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(accountDto, Account.class);
    }

}

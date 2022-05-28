package com.example.unitech.service;

import com.example.unitech.resource.AccountDetailsDto;
import com.example.unitech.resource.AccountsDto;

import java.util.List;

public interface AccountService {
    List<AccountDetailsDto> getAccountList(String pin);

    void create(AccountDetailsDto requestBody);

    void delete(String accountNumber);

}

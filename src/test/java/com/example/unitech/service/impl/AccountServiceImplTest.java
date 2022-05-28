package com.example.unitech.service.impl;

import com.example.unitech.data.repository.AccountRepository;
import com.example.unitech.resource.TransferDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;



    @Test
    public void getAccountListInactiveAccount() {

        String pin = "1234";

        when(accountRepository.findAccountsByPinAndStatus(pin, 1)).thenReturn(null);
        assertThrows(
                IllegalArgumentException.class,
                () -> accountService.getAccountList(pin),
                "Active account doesn't exist!");

    }

}
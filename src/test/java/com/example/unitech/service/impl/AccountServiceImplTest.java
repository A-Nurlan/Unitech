package com.example.unitech.service.impl;

import com.example.unitech.data.entity.Account;
import com.example.unitech.data.repository.AccountRepository;
import com.example.unitech.resource.TransferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;


    @Test
    public void getAccountListInactiveAccount() {

        String pin = "12344";

        when(accountRepository.findAccountsByPinAndStatus(pin, 1)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> accountService.getAccountList(pin),
                "Active account doesn't exist!");
    }


}
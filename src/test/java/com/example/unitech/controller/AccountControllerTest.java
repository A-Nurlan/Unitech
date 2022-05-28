package com.example.unitech.controller;

import com.example.unitech.resource.AccountDetailsDto;
import com.example.unitech.resource.AccountsDto;
import com.example.unitech.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    @Test
    public void create() {
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();

        controller.create(accountDetailsDto);

        verify(service, times(1)).create(accountDetailsDto);
    }

    @Test
    public void getAccountList() {
        String pin = "1234";

        List<AccountDetailsDto> mockDto = new ArrayList<>();

        when(service.getAccountList(pin)).thenReturn(mockDto);

        List<AccountDetailsDto> result = controller.getAccountList(pin);

        Assertions.assertEquals(result, mockDto);
    }


}

package com.example.unitech.service.impl;

import com.example.unitech.data.entity.Account;
import com.example.unitech.data.entity.TransactionHistory;
import com.example.unitech.data.repository.AccountRepository;
import com.example.unitech.data.repository.TransactionHistoryRepository;
import com.example.unitech.resource.AccountDetailsDto;
import com.example.unitech.resource.TransferDto;
import com.example.unitech.resource.mapper.TransferMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferMapper transferMapper;

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    @Test
    public void transferFromNonExistingAccount() {
        TransferDto requestBody = new TransferDto();

        requestBody.setFromAccount("sdfsdfsdfffdfd");

        when(accountRepository.findAccountByAccountNumber(requestBody.getFromAccount())).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> transferService.transfer(requestBody),
                "Sender account doesn't exist!");
    }


    @Test
    public void transferToNonExistingAccount() {
        TransferDto requestBody = new TransferDto();

        requestBody.setFromAccount("sdfsdfsdfffdfd");

        when(accountRepository.findAccountByAccountNumber(requestBody.getFromAccount())).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> transferService.transfer(requestBody),
                "Receiver account doesn't exist!");
    }

    @Test
    public void transferInactiveAccount() {
        TransferDto requestBody = new TransferDto();

        Account mock = new Account();
        mock.setStatus(false);

        when(accountRepository.findAccountByAccountNumber(requestBody.getFromAccount())).thenReturn(Optional.of(mock));

        assertThrows(
                IllegalArgumentException.class,
                () -> transferService.transfer(requestBody),
                "Sender or Receiver account is inactive !");

    }

    @Test
    public void transferSameAccount() {
        TransferDto requestBody = new TransferDto();
        Account account = new Account();

        requestBody.setToAccount(account.getAccountNumber());
        requestBody.setFromAccount(account.getAccountNumber());

        when(accountRepository.findAccountByAccountNumber(requestBody.getFromAccount())).thenReturn(Optional.of(account));

        assertThrows(
                IllegalArgumentException.class,
                () -> transferService.transfer(requestBody),
                "Same account!");
    }

    @Test
    public void transferNotEnoughCash() {
        TransferDto requestBody = new TransferDto();
        Account account = new Account();

        account.setBalance(BigDecimal.valueOf(5));
        requestBody.setAmount(BigDecimal.valueOf(6));

        when(accountRepository.findAccountByAccountNumber(requestBody.getFromAccount())).thenReturn(Optional.of(account));

        assertThrows(
                IllegalArgumentException.class,
                () -> transferService.transfer(requestBody),
                "Not enough cash!");
    }

    @Test
    public void transfer() {
        TransferDto requestBody = new TransferDto();

        requestBody.setFromAccount("1111111");
        requestBody.setToAccount("22222222");
        requestBody.setAmount(BigDecimal.valueOf(20));

        Account fromAccount=new Account();
        Account toAccount=new Account();

        when( accountRepository.findAccountByAccountNumber(requestBody.getFromAccount()))
                .thenReturn(Optional.of(fromAccount));
        when( accountRepository.findAccountByAccountNumber(requestBody.getToAccount()))
                .thenReturn(Optional.of(toAccount));

        fromAccount.setStatus(true);
        fromAccount.setAccountNumber("1111111");
        toAccount.setStatus(true);
        toAccount.setAccountNumber("22222222");

        fromAccount.setBalance(BigDecimal.valueOf(100));
        toAccount.setBalance(BigDecimal.valueOf(100));

        transferService.transfer(requestBody);

        TransactionHistory transactionHistory = transferMapper.from(requestBody);

        verify(transactionHistoryRepository, times(1))
                .save(transactionHistory);
    }

}
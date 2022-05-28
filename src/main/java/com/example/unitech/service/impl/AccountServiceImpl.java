package com.example.unitech.service.impl;

import com.example.unitech.data.entity.Account;
import com.example.unitech.data.repository.AccountRepository;
import com.example.unitech.resource.AccountDetailsDto;
import com.example.unitech.resource.AccountsDto;
import com.example.unitech.resource.mapper.AccountMapper;
import com.example.unitech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    @Override
    public List<AccountDetailsDto> getAccountList(String pin) {
        List<Account> accountList = accountRepository.findAccountsByPinAndStatus(pin, 1)
                .orElseThrow(() -> new IllegalArgumentException("Active account doesn't exist!"));

        List<AccountDetailsDto> accountDetailsDtoList = new ArrayList<>();
        accountList.forEach(account -> {
            accountDetailsDtoList.add(accountMapper.to(account));
        });

        return accountDetailsDtoList;
    }

    @Override
    public void create(AccountDetailsDto requestBody) {
        Account account = accountMapper.from(requestBody);
        accountRepository.save(account);
    }

    @Override
    public void delete(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(
                () -> new IllegalArgumentException("Account doesn't exist!"));

        accountRepository.delete(account);
    }
}

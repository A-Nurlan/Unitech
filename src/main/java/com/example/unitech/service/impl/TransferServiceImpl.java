package com.example.unitech.service.impl;

import com.example.unitech.data.entity.Account;
import com.example.unitech.data.entity.TransactionHistory;
import com.example.unitech.data.repository.AccountRepository;
import com.example.unitech.data.repository.TransactionHistoryRepository;
import com.example.unitech.resource.TransferDto;
import com.example.unitech.resource.mapper.TransferMapper;
import com.example.unitech.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;

    private final TransferMapper transferMapper;

    private final TransactionHistoryRepository transactionHistoryRepository;


    @Override
    @Transactional
    public void transfer(TransferDto requestBody) {
        Account fromAccount = accountRepository.findAccountByAccountNumber(requestBody.getFromAccount())
                .orElseThrow(() -> new IllegalArgumentException("Sender account doesn't exist!"));

        Account toAccount = accountRepository.findAccountByAccountNumber(requestBody.getToAccount())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account doesn't exist!"));

        if (!fromAccount.isStatus()|| !toAccount.isStatus())
            throw new IllegalArgumentException("Sender or Receiver account is inactive !");

        if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber()))
            throw new IllegalArgumentException("Same account!");

        if (fromAccount.getBalance().compareTo(requestBody.getAmount()) < 0)
            throw new IllegalArgumentException("Not enough cash!");

        fromAccount.setBalance(fromAccount.getBalance().subtract(requestBody.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(requestBody.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        TransactionHistory transactionHistory = transferMapper.from(requestBody);
        transactionHistoryRepository.save(transactionHistory);
    }
}

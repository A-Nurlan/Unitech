package com.example.unitech.controller;

import com.example.unitech.resource.AccountDetailsDto;
import com.example.unitech.resource.AccountsDto;
import com.example.unitech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/by-pin/{pin}")
    public List<AccountDetailsDto> getAccountList(@PathVariable("pin") String pin) {
        return accountService.getAccountList(pin);
    }

    @PostMapping
    public void create(@RequestBody @Validated AccountDetailsDto requestBody) {
        accountService.create(requestBody);
    }

    @DeleteMapping("/{accountNumber}")
    public void delete(@PathVariable String accountNumber) {
        accountService.delete(accountNumber);
    }


}

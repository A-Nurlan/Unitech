package com.example.unitech.data.repository;

import com.example.unitech.data.entity.Account;
import com.example.unitech.resource.AccountsDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

//    Optional<List<Account>> findAccountsByPin(String pin);

    Optional<List<Account>> findAccountsByPinAndStatus(String pin,int status);

    Optional<Account> findAccountByAccountNumber(String accountNumber);
}

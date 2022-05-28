package com.example.unitech.resource.mapper;

import com.example.unitech.data.entity.Account;
import com.example.unitech.data.entity.TransactionHistory;
import com.example.unitech.resource.AccountDetailsDto;
import com.example.unitech.resource.TransferDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper extends ResourceEntityMapper<AccountDetailsDto, Account> {

    @Override
    AccountDetailsDto to(Account entity);

    @Override
    Account from(AccountDetailsDto resource);

    @Override
    void mapTo(@MappingTarget AccountDetailsDto resource, Account entity);

    @Override
    void mapFrom(@MappingTarget Account resource, AccountDetailsDto entity);

}

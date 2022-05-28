package com.example.unitech.resource.mapper;

import com.example.unitech.data.entity.TransactionHistory;
import com.example.unitech.data.entity.User;
import com.example.unitech.resource.TransferDto;
import com.example.unitech.resource.UserResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransferMapper extends ResourceEntityMapper<TransferDto, TransactionHistory> {

    @Override
    TransferDto to(TransactionHistory entity);

    @Override
    TransactionHistory from(TransferDto resource);

    @Override
    void mapTo(@MappingTarget TransferDto resource, TransactionHistory entity);

    @Override
    void mapFrom(@MappingTarget TransactionHistory resource, TransferDto entity);

}

package com.example.unitech.controller;


import com.example.unitech.resource.TransferDto;
import com.example.unitech.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public void transfer(@RequestBody @Validated TransferDto requestBody) {
        transferService.transfer(requestBody);
    }


}

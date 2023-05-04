package com.wedoogift.deposit.controller;


import com.wedoogift.deposit.dto.DepositDto;
import com.wedoogift.deposit.dto.DepositFeignRequest;
import com.wedoogift.deposit.exception.custom.WedoogiftBadRequestException;
import com.wedoogift.deposit.exception.custom.WedoogiftUnauthorizedRequestException;
import com.wedoogift.deposit.feignproxy.UserIdentityProxyService;
import com.wedoogift.deposit.model.Deposit;
import com.wedoogift.deposit.repository.DepositRepository;
import com.wedoogift.deposit.service.DepositService;
import com.wedoogift.deposit.util.DepositUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("deposit")
@Tag(name = "Deposit API")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @Autowired
    private DepositUtil depositUtil;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepositDto> getAllDeposits(){
        return depositService.fetchDeposits();
    }


    @GetMapping("/expired")
    @ResponseStatus(HttpStatus.OK)
    public List<DepositFeignRequest> getAllExpiredDeposits(){
        return depositService.getAllExpiredDeposits();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositDto deposit(@RequestBody DepositDto deposit){
        if(!depositUtil.checkDepositDetails(deposit)){
            throw new WedoogiftUnauthorizedRequestException("exception.unauthorized.deposit",deposit);
        }
        return depositService.addDeposit(deposit);
    }
}

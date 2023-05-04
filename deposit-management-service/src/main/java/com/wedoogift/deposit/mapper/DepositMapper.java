package com.wedoogift.deposit.mapper;

import com.wedoogift.deposit.domain.BalanceUpdate;
import com.wedoogift.deposit.dto.DepositDto;
import com.wedoogift.deposit.dto.DepositFeignRequest;
import com.wedoogift.deposit.model.Deposit;

public class DepositMapper {

    private DepositMapper(){}
    public static Deposit mapDepositToEntity(DepositDto depositDto){
        return Deposit.builder()
                .amount(depositDto.amount())
                .type(depositDto.depositType())
                .companyId(depositDto.companyId())
                      .build();
    }

    public static DepositDto mapDepositToDto(Deposit deposit){
        return DepositDto.builder()
                      .amount(deposit.getAmount())
                         .remainingAmount(deposit.getRemainingAmount())
                      .depositType(deposit.getType())
                      .depositExpirationDate(deposit.getExpirationDate())
                      .userId(deposit.getUserId())
                      .companyId(deposit.getCompanyId())
                      .build();
    }


    public static DepositFeignRequest mapToDepositFeignRequest(Deposit deposit){
        return DepositFeignRequest.builder()
                         .amount(deposit.getRemainingAmount())
                         .depositType(deposit.getType().getDepositType())
                         .userId(deposit.getUserId())
                         .build();
    }


    public static BalanceUpdate mapDepositToEvent(DepositDto depositDto){
        return BalanceUpdate.builder()
                            .depositAmount(depositDto.amount())
                            .transactionType(0)
                            .companyId(depositDto.companyId())
                            .userId(depositDto.userId())
                            .depositType(depositDto.depositType().getDepositType())
                            .build();
    }
}

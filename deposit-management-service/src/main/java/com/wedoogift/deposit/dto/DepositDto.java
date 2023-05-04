package com.wedoogift.deposit.dto;

import com.wedoogift.deposit.enumeration.DepositTypeEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Builder
public record DepositDto(BigDecimal amount,

                         BigDecimal remainingAmount,
                         DepositTypeEnum depositType,

                         LocalDateTime depositExpirationDate,
                         Integer userId,

                         Integer companyId ) {
}

package com.wedoogift.deposit.domain;


import lombok.Builder;

import java.math.BigDecimal;


@Builder
public record BalanceUpdate(Integer companyId,
                                 Integer userId,

                                 // (0 : deposit) ,(1 : payment or expiration)
                                 Integer transactionType,
                                 BigDecimal depositAmount,

                                 // Meal or Gift
                                 String depositType) {

}

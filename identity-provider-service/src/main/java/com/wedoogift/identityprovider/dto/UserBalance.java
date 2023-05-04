package com.wedoogift.identityprovider.dto;

import lombok.Builder;

import java.math.BigDecimal;


@Builder
public record UserBalance(String userName,
                          BigDecimal mealBalance,
                          BigDecimal giftBalance) {
}

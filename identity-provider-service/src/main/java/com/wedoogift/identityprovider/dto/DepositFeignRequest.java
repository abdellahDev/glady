package com.wedoogift.identityprovider.dto;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DepositFeignRequest(Integer userId, String depositType, BigDecimal amount) {
}

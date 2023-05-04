package com.wedoogift.identityprovider.dto;

import lombok.Builder;

import java.math.BigDecimal;


@Builder
public record CompanyDto(Integer id,
                         String companyName,

                         String address,
                         BigDecimal balance) {
}

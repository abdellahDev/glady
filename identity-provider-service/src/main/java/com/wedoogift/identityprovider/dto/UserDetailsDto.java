package com.wedoogift.identityprovider.dto;

import com.wedoogift.identityprovider.entity.CompanyEntity;
import com.wedoogift.identityprovider.enumeration.RoleEnum;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserDetailsDto(Integer id,
                             String firstName,
                             String lastName,
                             String email,

                             String password,
                             RoleEnum role,

                             CompanyDto company,
                             BigDecimal giftBalance,
                             BigDecimal mealBalance) {
}

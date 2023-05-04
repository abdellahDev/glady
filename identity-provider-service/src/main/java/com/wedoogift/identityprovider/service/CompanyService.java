package com.wedoogift.identityprovider.service;

import com.wedoogift.identityprovider.dto.CompanyDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface CompanyService {

    public CompanyDto createCompany(CompanyDto companyDto);
    public CompanyDto findCompanyByName(String company);

    public BigDecimal updateCompanyBalance(Integer company, BigDecimal amount);

    public CompanyDto findCompanyById(Integer companyId);

    public boolean checkCompanyExists(Integer companyId);
}

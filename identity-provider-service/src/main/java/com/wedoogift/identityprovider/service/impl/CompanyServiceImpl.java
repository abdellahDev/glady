package com.wedoogift.identityprovider.service.impl;

import com.wedoogift.identityprovider.dto.CompanyDto;
import com.wedoogift.identityprovider.entity.CompanyEntity;
import com.wedoogift.identityprovider.entity.UserEntity;
import com.wedoogift.identityprovider.exception.custom.WedoogiftNotFoundException;
import com.wedoogift.identityprovider.mapper.CompanyMapper;
import com.wedoogift.identityprovider.mapper.UserMapper;
import com.wedoogift.identityprovider.repository.CompanyRepository;
import com.wedoogift.identityprovider.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    // CRUD


    private final CompanyRepository companyRepository;





    @Override
    @Transactional
    public CompanyDto createCompany(CompanyDto companyDto){
        CompanyEntity companyEntity = CompanyMapper.mapCompanyDtoToEntity(companyDto);
        companyEntity = companyRepository.save(companyEntity);
        return CompanyMapper.mapCompanyToDto(companyEntity);
    }

    @Override
    public CompanyDto findCompanyByName(String company) {
        CompanyEntity companyEntity = companyRepository.findByCompanyName(company).orElseThrow(()->new WedoogiftNotFoundException("Company does not exist"));

        return CompanyMapper.mapCompanyToDto(companyEntity);
    }

    @Override
    @Transactional
    public BigDecimal updateCompanyBalance(Integer company, BigDecimal amount) {
        CompanyEntity companyEntity = companyRepository.findById(company).orElseThrow(()->new WedoogiftNotFoundException("exception.company.not.found",company));

      BigDecimal updatedBalance = companyEntity.getBalance().add(amount);
       companyEntity.setBalance(updatedBalance);
       companyRepository.save(companyEntity);

       return updatedBalance;
    }

    @Override
    public CompanyDto findCompanyById(Integer companyId) {
        CompanyEntity companyEntity = companyRepository.findById(companyId)
                                                       .orElseThrow(()->new WedoogiftNotFoundException("exception.company.not.found",companyId));

        return CompanyMapper.mapCompanyToDto(companyEntity);
    }

    @Override
    public boolean checkCompanyExists(Integer companyId) {
        return companyRepository.existsById(companyId);
    }
}

package com.wedoogift.identityprovider.mapper;


import com.wedoogift.identityprovider.dto.CompanyDto;
import com.wedoogift.identityprovider.entity.CompanyEntity;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    private CompanyMapper(){}

    /**
     *  to convert a Company entity to dto
     * @param companyEntity : CompanyEntity
     * @return : CompanyDto
     */
    public static CompanyDto mapCompanyToDto(CompanyEntity companyEntity){
        return CompanyDto.builder()
                .id(companyEntity.getId())
                .companyName(companyEntity.getCompanyName())
                .address(companyEntity.getCompanyAddress())
                .balance(companyEntity.getBalance())
                .build();
    }

    /**
     * convert Company dto to entity
     * @param companyDto : CompanyDto
     * @return : CompanyEntity
     */
    public static CompanyEntity mapCompanyDtoToEntity(CompanyDto companyDto){
        return CompanyEntity.builder()
                .id(companyDto.id())
                .companyName(companyDto.companyName())
                .companyAddress(companyDto.address())
                .balance(companyDto.balance())
                .build();
    }
}

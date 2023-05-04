package com.wedoogift.identityprovider.util;

import com.wedoogift.identityprovider.dto.CompanyDto;

public class CompanyUtil {

    private CompanyUtil(){

    }


    /**
     * check validity of the given company's info
     * @param companyDto : CompanyDto
     * @return : boolean
     */
    public static boolean checkCompanyDetails(CompanyDto companyDto){
        return companyDto.companyName()!=null;
    }
}

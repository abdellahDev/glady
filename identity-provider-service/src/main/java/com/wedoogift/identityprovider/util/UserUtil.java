package com.wedoogift.identityprovider.util;

import com.wedoogift.identityprovider.dto.CompanyDto;
import com.wedoogift.identityprovider.dto.UserDetailsDto;

import java.util.HashMap;
import java.util.Map;

public class UserUtil {

    private UserUtil(){

    }


    /**
     * check validity of the given company's info
     * @param userDetails : UserDetailsDto
     * @return : boolean
     */
    public static boolean checkUserDetails(UserDetailsDto userDetails){
        return userDetails.email()!=null && userDetails.lastName()!=null ;
    }


}

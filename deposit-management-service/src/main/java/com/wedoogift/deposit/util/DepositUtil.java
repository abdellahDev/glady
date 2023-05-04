package com.wedoogift.deposit.util;

import com.wedoogift.deposit.dto.DepositDto;
import com.wedoogift.deposit.enumeration.DepositTypeEnum;
import com.wedoogift.deposit.exception.custom.WedoogiftInvalidMessageException;
import com.wedoogift.deposit.feignproxy.UserIdentityProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
public class DepositUtil {
    @Autowired
    private UserIdentityProxyService userIdentityProxy;




    public  LocalDateTime computeExpirationDate(DepositTypeEnum depositType, LocalDateTime depositDate){

        switch (depositType) {
            case GIFT -> {
                return depositDate.plusDays(365);
            }
            case MEAL -> {
                int currentYear = depositDate.getYear();
                LocalDateTime februaryLastDay = LocalDateTime.of(currentYear, 2, 28, 23, 59, 59);
                if (depositDate.isAfter(februaryLastDay)) {
                    currentYear += 1;
                }
                return LocalDateTime.of(currentYear, 3, 1, 0, 0, 0).minusNanos(1);
            }
            default -> throw new WedoogiftInvalidMessageException("exception.invalid.operation",depositType);
        }
    }

    public  boolean checkDepositDetails(DepositDto depositDetails){
        BigDecimal companyBalance = userIdentityProxy.getCompanyBalance(depositDetails.companyId());
        return companyBalance!=null &&
                companyBalance.compareTo(depositDetails.amount())>0
                && userIdentityProxy.loadUser(depositDetails.userId())!=null;
    }
}

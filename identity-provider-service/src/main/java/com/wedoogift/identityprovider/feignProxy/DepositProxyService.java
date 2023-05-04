package com.wedoogift.identityprovider.feignProxy;


import com.wedoogift.identityprovider.dto.DepositFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * only used for scheduled task to update balance every day at midnight
 */
@FeignClient(name = "${deposit-app-name}", url = "${deposit-app-url}")
public interface DepositProxyService {

    @GetMapping("deposit/expired")
    @ResponseStatus(HttpStatus.OK)
    public List<DepositFeignRequest> getAllExpiredDeposits();


}

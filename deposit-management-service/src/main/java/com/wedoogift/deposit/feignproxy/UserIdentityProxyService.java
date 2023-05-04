package com.wedoogift.deposit.feignproxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@FeignClient(name = "${identity-provider-app-name}", url = "${identity-provider-app-url}")
public interface UserIdentityProxyService {

    @GetMapping("api/company/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCompanyBalance(@PathVariable Integer companyId);



    @GetMapping("api/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String loadUser(@PathVariable Integer userId);


}

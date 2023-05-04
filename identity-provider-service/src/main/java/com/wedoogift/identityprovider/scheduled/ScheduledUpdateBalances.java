package com.wedoogift.identityprovider.scheduled;


import com.wedoogift.identityprovider.dto.DepositFeignRequest;
import com.wedoogift.identityprovider.entity.UserEntity;
import com.wedoogift.identityprovider.exception.custom.WedoogiftNotFoundException;
import com.wedoogift.identityprovider.feignProxy.DepositProxyService;
import com.wedoogift.identityprovider.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ScheduledUpdateBalances {



    private final DepositProxyService depositProxyService;


    private final UserRepository userRepository;


    public ScheduledUpdateBalances(DepositProxyService depositProxyService,UserRepository userRepository) {
        this.depositProxyService = depositProxyService;
        this.userRepository=userRepository;
    }


    /**
     * every day at midnight operation to check expired balances
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateBalances() {
        List<DepositFeignRequest> expiredDeposits = depositProxyService.getAllExpiredDeposits();
        if(expiredDeposits!=null){
            // <User, <[Gift or Meal], sum of corresponding deposits>
            Map<Integer,
                    Map<String, BigDecimal>> userExpiredDeposits = expiredDeposits.stream()
                                                                             .collect(Collectors.groupingBy(
                                                                             DepositFeignRequest::userId, Collectors.groupingBy(
                                                                             DepositFeignRequest::depositType,
                                                                              Collectors.mapping(DepositFeignRequest::amount,
                                                                             Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))));

        // update users
            userExpiredDeposits.entrySet().stream()
                    .forEach(userExpiredDeposit->{
                        Integer userId = userExpiredDeposit.getKey();
                        Map<String, BigDecimal> deposits = userExpiredDeposit.getValue();
                        // optional check since the exception is already handled
                        UserEntity user= userRepository.findById(userId).orElseThrow(()->new WedoogiftNotFoundException("exception.company.not.found",userId));
                        user.setMealBalance(user.getMealBalance().subtract(deposits.get("Meal")));
                        user.setGiftBalance(user.getGiftBalance().subtract(deposits.get("Gift")));

                        // save new updates

                        userRepository.save(user);
                    });
        }
    }


}

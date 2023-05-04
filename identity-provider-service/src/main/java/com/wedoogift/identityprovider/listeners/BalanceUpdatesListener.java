package com.wedoogift.identityprovider.listeners;


import com.wedoogift.identityprovider.exception.custom.WedoogiftInvalidMessageException;
import com.wedoogift.identityprovider.service.CompanyService;
import com.wedoogift.identityprovider.service.UserService;
import com.wedoogift.identityprovider.util.KafkaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class BalanceUpdatesListener{


    public final UserService userService;

    public final KafkaUtil kafkaUtil;

    public final CompanyService companyService;


    @KafkaListener(topics = "${deposit.kafka.topic}")
    @Transactional
    public String balanceUpdateListens(String messageIn){
        log.info("Received new Updates : {}",messageIn);
        try {
            final Map<String, Object> payload = kafkaUtil.readJsonAsMap(messageIn);
            Integer companyId = (Integer)  payload.get("companyId");
            Integer userId = (Integer) payload.get("userId");
            String depositType = (String) payload.get("depositType");
            BigDecimal amount = BigDecimal.valueOf((Integer)payload.get("depositAmount"));
            Integer transactionType = (Integer) payload.get("transactionType");

            userService.updateUserBalance(userId,depositType,amount,transactionType) ;
            companyService.updateCompanyBalance(companyId,amount.negate());

        }catch (Exception ex) {
            log.error("Invalid message received : {}", messageIn);
            throw new WedoogiftInvalidMessageException("exception.invalid.message",messageIn);
        }

    return messageIn;
    }

}

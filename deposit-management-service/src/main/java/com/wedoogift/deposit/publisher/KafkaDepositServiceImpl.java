package com.wedoogift.deposit.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedoogift.deposit.config.KafkaPropsConfig;
import com.wedoogift.deposit.domain.BalanceUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaDepositServiceImpl implements KafkaDepositService{

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaPropsConfig kafkaPropsConfig;
    @Override
    public void publishUpdatedBalance(BalanceUpdate balanceUpdate) {
        try {
            final String payload = objectMapper.writeValueAsString(balanceUpdate);
            kafkaTemplate.send(kafkaPropsConfig.getTopic(), payload);
            log.info("Message {} published",balanceUpdate);
        } catch(final JsonProcessingException ex) {
            log.error("Message {} not published ",balanceUpdate);
            throw new RuntimeException("not Published");
        }
    }
}

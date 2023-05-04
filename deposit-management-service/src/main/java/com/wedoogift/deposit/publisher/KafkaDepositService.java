package com.wedoogift.deposit.publisher;

import com.wedoogift.deposit.domain.BalanceUpdate;

public interface KafkaDepositService {
    public void publishUpdatedBalance(BalanceUpdate balanceUpdate);
}

package com.wedoogift.deposit.service;

import com.wedoogift.deposit.dto.DepositDto;
import com.wedoogift.deposit.dto.DepositFeignRequest;
import com.wedoogift.deposit.model.Deposit;
import reactor.core.publisher.Flux;

import java.util.List;

public interface DepositService {
    public List<Deposit>  loadAllDepositHistory();
    public DepositDto addDeposit(DepositDto newDeposit);

    public List<DepositDto> fetchDeposits();

    public List<DepositFeignRequest> getAllExpiredDeposits();
}

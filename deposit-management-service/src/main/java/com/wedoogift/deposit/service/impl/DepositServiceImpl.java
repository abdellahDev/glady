package com.wedoogift.deposit.service.impl;

import com.wedoogift.deposit.domain.BalanceUpdate;
import com.wedoogift.deposit.dto.DepositDto;
import com.wedoogift.deposit.dto.DepositFeignRequest;
import com.wedoogift.deposit.exception.custom.WedoogiftNotFoundException;
import com.wedoogift.deposit.model.Deposit;
import com.wedoogift.deposit.publisher.KafkaDepositService;
import com.wedoogift.deposit.repository.DepositRepository;
import com.wedoogift.deposit.service.DepositService;
import com.wedoogift.deposit.util.DepositUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.wedoogift.deposit.mapper.DepositMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;

    private final DepositUtil depositUtil;

    private final KafkaDepositService kafkaDepositService;




    @Override
    public List<Deposit> loadAllDepositHistory() {
        return depositRepository.findAll();
    }

    @Override
    @Transactional
    public DepositDto addDeposit(DepositDto newDeposit) {
        Deposit deposit = DepositMapper.mapDepositToEntity(newDeposit);

                LocalDateTime depositDate = LocalDateTime.now();
                deposit.setDepositDate(depositDate);
                deposit.setExpirationDate(depositUtil.computeExpirationDate(newDeposit.depositType(), depositDate));
                deposit.setUserId(newDeposit.userId());
                deposit.setRemainingAmount(newDeposit.amount());
                depositRepository.save(deposit);
                BalanceUpdate updatedBalance = DepositMapper.mapDepositToEvent(newDeposit);

                kafkaDepositService.publishUpdatedBalance(updatedBalance);


        return DepositMapper.mapDepositToDto(deposit);
    }

    @Override
    public List<DepositDto> fetchDeposits() {
         return Optional.of(depositRepository.findAll().stream()
                                             .map(DepositMapper::mapDepositToDto)
                                             .collect(Collectors.toList()))
                        .orElseThrow(()-> new WedoogiftNotFoundException("exception.not.found.deposits"));
    }

    @Override
    public List<DepositFeignRequest> getAllExpiredDeposits() {
        List<Deposit> expiredDeposits =  depositRepository.findByExpirationDateBefore(LocalDateTime.now()).orElseThrow(()-> new WedoogiftNotFoundException("exception.not.found.expired.deposits"));

         return expiredDeposits.stream()
                               .map(DepositMapper::mapToDepositFeignRequest)
                               .collect(Collectors.toList());
    }
}

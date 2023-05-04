package com.wedoogift.deposit.repository;

import com.wedoogift.deposit.model.Deposit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DepositRepository extends MongoRepository<Deposit,Integer> {

    public Optional<List<Deposit>> findByExpirationDateBefore(LocalDateTime now);


}

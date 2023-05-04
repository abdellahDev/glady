package com.wedoogift.deposit.model;


import com.wedoogift.deposit.enumeration.DepositTypeEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "deposit")
public class Deposit {

    @Id
    private String id;

    private BigDecimal amount;

    // left amount after a specific payment
    private BigDecimal remainingAmount;

    private LocalDateTime depositDate;

    private LocalDateTime expirationDate;

    private DepositTypeEnum type;

    private Integer userId;


    private Integer userAdminId;

    private Integer companyId;
}

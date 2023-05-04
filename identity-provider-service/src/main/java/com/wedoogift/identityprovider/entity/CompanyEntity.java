package com.wedoogift.identityprovider.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class CompanyEntity {

    @Id
    @SequenceGenerator(name = "company_generator", sequenceName = "company_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
    @Column(name = "company_id")
    private Integer id;

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;


    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_balance")
    private BigDecimal balance;
}
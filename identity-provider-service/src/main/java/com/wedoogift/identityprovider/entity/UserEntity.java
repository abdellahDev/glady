package com.wedoogift.identityprovider.entity;

import com.wedoogift.identityprovider.enumeration.RoleEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_firstname", nullable = false)
    private String firstname;

    @Column(name = "user_lastname", nullable = false)
    private String lastname;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_pass", nullable = false)
    private String password;

    @Column(name="user_role")
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    @Column(name = "gift_balance")
    private BigDecimal giftBalance;
    @Column(name = "meal_balance")
    private BigDecimal mealBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;
}

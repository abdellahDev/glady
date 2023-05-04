package com.wedoogift.deposit.enumeration;

import lombok.Getter;

public enum DepositTypeEnum {
    MEAL("Meal"),
    GIFT("Gift");

    @Getter
    private final String depositType;
    DepositTypeEnum(String depositType) {
        this.depositType=depositType;
    }

}

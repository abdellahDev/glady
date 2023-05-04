package com.wedoogift.deposit.exception;

import lombok.Builder;

import java.util.Date;


@Builder
public record ErrorObject(Integer status,
                          String errorMessage,
                          Date timestamp) {

}
